package com.bhma.client.utility;

import com.bhma.common.util.ServerRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private static final int SERVER_PORT = 9990;
    private static final int CLIENT_PORT = 9999;
    private final static int BUFFER_SIZE = 2048;

    public static ServerRequest receiveRequest() throws IOException, ClassNotFoundException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", SERVER_PORT);
        datagramChannel.bind(inetSocketAddress);
        byte[] bytes = new byte[BUFFER_SIZE];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        datagramChannel.receive(byteBuffer);
        return (ServerRequest) deserialize(bytes);
    }

    public static Object receiveObject() throws IOException, ClassNotFoundException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress("127.0.0.1", CLIENT_PORT));
        byte[] bytes = new byte[BUFFER_SIZE];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        datagramChannel.receive(byteBuffer);
        return deserialize(bytes);
    }

    public static void send(Object object) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress("127.0.0.1", CLIENT_PORT));
        byte[] bytes = serialize(object);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", SERVER_PORT);
        datagramChannel.send(byteBuffer, inetSocketAddress);
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
