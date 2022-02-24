package com.bhma.client.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * data class witch describes some chapter by it name and world
 */
@XmlType(propOrder = {"name", "world"})
public class Chapter {
    private String name;
    private String world;

    /**
     * generates new chapter by name and world value
     *
     * @param name  name of chapter (cannot be null)
     * @param world world of chapter (cannot be null)
     */
    public Chapter(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public Chapter() {
    };

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    /**
     * @return string witch includes values of name and world
     */
    @Override
    public String toString() {
        return "Chapter{"
                + "name='" + name + '\''
                + ", world='" + world
                + '\''
                + '}';
    }

    /**
     * @param o the object to compare with
     * @return true if objects have same values of name and world
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chapter chapter = (Chapter) o;
        return name.equals(chapter.name) && world.equals(chapter.world);
    }

    /**
     * @return int value by name and world fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, world);
    }
}
