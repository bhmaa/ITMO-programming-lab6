package com.bhma.client.utility;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Hashtable;

public final class CollectionCreator {
    private CollectionCreator() {
    };

    /**
     * builds new Collection Manager from xml-file
     * @param filePath to the xml-file
     * @return new Collection Manager
     * @throws JAXBException if xml-file cannot be converted to java object
     */
    public static CollectionManager load(String filePath) throws JAXBException {
        File file = new File(filePath);
        CollectionManager collectionManager;
        if (file.exists() && file.length() != 0) {
            collectionManager = Parser.convertToJavaObject(file);
            collectionManager.setFilePath(filePath);
            System.out.println("The collection was successfully loaded from the file " + filePath);
        } else {
            collectionManager = new CollectionManager(new Hashtable<>(), filePath);
            System.out.println("No file with this name was found. A new empty collection has been created.");
        }
        return collectionManager;
    }
}
