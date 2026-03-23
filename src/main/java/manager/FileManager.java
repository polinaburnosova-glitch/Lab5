package manager;

import model.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Менеджер для работы с файлами.
 * Отвечает за загрузку коллекции из XML-файла и сохранение её в файл.
 * Использует Jackson XML для сериализации/десериализации.
 * Чтение осуществляется через {@link BufferedInputStream},
 * запись через {@link FileOutputStream} (согласно ТЗ).
 *
 * @author Your Name
 * @version 1.0
 * @see CollectionManager
 * @see CollectionWrapper
 */
public class FileManager {
    private final String fileName;
    private final XmlMapper xmlMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Конструктор. Инициализирует Jackson XmlMapper с поддержкой LocalDateTime.
     *
     * @param fileName имя файла для работы
     */
    public FileManager(String fileName) {
        this.fileName = fileName;

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_FORMATTER));

        this.xmlMapper = XmlMapper.builder()
                .addModule(javaTimeModule)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .build();
    }

    /**
     * Загружает коллекцию из XML-файла.
     *
     * @return загруженная коллекция (пустая, если файл не существует или повреждён)
     * @throws IOException если ошибка доступа к файлу
     */
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

    /**
     * Сохраняет коллекцию в XML-файл.
     *
     * @param collection коллекция для сохранения
     * @throws IOException если ошибка доступа к файлу
     */
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
