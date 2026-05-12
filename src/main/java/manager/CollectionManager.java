package manager;

import model.HumanBeing;
import model.Mood;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Менеджер для управления коллекцией объектов HumanBeing.
 * Обеспечивает основные операции: добавление, удаление, обновление,
 * фильтрацию и сортировку элементов коллекции.
 * Коллекция хранится в {@link Deque} (реализация {@link ArrayDeque})
 * и автоматически сортируется по id после каждой операции.
 *
 * @author Your Name
 * @version 1.0
 * @see HumanBeing
 * @see FileManager
 */
public class CollectionManager {
    private Deque<HumanBeing> collection;
    private LocalDateTime initializationDate;
    private long nextId;

    /**
     * Конструктор. Создаёт пустую коллекцию и устанавливает начальную дату.
     */
    public CollectionManager() {
        this.collection = new ArrayDeque<>();
        this.initializationDate = LocalDateTime.now();
        this.nextId = 1;
    }

    /**
     * Инициализирует коллекцию загруженными данными.
     *
     * @param collection загруженная коллекция (может быть null)
     */
    public void initializeCollection(Deque<HumanBeing> collection) {
        if (collection == null) {
            this.collection = new ArrayDeque<>();
        }
        else {
            this.collection = collection;
        }
        this.initializationDate = LocalDateTime.now();
        updateNextId();
        sortCollection();
    }

    /**
     * Обновляет значение nextId на основе максимального id в коллекции.
     */
    private void updateNextId() {
        long maxId = 0;
        for (HumanBeing person : collection) {
            if (person.getId() > maxId) {
                maxId = person.getId();
            }
        }
        this.nextId = maxId + 1;
    }

    /**
     * Сортирует коллекцию по id (естественный порядок, заданный в compareTo).
     */
    private void sortCollection() {
        Deque<HumanBeing> sorted = collection.stream().sorted().collect(Collectors.toCollection(ArrayDeque::new));
        collection.clear();
        collection.addAll(sorted);
    }

    /**
     * Добавляет новый элемент в коллекцию.
     * Автоматически устанавливает id и дату создания.
     *
     * @param person объект для добавления (без id и даты)
     */
    public void add(HumanBeing person) {
        HumanBeing completedPerson = new HumanBeing(
                nextId++,
                LocalDateTime.now(),
                person.getName(),
                person.getCoordinates(),
                person.getRealHero(),
                person.getHasToothpick(),
                person.getImpactSpeed(),
                person.getSoundtrackName(),
                person.getWeaponType(),
                person.getMood(),
                person.getCar()
        );
        collection.add(completedPerson);
        sortCollection();
    }

    /**
     * Удаляет элемент по id.
     *
     * @param id идентификатор удаляемого элемента
     * @return true если элемент был удалён, false если не найден
     */
    public boolean removeById(Long id) {
        for (HumanBeing person : collection) {
            if (person.getId().equals(id)) {
                boolean removed = collection.remove(person);
                if (removed) {
                    sortCollection();
                }
                return removed;
            }
        }
        return false;
    }

    /**
     * Очищает коллекцию.
     */
    public void  clear() {
        collection.clear();
    }

    /**
     * Возвращает элемент по id.
     *
     * @param id идентификатор искомого элемента
     * @return элемент или null, если не найден
     */
    public HumanBeing getById(Long id) {
        for (HumanBeing person : collection) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Обновляет элемент по id.
     * Старый элемент удаляется, новый добавляется с тем же id.
     *
     * @param id идентификатор обновляемого элемента
     * @param updatedPerson новый объект (без id и даты)
     */
    public void updateById(Long id, HumanBeing updatedPerson) {
        HumanBeing lastPerson = getById(id);
        if (lastPerson == null) {
            return;
        }
        removeById(id);

        HumanBeing completedPerson = new HumanBeing(
                id,
                lastPerson.getCreationDate(),
                updatedPerson.getName(),
                updatedPerson.getCoordinates(),
                updatedPerson.getRealHero(),
                updatedPerson.getHasToothpick(),
                updatedPerson.getImpactSpeed(),
                updatedPerson.getSoundtrackName(),
                updatedPerson.getWeaponType(),
                updatedPerson.getMood(),
                updatedPerson.getCar()
        );
        collection.add(completedPerson);
        sortCollection();
    }

    /**
     * Удаляет первый элемент коллекции.
     *
     * @return удалённый элемент или null, если коллекция пуста
     */
    public HumanBeing removeFirst() {
        HumanBeing first = collection.pollFirst();
        if (first != null) {
            sortCollection();
        }
        return first;
    }

    /**
     * Возвращает максимальный элемент коллекции.
     *
     * @return Optional с максимальным элементом или пустой Optional
     */
    public Optional<HumanBeing> getMax() {
        return collection.stream().max(Comparator.naturalOrder());
    }

    /**
     * Возвращает минимальный элемент коллекции.
     *
     * @return Optional с минимальным элементом или пустой Optional
     */
    public Optional<HumanBeing> getMin() {
        return collection.stream().min(Comparator.naturalOrder());
    }

    /**
     * Возвращает элемент с минимальным id.
     * Так как коллекция отсортирована по id, это первый элемент.
     *
     * @return Optional с элементом или пустой Optional
     */
    public Optional<HumanBeing> getMinById() {
        return getMin();
    }

    /**
     * Фильтрует коллекцию по настроению.
     *
     * @param mood настроение для фильтрации
     * @return коллекция элементов с указанным настроением
     */
    public Deque<HumanBeing> filterByMood(Mood mood) {
        Deque<HumanBeing> result = new ArrayDeque<>();
        for (HumanBeing person : collection) {
            if (mood.equals(person.getMood())) {
                result.add(person);
            }
        }
        return result;
    }

    /**
     * Фильтрует коллекцию по началу названия саундтрека.
     *
     * @param prefix подстрока для поиска в начале названия
     * @return коллекция элементов, soundtrackName которых начинается с prefix
     */
    public Deque<HumanBeing> filterSoundtrackName(String prefix) {
        Deque<HumanBeing> result = new ArrayDeque<>();
        for (HumanBeing person : collection) {
            if (person.getSoundtrackName() != null && person.getSoundtrackName().startsWith(prefix)) {
                result.add(person);
            }
        }
        return result;
    }

    /**
     * Возвращает копию коллекции.
     *
     * @return копия текущей коллекции
     */
    public Deque<HumanBeing> getCollection() {
        return new ArrayDeque<>(collection);
    }

    /**
     * Возвращает количество элементов в коллекции.
     *
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * Возвращает дату инициализации менеджера.
     *
     * @return дата инициализации
     */
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    /**
     * Возвращает тип коллекции (для команды info).
     *
     * @return имя класса коллекции
     */
    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }
}
