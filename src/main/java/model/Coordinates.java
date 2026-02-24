package model;

import java.io.Serializable;

public class Coordinates implements Serializable{
    private static final long serialVersionUID = 1L;

    private Double x;
    private Float y;

    public Coordinates(Double x, Float y) {
        if (x == null) {
            throw new IllegalArgumentException("Поле x не может быть null");
        }
        if (y == null) {
            throw new IllegalArgumentException("Поле y не может быть null");
        }
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x= " + x + ", y=" + y;
    }

}
