package com.bhma.server.util;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
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
    public static CollectionManager load(String filePath) throws JAXBException, IOException {
        File file = new File(filePath);
        CollectionManager collectionManager;
        if (file.exists() && file.length() != 0) {
            collectionManager = XMLParser.convertToJavaObject(file);
            collectionManager.setFilePath(filePath);
        } else {
            collectionManager = new CollectionManager(new Hashtable<>(), filePath);
        }
        if (file.exists()) {
            System.out.println("The collection was successfully loaded from the file " + filePath);
        } else {
            System.out.println("No file with this name was found. A new empty collection has been created");
        }
        return collectionManager;
    }
}
