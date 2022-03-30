package com.bhma.client.utility;

import com.bhma.client.data.Chapter;
import com.bhma.client.data.SpaceMarine;
import com.bhma.client.data.Weapon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringJoiner;
import java.util.Comparator;

@XmlRootElement(name = "spaceMarines")
public class CollectionManager {
    @XmlElement(name = "spaceMarine")
    private Hashtable<Long, SpaceMarine> collection = new Hashtable<>();
    private final Date dateOfInitialization = new Date();
    private String filePath;

    public CollectionManager(Hashtable<Long, SpaceMarine> collection, String filePath) {
        this.collection = collection;
        this.filePath = filePath;
    }

    public CollectionManager() {
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return string with collection's class, date of creation and size
     */
    public String collectionInfo() {
        return "Collection type: " + collection.getClass().getName() + "\n"
                + "Date of initialization: " + dateOfInitialization + "\n"
                + "Collection size: " + collection.size();
    }

    public Hashtable<Long, SpaceMarine> getCollection() {
        return collection;
    }

    public void addToCollection(Long key, SpaceMarine spaceMarine) {
        spaceMarine.setId(getMaxId() + 1);
        spaceMarine.setCreationDate(new Date());
        collection.put(key, spaceMarine);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        if (collection.size() > 0) {
            collection.forEach((k, v) -> stringJoiner.add(k + ": " + v.toString()));
        } else {
            stringJoiner.add("The collection is empty");
        }
        return stringJoiner.toString();
    }

    /**
     * @param id id of a space-marine instance
     * @return space-marine instance whose id is equal to the entered one
     */
    public SpaceMarine getById(Long id) {
        return collection.values().stream().filter(v -> v.getId().equals(id)).findFirst().get();
    }

    /**
     * @param id checking id
     * @return true if the collection has an element with that id, and false otherwise
     */
    public boolean containsId(Long id) {
        return collection.values().stream().anyMatch(v -> v.getId().equals(id));
    }

    /**
     * @param key checking key
     * @return true if the collection has a key which is equal to the checking one, and false otherwise
     */
    public boolean containsKey(Long key) {
        return collection.containsKey(key);
    }

    /**
     * @param key
     * @return an element in collection which is the value for the entered key
     */
    public SpaceMarine getByKey(Long key) {
        return collection.get(key);
    }

    /**
     * @param key key to the element that will be removed from the collection
     */
    public void remove(Long key) {
        collection.remove(key);
    }

    /**
     * removes all elements from the collection
     */
    public void clear() {
        collection.clear();
    }

    /**
     * removes all elements which is greater than param from the collection
     * @param spaceMarine
     */
    public void removeGreater(SpaceMarine spaceMarine) {
        collection.entrySet().removeIf(e -> e.getValue().compareTo(spaceMarine) > 0);
    }

    /**
     * removes all elements which have key that lower than param
     * @param key
     */
    public void removeLowerKey(Long key) {
        collection.entrySet().removeIf(e -> e.getKey() < key);
    }

    /**
     * convert collection to xml and saves it to the file by filePath
     */
    public void save(OutputManager outputManager) {
        Parser.convertToXML(this, filePath, outputManager);
    }

    /**
     * removes one element whose weapon type is equals to param
     * @param weapon
     */
    public void removeAnyByWeaponType(Weapon weapon) {
        collection.entrySet().stream().filter(e -> e.getValue().getWeaponType().equals(weapon))
                .findFirst().map(e -> collection.remove(e.getKey()));
    }

    /**
     * @return average value of the health field in collection
     */
    public double averageOfHealth() {
        return collection.values().stream().mapToDouble(SpaceMarine::getHealth).average().orElse(0);
    }

    /**
     * @param chapter
     * @return number of elements whose value of chapter field is equal to the param
     */
    public long countByChapter(Chapter chapter) {
        return collection.values().stream().filter(v -> v.getChapter().equals(chapter)).count();
    }

    /**
     * @return max id from the collection
     */
    public long getMaxId() {
        if (collection.size() > 0) {
            return collection.values().stream().max(Comparator.comparing(SpaceMarine::getId)).get().getId();
        } else {
            return 0;
        }
    }
}
