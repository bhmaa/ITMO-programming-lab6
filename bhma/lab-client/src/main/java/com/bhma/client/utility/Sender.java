package com.bhma.client.utility;

import com.bhma.common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class Sender {
    private static final int SERVER_PORT = 9990;
    private static final int CLIENT_PORT = 9999;
    private static final int BUFFER_SIZE = 3048;

    private Sender() {
    }

    public static DatagramChannel start() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress("127.0.0.1", CLIENT_PORT));
        return channel;
    }

    public static Object send(DatagramChannel channel, Object object) throws IOException, ClassNotFoundException {
        byte[] bytesSending = Serializer.serialize(object);
        ByteBuffer byteSendingBuffer = ByteBuffer.wrap(bytesSending);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", SERVER_PORT);
        channel.send(byteSendingBuffer, inetSocketAddress);

        byte[] bytesReceiving = new byte[BUFFER_SIZE];
        ByteBuffer byteReceiveBuffer = ByteBuffer.wrap(bytesReceiving);
        channel.receive(byteReceiveBuffer);
        bytesReceiving = byteReceiveBuffer.array();
        return Serializer.deserialize(bytesReceiving);
    }
}
