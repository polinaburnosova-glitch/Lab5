package manager;

import model.HumanBeing;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Вспомогательный класс-обертка для корректной сериализации коллекции в XML.
 * Нужен, чтобы создать корневой элемент &lt;collection&gt;.
 * Класс package-private, так как используется только внутри пакета manager.
 *
 * @author Your Name
 * @version 1.0
 * @see FileManager
 */
@JacksonXmlRootElement(localName = "collection")
class CollectionWrapper {

    /**
     * Коллекция объектов HumanBeing.
     * Каждый элемент будет сериализован как &lt;humanbeing&gt;.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "humanbeing")
    private Deque<HumanBeing> collection = new ArrayDeque<>();

    /**
     * Пустой конструктор для Jackson.
     */
    public CollectionWrapper() {}

    /**
     * Конструктор с коллекцией.
     *
     * @param collection коллекция для обертки
     */
    public CollectionWrapper(Deque<HumanBeing> collection) {
        this.collection = collection;
    }

    /**
     * Возвращает коллекцию.
     *
     * @return коллекция
     */
    public Deque<HumanBeing> getCollection() {
        return collection;
    }

    /**
     * Устанавливает коллекцию.
     *
     * @param collection коллекция
     */
    public void setCollection(Deque<HumanBeing> collection) {
        this.collection = collection;
    }
}
