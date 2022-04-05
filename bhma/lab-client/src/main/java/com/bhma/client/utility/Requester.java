package com.bhma.client.utility;

import com.bhma.client.exceptions.NoConnectionException;
import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.Serializer;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public final class Requester {
    private final int bufferSize;
    private final int timeout;
    private final int serverPort;
    private final int reconnectionAttempts;
    private final OutputManager outputManager;

    public Requester(int serverPort, int timeout, int bufferSize, int reconnectionAttempts, OutputManager outputManager) {
        this.serverPort = serverPort;
        this.timeout = timeout;
        this.bufferSize = bufferSize;
        this.reconnectionAttempts = reconnectionAttempts;
        this.outputManager = outputManager;
    }

    public ServerResponse send(ClientRequest request) throws IOException, ClassNotFoundException, NoConnectionException {
        DatagramSocket client = new DatagramSocket();
        client.setSoTimeout(timeout);
        InetAddress address = InetAddress.getByName("localhost");
        byte[] buf = Serializer.serialize(request);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, serverPort);
        client.send(packet);
        byte[] buffer = new byte[bufferSize];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        for (int attempt = 1; attempt <= reconnectionAttempts; attempt++) {
            try {
                client.receive(response);
                break;
            } catch (SocketTimeoutException e) {
                outputManager.printlnImportantColorMessage("Cannot connect with server. Retrying attempt #"
                        + attempt + " now...", Color.RED);
                if (attempt == reconnectionAttempts) {
                    throw new NoConnectionException();
                }
            }
        }
        return (ServerResponse) Serializer.deserialize(buffer);
    }
}
