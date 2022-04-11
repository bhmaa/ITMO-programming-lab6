package com.bhma.client.utility;

import com.bhma.client.exceptions.NoConnectionException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.Serializer;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class Requester {
    private final InetSocketAddress serverAddress;
    private final int bufferSize;
    private final int timeout;
    private final int reconnectionAttempts;
    private final OutputManager outputManager;

    public Requester(InetSocketAddress serverAddress, int timeout, int bufferSize, int reconnectionAttempts,
                     OutputManager outputManager) {
        this.serverAddress = serverAddress;
        this.timeout = timeout;
        this.bufferSize = bufferSize;
        this.reconnectionAttempts = reconnectionAttempts;
        this.outputManager = outputManager;
    }

    public ServerResponse send(ClientRequest request) throws IOException, ClassNotFoundException, NoConnectionException,
            InterruptedException {
        DatagramChannel client = DatagramChannel.open().bind(null);
        client.configureBlocking(false);
        byte[] bytesSending = Serializer.serialize(request);
        ByteBuffer wrapperSending = ByteBuffer.wrap(bytesSending);
        client.send(wrapperSending, serverAddress);
        byte[] bytesReceiving = new byte[bufferSize];
        ByteBuffer wrapperReceiving = ByteBuffer.wrap(bytesReceiving);
        for (int attempt = 1; attempt <= reconnectionAttempts; attempt++) {
            Thread.sleep(timeout);
            if (client.receive(wrapperReceiving) != null) {
                break;
            } else {
                outputManager.printlnImportantColorMessage("Cannot connect with server. Retrying attempt #"
                        + attempt + " now...", Color.RED);
                if (attempt == reconnectionAttempts) {
                    throw new NoConnectionException();
                }
            }
        }
        return (ServerResponse) Serializer.deserialize(bytesReceiving);
    }
}
