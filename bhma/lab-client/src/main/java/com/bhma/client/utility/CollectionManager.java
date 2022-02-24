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

    public boolean containsKey(Long key) {
        return collection.containsKey(key);
    }

    public SpaceMarine getByKey(Long key) {
        return collection.get(key);
    }

    public void remove(Long key) {
        collection.remove(key);
    }

    public void clear() {
        collection.clear();
    }

    public void removeGreater(SpaceMarine spaceMarine) {
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getValue().compare(spaceMarine) > 0) {
                collection.remove(element.getKey());
            }
        }
    }

    public void removeLowerKey(Long key) {
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getKey() < key) {
                collection.remove(element.getKey());
            }
        }
    }

    public void save() {
        Parser.convertToXML(this, filePath);
    }

    public void removeAnyByWeaponType(Weapon weapon) {
        for (Map.Entry<Long, SpaceMarine> element : collection.entrySet()) {
            if (element.getValue().getWeaponType().equals(weapon)) {
                collection.remove(element.getKey());
                break;
            }
        }
    }

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

    public long countByChapter(Chapter chapter) {
        long result = 0;
        for (SpaceMarine spaceMarine : collection.values()) {
            if (spaceMarine.getChapter().equals(chapter)) {
                result++;
            }
        }
        return result;
    }
}
