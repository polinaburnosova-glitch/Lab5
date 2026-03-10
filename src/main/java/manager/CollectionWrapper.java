package manager;

import model.HumanBeing;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Deque;
import java.util.ArrayDeque;

@JacksonXmlRootElement(localName = "collection")
class CollectionWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "humanbeing")
    private Deque<HumanBeing> collection = new ArrayDeque<>();

    public CollectionWrapper() {}

    public CollectionWrapper(Deque<HumanBeing> collection) {
        this.collection = collection;
    }

    public Deque<HumanBeing> getCollection() {
        return collection;
    }

    public void setCollection(Deque<HumanBeing> collection) {
        this.collection = collection;
    }
}
