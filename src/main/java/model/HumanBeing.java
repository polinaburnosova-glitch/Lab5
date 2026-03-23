package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Класс, представляющий человека.
 * Является основным объектом коллекции. Содержит всю информацию о человеке:
 * идентификатор, имя, координаты, дату создания и другие характеристики.
 * Класс реализует {@link Comparable} для сортировки по id и {@link Serializable} для сохранения.
 *
 * @author Your Name
 * @version 1.0
 * @see Coordinates
 * @see Car
 * @see WeaponType
 * @see Mood
 */
public class HumanBeing implements Comparable<HumanBeing>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final Long id;
    private final String name;
    private final Coordinates coordinates;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime creationDate;

    private final Boolean realHero;
    private final Boolean hasToothpick;
    private final float impactSpeed;
    private final String soundtrackName;
    private final WeaponType weaponType;
    private final Mood mood;
    private final Car car;

    /**
     * ПЕРВЫЙ КОНСТРУКТОР - для создания НОВЫХ объектов (без id и creationDate).
     * Используется при добавлении элемента через команды add, update и т.д.
     * Поля id и creationDate будут установлены позже в {@link manager.CollectionManager}.
     *
     * @param name имя человека (не может быть null или пустым)
     * @param coordinates координаты человека (не может быть null)
     * @param realHero флаг "настоящий герой" (не может быть null)
     * @param hasToothpick флаг "наличие зубочистки" (не может быть null)
     * @param impactSpeed скорость воздействия (максимум 657)
     * @param soundtrackName название саундтрека (не может быть null или пустым)
     * @param weaponType тип оружия (не может быть null)
     * @param mood настроение (может быть null)
     * @param car машина (не может быть null)
     * @throws IllegalArgumentException если какое-либо поле не прошло валидацию
     */
    public HumanBeing(String name, Coordinates coordinates,
                      Boolean realHero, Boolean hasToothpick,
                      float impactSpeed, String soundtrackName,
                      WeaponType weaponType, Mood mood, Car car) {
        // id и creationDate будут установлены позже
        this.id = null;
        this.creationDate = null;

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.name = name.trim();

        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        this.coordinates = coordinates;

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

        this.mood = mood;

        if (car == null) {
            throw new IllegalArgumentException("car не может быть null");
        }
        this.car = car;
    }

    /**
     * ВТОРОЙ КОНСТРУКТОР - для загрузки из файла (с id и creationDate)
     * Используется Jackson для десериализации XML.
     *
     * @param id идентификатор (не может быть null, > 0)
     * @param creationDate дата создания (не может быть null)
     * @param name имя человека (не может быть null или пустым)
     * @param coordinates координаты человека (не может быть null)
     * @param realHero флаг "настоящий герой" (не может быть null)
     * @param hasToothpick флаг "наличие зубочистки" (не может быть null)
     * @param impactSpeed скорость воздействия (максимум 657)
     * @param soundtrackName название саундтрека (не может быть null или пустым)
     * @param weaponType тип оружия (не может быть null)
     * @param mood настроение (может быть null)
     * @param car машина (не может быть null)
     * @throws IllegalArgumentException если какое-либо поле не прошло валидацию
     */
    @JsonCreator
    public HumanBeing(
            @JsonProperty("id") Long id,
            @JsonProperty("creationDate") LocalDateTime creationDate,
            @JsonProperty("name") String name,
            @JsonProperty("coordinates") Coordinates coordinates,
            @JsonProperty("realHero") Boolean realHero,
            @JsonProperty("hasToothpick") Boolean hasToothpick,
            @JsonProperty("impactSpeed") float impactSpeed,
            @JsonProperty("soundtrackName") String soundtrackName,
            @JsonProperty("weaponType") WeaponType weaponType,
            @JsonProperty("mood") Mood mood,
            @JsonProperty("car") Car car) {

        // Валидация id
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть больше 0");
        }
        this.id = id;

        // Валидация creationDate
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate не может быть null");
        }
        this.creationDate = creationDate;

        // Валидация имени
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.name = name.trim();

        // Валидация координат
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        this.coordinates = coordinates;

        // Валидация realHero
        if (realHero == null) {
            throw new IllegalArgumentException("realHero не может быть null");
        }
        this.realHero = realHero;

        // Валидация hasToothpick
        if (hasToothpick == null) {
            throw new IllegalArgumentException("hasToothpick не может быть null");
        }
        this.hasToothpick = hasToothpick;

        // Валидация impactSpeed
        if (impactSpeed > 657) {
            throw new IllegalArgumentException("impactSpeed не может быть больше 657");
        }
        this.impactSpeed = impactSpeed;

        // Валидация soundtrackName
        if (soundtrackName == null || soundtrackName.trim().isEmpty()) {
            throw new IllegalArgumentException("soundtrackName не может быть null или пустым");
        }
        this.soundtrackName = soundtrackName.trim();

        // Валидация weaponType
        if (weaponType == null) {
            throw new IllegalArgumentException("weaponType не может быть null");
        }
        this.weaponType = weaponType;

        this.mood = mood;

        // Валидация car
        if (car == null) {
            throw new IllegalArgumentException("car не может быть null");
        }
        this.car = car;
    }

    // Геттеры
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

    /**
     * Сравнивает двух людей по id.
     * Используется для сортировки коллекции по умолчанию.
     *
     * @param other другой объект для сравнения
     * @return отрицательное число, если текущий id меньше;
     *         положительное, если больше;
     *         0, если равны
     */
    @Override
    public int compareTo(HumanBeing other) {
        if (this.id == null || other.id == null) {
            return 0;
        }
        return this.id.compareTo(other.id);
    }

    /**
     * Возвращает строковое представление объекта.
     * Использует Apache Commons ToStringBuilder для красивого форматирования.
     *
     * @return строковое представление объекта HumanBeing
     */
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
                .append("soundtrackName", soundtrackName)
                .append("weaponType", weaponType)
                .append("mood", mood)
                .append("car", car.getCool() ? "cool" : "not cool")
                .toString();
    }
}
