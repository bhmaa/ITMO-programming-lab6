package com.bhma.server.util;

import com.bhma.common.util.ClientRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class Sender {
    private static final int SERVER_PORT = 9990;
    private static final int CLIENT_PORT = 9999;
    private static final int BUFFER_SIZE = 2048;

    private Sender() {
    }

    public static DatagramChannel start() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(SERVER_PORT));
        // channel.configureBlocking(false);
        return channel;
    }

    public static ClientRequest receiveRequest(DatagramChannel channel) throws IOException, ClassNotFoundException {
        return (ClientRequest) receiveObject(channel);
    }

    public static Object receiveObject(DatagramChannel channel) throws IOException, ClassNotFoundException {
        byte[] bytes = new byte[BUFFER_SIZE];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        channel.receive(byteBuffer);
        return deserialize(bytes);
    }

    public static void send(DatagramChannel channel, Object object) throws IOException {
        byte[] bytes = serialize(object);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        SocketAddress receiverAddress = new InetSocketAddress("127.0.0.1", CLIENT_PORT);
        channel.send(byteBuffer, receiverAddress);
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }
}
