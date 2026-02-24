package manager;

import model.HumanBeing;
import model.Mood;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionManager {
    private ArrayDeque<HumanBeing> collection;
    private LocalDateTime initializationDate;
    private long nextId;

    public CollectionManager() {
        this.collection = new ArrayDeque<>();
        this.initializationDate = LocalDateTime.now();
        this.nextId = 1;
    }

    public void initializeCollection(ArrayDeque<HumanBeing> collection) {
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

    private void updateNextId() {
        long maxId = 0;
        for (HumanBeing person : collection) {
            if (person.getId() > maxId) {
                maxId = person.getId();
            }
        }
        this.nextId = maxId + 1;
    }

    private void sortCollection() {
        ArrayDeque<HumanBeing> sorted = collection.stream().sorted().collect(Collectors.toCollection(ArrayDeque::new));
        collection.clear();
        collection.addAll(sorted);
    }

    public void add(HumanBeing person) {
        person.setId(nextId++);
        person.setCreationDate(LocalDateTime.now());
        collection.add(person);
        sortCollection();
    }

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

    public void  clear() {
        collection.clear();
    }

    public HumanBeing getById(Long id) {
        for (HumanBeing person : collection) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    public void updateById(Long id, HumanBeing updatedPerson) {
        removeById(id);
        updatedPerson.setId(id);
        updatedPerson.setCreationDate(LocalDateTime.now());
        collection.add(updatedPerson);
        sortCollection();
    }

    public HumanBeing removeFirst() {
        HumanBeing first = collection.pollFirst();
        if (first != null) {
            sortCollection();
        }
        return first;
    }

    public Optional<HumanBeing> getMax() {
        return collection.stream().max(Comparator.naturalOrder());
    }

    public Optional<HumanBeing> getMin() {
        return collection.stream().min(Comparator.naturalOrder());
    }

    public Optional<HumanBeing> getMinById() {
        return getMin();
    }

    public ArrayDeque<HumanBeing> filterByMood(Mood mood) {
        ArrayDeque<HumanBeing> result = new ArrayDeque<>();
        for (HumanBeing person : collection) {
            if (mood.equals(person.getMood())) {
                result.add(person);
            }
        }
        return result;
    }

    public ArrayDeque<HumanBeing> filterSoundtrackName(String prefix) {
        ArrayDeque<HumanBeing> result = new ArrayDeque<>();
        for (HumanBeing person : collection) {
            if (person.getSoundtrackName() != null && person.getSoundtrackName().startsWith(prefix)) {
                result.add(person);
            }
        }
        return result;
    }

    public ArrayDeque<HumanBeing> getCollection() {
        return collection;
    }

    public int size() {
        return collection.size();
    }

    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }
}
