package com.bhma.common.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * main data class
 */
@XmlRootElement(name = "spaceMarine")
@XmlType(propOrder = {"id", "creationDate", "name", "coordinates", "health", "category", "weaponType", "meleeWeapon", "chapter"})
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private double health;
    private AstartesCategory category;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    /**
     * builds new object and generate id and current date as date of creation
     *
     * @param name              name of the space marine (cannot be null)
     * @param coordinates       coordinates of the space marine
     * @param health            health of the space marine (can be null or must be greater than 0)
     * @param category          astartes category of the space marine (cannot be null)
     * @param weaponType        weapon type of the space marine (cannot be null)
     * @param meleeWeapon       melee weapon type of the space marine (can be null)
     * @param chapter           chapter of the space marine
     */
    public SpaceMarine(String name, Coordinates coordinates, double health, AstartesCategory category,
                       Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine() {
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getHealth() {
        return health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    @XmlElement
    public Chapter getChapter() {
        return chapter;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @XmlElement
    public Date getCreationDate() {
        return creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * @return string which includes values of all fields (id, name, coordinates, creation date, health, category, weapon type, melee weapon and chapter)
     */
    @Override
    public String toString() {
        return "SpaceMarine{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", health=" + health
                + ", category=" + category
                + ", weaponType=" + weaponType
                + ", meleeWeapon=" + meleeWeapon
                + ", chapter=" + chapter
                + '}';
    }

    /**
     * compares objects by their names, coordinates, categories, weapon types and chapters
     *
     * @param o the object to compare against
     * @return true if objects are equals, false if they're not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpaceMarine that = (SpaceMarine) o;
        return name.equals(that.name) && coordinates.equals(that.coordinates) && category == that.category && weaponType == that.weaponType && chapter.equals(that.chapter);
    }

    /**
     * @return integer value by name, coordinate, category, weapon type and chapter
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, category, weaponType, chapter);
    }

    /**
     * @param o the object to compare against
     * @return difference between this object's and other's hashcode
     */
    @Override
    public int compareTo(SpaceMarine o) {
        int value = this.name.compareTo(o.name);
        if (value == 0) {
            value = Double.compare(this.health, o.health);
            if (value == 0) {
                value = this.id.compareTo(o.id);
            }
        }
        return value;
    }
}
