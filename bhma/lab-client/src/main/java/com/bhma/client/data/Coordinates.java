package com.bhma.client.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

/**
 * data class witch describes coordinates by x and y
 */
@XmlType(propOrder = {"x", "y"})
public class Coordinates {
    private double x;
    private long y;

    /**
     * generates new coordinates by x and y value
     *
     * @param x double value that must be greater the -685 (cannot be null)
     * @param y long value (cannot be null)
     */
    public Coordinates(double x, long y) {
        this.y = y;
        this.x = x;
    }

    public Coordinates() {
    };

    @XmlElement
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @XmlElement
    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return string witch is includes values of x and y
     */
    @Override
    public String toString() {
        return "Coordinates{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }

    /**
     * @param o the object to compare with
     * @return true if objects have same values of x and y
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 && y == that.y;
    }

    /**
     * @return int value by x and y
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
