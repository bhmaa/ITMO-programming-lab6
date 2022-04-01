package com.bhma.client.utility;

import com.bhma.common.util.ClientRequest;
import com.bhma.common.util.Serializer;
import com.bhma.common.util.ServerResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public final class Requester {
    private static final int BUFFER_SIZE = 3048;
    private static final int TIMEOUT = 1000;
    private static final int SERVER_PORT = 9990;

    private Requester() {
    }

    public static ServerResponse send(ClientRequest request) throws IOException, ClassNotFoundException {
        DatagramSocket client = new DatagramSocket();
        client.setSoTimeout(TIMEOUT);
        InetAddress address = InetAddress.getByName("localhost");
        byte[] buf = Serializer.serialize(request);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, SERVER_PORT);
        client.send(packet);
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        client.receive(response);
        return (ServerResponse) Serializer.deserialize(buffer);
    }
}
