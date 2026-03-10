package manager;

import model.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.util.Deque;
import java.util.ArrayDeque;

public class FileManager {
    private final String fileName;
    private final XmlMapper xmlMapper;

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.xmlMapper = XmlMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .build();
    }

    public Deque<HumanBeing> loadCollection() throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Файл не найден, создается пустая коллекция");
            return new ArrayDeque<>();
        }

        if (!file.canRead()) {
            throw new IOException("Нет прав для чтения файла");
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            CollectionWrapper wrapper = xmlMapper.readValue(bis, CollectionWrapper.class);
            return wrapper.getCollection();}
        catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return new ArrayDeque<>();
        }
    }
    public void saveCollection(Deque<HumanBeing> collection) throws IOException {
        File file = new File(fileName);

        if (file.exists() && !file.canWrite()) {
            throw new IOException("Нет прав для записи в файл");
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            CollectionWrapper wrapper = new CollectionWrapper(collection);
            xmlMapper.writeValue(fos, wrapper);
        }
    }

}
