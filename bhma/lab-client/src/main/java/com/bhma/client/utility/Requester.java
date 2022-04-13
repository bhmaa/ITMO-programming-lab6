package com.bhma.client.utility;

import com.bhma.client.exceptions.NoConnectionException;
import com.bhma.common.util.CommandRequirement;
import com.bhma.common.util.PullingRequest;
import com.bhma.common.util.PullingResponse;
import com.bhma.common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

public final class Requester {
    private final DatagramChannel client;
    private final InetSocketAddress serverAddress;
    private final int bufferSize;
    private final int timeout;
    private final int reconnectionAttempts;
    private final OutputManager outputManager;

    public Requester(DatagramChannel client, InetSocketAddress serverAddress, int timeout, int bufferSize,
                     int reconnectionAttempts, OutputManager outputManager) {
        this.client = client;
        this.serverAddress = serverAddress;
        this.timeout = timeout;
        this.bufferSize = bufferSize;
        this.reconnectionAttempts = reconnectionAttempts;
        this.outputManager = outputManager;
    }

    public Object send(Object request) throws IOException, NoConnectionException, InterruptedException, ClassNotFoundException {
        byte[] bytesSending = Serializer.serialize(request);
        ByteBuffer wrapperSending = ByteBuffer.wrap(bytesSending);
        for (int attempt = 1; attempt <= reconnectionAttempts; attempt++) {
            if (client.send(wrapperSending, serverAddress) == bytesSending.length) {
                break;
            } else {
                outputManager.printlnImportantColorMessage("Cannot send request to the server. Retrying attempt #"
                        + attempt + " now...", Color.RED);
                if (attempt == reconnectionAttempts) {
                    throw new NoConnectionException();
                }
                Thread.sleep(timeout);
            }
        }
        byte[] bytesReceiving = new byte[bufferSize];
        ByteBuffer wrapperReceiving = ByteBuffer.wrap(bytesReceiving);
        for (int attempt = 1; attempt <= reconnectionAttempts; attempt++) {
            Thread.sleep(timeout);
            if (client.receive(wrapperReceiving) != null) {
                break;
            } else {
                outputManager.printlnImportantColorMessage("Cannot receive response from server. Retrying attempt #"
                        + attempt + " now...", Color.RED);
                if (attempt == reconnectionAttempts) {
                    throw new NoConnectionException();
                }
            }
        }
        return Serializer.deserialize(bytesReceiving);
    }

    public HashMap<String, CommandRequirement> sendPullingRequest() throws NoConnectionException, IOException, InterruptedException, ClassNotFoundException {
        PullingResponse response = (PullingResponse) send(new PullingRequest());
        return response.getRequirements();
    }
}
