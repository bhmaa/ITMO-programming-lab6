package com.bhma.server.util;

import com.bhma.common.util.ExecuteCode;
import com.bhma.common.util.ServerResponse;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.Hashtable;

public final class CollectionCreator {
    private CollectionCreator() {
    }

    /**
     * builds new Collection Manager from xml-file
     * @param filePath to the xml-file
     * @return new Collection Manager
     * @throws JAXBException if xml-file cannot be converted to java object
     */
    public static CollectionManager load(String filePath, DatagramChannel channel) throws JAXBException, IOException {
        File file = new File(filePath);
        CollectionManager collectionManager;
        if (file.exists() && file.length() != 0) {
            collectionManager = XMLParser.convertToJavaObject(file);
            collectionManager.setFilePath(filePath);
        } else {
            collectionManager = new CollectionManager(new Hashtable<>(), filePath);
        }
        if (file.exists()) {
            Sender.send(channel, new ServerResponse("The collection was successfully loaded from the file "
                    + filePath, ExecuteCode.SUCCESS));
        } else {
            Sender.send(channel, new ServerResponse("No file with this name was found. A new empty collection "
                    + "has been created", ExecuteCode.SUCCESS));
        }
        return collectionManager;
    }
}
