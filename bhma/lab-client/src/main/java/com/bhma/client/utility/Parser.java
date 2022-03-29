package com.bhma.client.utility;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * responsible for converting xml files to the collection manager instance and converting collection manager instance
 * to the xml file
 */
public final class Parser {
    private Parser() {
    }

    /**
     * converts the collection manager instance to the file
     * @param collectionManager
     * @param fileName where will be saves this xml-file
     */
    public static void convertToXML(CollectionManager collectionManager, String fileName, OutputManager outputManager) {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            marshaller.marshal(collectionManager, bufferedOutputStream);
            bufferedOutputStream.close();
        } catch (JAXBException | IOException e) {
            outputManager.printlnImportantColorMessage("Error during converting java object to xml", Color.RED);
        }
    }

    /**
     * converts xml-file to the collection manager instance
     * @param fileName where is the file
     * @return collection manager instance
     * @throws JAXBException if xml-file cannot be converted to java object
     */
    public static CollectionManager convertToJavaObject(File fileName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (CollectionManager) unmarshaller.unmarshal(fileName);
    }
}
