package model;

import java.io.Serializable;

public class Car implements Serializable{
    private static final long serialVersionUID = 1L;

    private Boolean cool;

    public Car(Boolean cool) {
        if (cool == null) {
            throw new IllegalArgumentException("Поле cool не может быть null");
        }
        this.cool = cool;
    }

    public Boolean getCool() {
        return cool;
    }

    @Override
    public String toString() {
        return "cool = " + cool;
    }
}
