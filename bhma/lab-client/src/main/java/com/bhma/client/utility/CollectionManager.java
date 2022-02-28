package com.bhma.client.utility;

import com.bhma.client.data.Chapter;
import com.bhma.client.data.SpaceMarine;
import com.bhma.client.data.Weapon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringJoiner;

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
    };

    public void setFilePath(String filePath) {
       this.filePath = filePath;
   }

    public String collectionInfo() {
        return "Collection type: " + collection.getClass().getName() + "\n"
                + "Date of initialization: " + dateOfInitialization + "\n"
                + "Collection size: " + collection.size();
    }

    public Hashtable<Long, SpaceMarine> getCollection() {
        return collection;
    }

    public void addToCollection(Long key, SpaceMarine spaceMarine) {
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
     * @param id id of a spacemarine instance
     * @return spacemarine instance whose id is equal to the entered one
     */
    public SpaceMarine getById(Long id) {
        SpaceMarine spaceMarineById = null;
        for (SpaceMarine spaceMarine : collection.values()) {
            if (spaceMarine.getId().equals(id)) {
                spaceMarineById = spaceMarine;
                break;
            }
        }
        return spaceMarineById;
    }

    /**
     * @param id checking id
     * @return true if the collection has an element with that id, and false otherwise
     */
    public boolean containsId(Long id) {
        boolean contains = false;
        for (SpaceMarine spaceMarine : collection.values()) {
            if (spaceMarine.getId().equals(id)) {
                contains = true;
                break;
            }
        }
        return contains;
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
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getValue().compareTo(spaceMarine) > 0) {
                collection.remove(element.getKey());
            }
        }
    }

    /**
     * removes all elements which have key that lower than param
     * @param key
     */
    public void removeLowerKey(Long key) {
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getKey() < key) {
                collection.remove(element.getKey());
            }
        }
    }

    /**
     * convert collection to xml and saves it to the file by filePath
     */
    public void save() {
        Parser.convertToXML(this, filePath);
    }

    /**
     * removes one element whose weapon type is equals to param
     * @param weapon
     */
    public void removeAnyByWeaponType(Weapon weapon) {
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getValue().getWeaponType().equals(weapon)) {
                collection.remove(element.getKey());
                break;
            }
        }
    }

    /**
     * @return average value of the health field in collection
     */
    public double averageOfHealth() {
        if (collection.size() > 0) {
            double result = 0.0;
            int numberOfElements = 0;
            for (SpaceMarine spaceMarine : collection.values()) {
                if (spaceMarine.getHealth() != null) {
                    result = result + spaceMarine.getHealth();
                    numberOfElements++;
                }
            }
            result = result / numberOfElements;
            return result;
        } else {
            return 0;
        }
    }

    /**
     * @param chapter
     * @return number of elements whose value of chapter field is equal to the param
     */
    public long countByChapter(Chapter chapter) {
        long result = 0;
        for (SpaceMarine spaceMarine : collection.values()) {
            if (spaceMarine.getChapter().equals(chapter)) {
                result++;
            }
        }
        return result;
    }

    /**
     * @return max id from the collection
     */
    public long getMaxId() {
        long maxId = 0;
        if (collection.size() > 0) {
            for (SpaceMarine spaceMarine : collection.values()) {
                if (spaceMarine.getId() > maxId) {
                    maxId = spaceMarine.getId();
                }
            }
        }
        return maxId;
    }
}
