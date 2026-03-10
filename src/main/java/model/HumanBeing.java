package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HumanBeing implements Comparable<HumanBeing>, Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private Boolean realHero;
    private Boolean hasToothpick;
    private float impactSpeed;
    private String soundtrackName;
    private WeaponType weaponType;
    private Mood mood;
    private Car car;

    public HumanBeing(Long id, String name, Coordinates coordinates,
                      Boolean realHero, Boolean hasToothpick,
                      float impactSpeed, String soundtrackName,
                      WeaponType weaponType, Mood mood, Car car) {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть больше 0");
        }
        this.id = id;

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.name = name.trim();

        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        this.coordinates = coordinates;

        this.creationDate = LocalDateTime.now();

        if (realHero == null) {
            throw new IllegalArgumentException("realHero не может быть null");
        }
        this.realHero = realHero;

        if (hasToothpick == null) {
            throw new IllegalArgumentException("hasToothpick не может быть null");
        }
        this.hasToothpick = hasToothpick;

        if (impactSpeed > 657) {
            throw new IllegalArgumentException("impactSpeed не может быть больше 657");
        }
        this.impactSpeed = impactSpeed;

        if (soundtrackName == null || soundtrackName.trim().isEmpty()) {
            throw new IllegalArgumentException("soundtrackName не может быть null или пустым");
        }
        this.soundtrackName = soundtrackName.trim();

        if (weaponType == null) {
            throw new IllegalArgumentException("weaponType не может быть null");
        }
        this.weaponType = weaponType;

        if (car == null) {
            throw new IllegalArgumentException("car не может быть null");
        }
        this.car = car;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Boolean getRealHero() { return realHero; }
    public Boolean getHasToothpick() { return hasToothpick; }
    public float getImpactSpeed() { return impactSpeed; }
    public String getSoundtrackName() { return soundtrackName; }
    public WeaponType getWeaponType() { return weaponType; }
    public Mood getMood() { return mood; }
    public Car getCar() { return car; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(HumanBeing other){
        return this.id.compareTo(other.id);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустой строкой");
        }
        this.name = name.trim();
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        this.coordinates = coordinates;
    }

    public void setRealHero(Boolean realHero) {
        if (realHero == null) {
            throw new IllegalArgumentException("realHero не может быть null");
        }
        this.realHero = realHero;
    }

    public void setHasToothpick(Boolean hasToothpick) {
        if (hasToothpick == null) {
            throw new IllegalArgumentException("hasToothpick не может быть null");
        }
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(float impactSpeed) {
        if (impactSpeed > 657) {
            throw new IllegalArgumentException("impactSpeed не мжет быть больше 657");
        }
        this.impactSpeed = impactSpeed;
    }

    public void setSoundtrackName(String soundtrackName) {
        if (soundtrackName == null || soundtrackName.trim().isEmpty()){
            throw new IllegalArgumentException("soundtrackName не мжет быть больше null или пустой строкой");
        }
        this.soundtrackName = soundtrackName.trim();
    }

    public void setWeaponType(WeaponType weaponType) {
        if (weaponType == null) {
            throw new IllegalArgumentException("weaponType не может быть null");
        }
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("car не может быть null");
        }
        this.car = car;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("coordinates", "(" + coordinates.getX() + ", " + coordinates.getY() + ")")
                .append("creationDate", creationDate)
                .append("realHero", realHero)
                .append("hasToothpick", hasToothpick)
                .append("impactSpeed", impactSpeed)
                .append("weaponType", weaponType)
                .append("mood", mood)
                .append("car", car.getCool() ? "cool" : "not cool")
                .toString();
    }
}
