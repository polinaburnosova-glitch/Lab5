package console;

import manager.CommandManager;
import model.*;
import java.io.*;

/**
 * Класс для чтения команд из файла (скрипта).
 *
 * <p>Этот класс позволяет выполнять команды, записанные в текстовом файле.
 * Он читает файл построчно и передаёт каждую команду в CommandManager.
 * Также он умеет читать объекты HumanBeing из файла для команд add/update.
 * @see InputReader
 * @see CommandManager
 */
public class FileInputReader implements InputReader {

    /** Менеджер команд, который будет выполнять прочитанные команды */
    private final CommandManager commandManager;

    /**
     * Читает файл. Позволяет читать файл построчно.
     * Использует буферизацию для эффективности.
     */
    private final BufferedReader reader;

    /**
     * Хранилище строк, которые нужно будет прочитать при следующем вызове readHumanBeing().
     * Используется, когда в скрипте встречается команда add/update.
     * Мы читаем все поля объекта сразу и сохраняем их здесь,
     * чтобы потом передать в метод readHumanBeing().
     */
    private String[] readingLines;

    /**
     * Конструктор. Открывает файл для чтения.
     *
     * @param fileName имя файла со скриптом
     * @param commandManager менеджер команд для выполнения
     * @throws FileNotFoundException если файл не найден
     */
    public FileInputReader(String fileName, CommandManager commandManager) throws FileNotFoundException {
        this.commandManager = commandManager;
        this.reader = new BufferedReader(new FileReader(fileName));
        this.readingLines = null;
    }

    /**
     * Читает один объект HumanBeing из файла.
     *
     * <p>Этот метод вызывается командой add (или update) когда нужно создать объект.
     * Он читает 9 строк из файла (все поля человека) и собирает из них объект.
     * @return созданный объект HumanBeing, или null если ошибка чтения
     */
    @Override
    public HumanBeing readHumanBeing() {
        try {
            if (readingLines != null && readingLines.length > 0) {
                String[] lines = readingLines;
                readingLines = null;
                return parseHumanBeing(lines);
            }

            String[] lines = new String[9];
            for (int i = 0; i < 9; i++) {
                lines[i] = reader.readLine();
                if (lines[i] == null) {
                    return null;
                }
                lines[i] = lines[i].trim();
            }
            return parseHumanBeing(lines);
        }

        catch (IOException e) {
            System.out.println("Ошибка во время чтения из файла " + e.getMessage());
            return null;
        }
    }

    /**
     * Превращает массив строк в объект HumanBeing.
     *
     * <p>Этот метод парсит строки в правильные типы данных.
     * @param lines массив из 10 строк (9 полей + car.cool)
     * @return объект HumanBeing, или null если ошибка парсинга
     */
    private HumanBeing parseHumanBeing(String[] lines) {
        try {
            String name = lines[0];
            double x = Double.parseDouble(lines[1]);
            float y = Float.parseFloat(lines[2]);
            Coordinates coordinates = new Coordinates(x, y);

            boolean realHero = Boolean.parseBoolean(lines[3]);
            boolean hasToothpick = Boolean.parseBoolean(lines[4]);
            float impactSpeed = Float.parseFloat(lines[5]);
            String soundtrackName = lines[6];

            WeaponType weaponType = WeaponType.valueOf(lines[7].toUpperCase());

            String moodLine = lines[8];
            Mood mood = moodLine.isEmpty() ? null : Mood.valueOf(moodLine.toUpperCase());

            boolean cool = Boolean.parseBoolean(lines[9]);
            Car car = new Car(cool);

            return new HumanBeing(name, coordinates, realHero, hasToothpick,
                    impactSpeed, soundtrackName, weaponType, mood, car);

        } catch (Exception e) {
            System.out.println("Ошибка во время парсинга: " + e.getMessage());
            return null;
        }
    }

    /**
     * Выполняет весь скрипт из файла.
     *
     * <p>Этот метод читает файл построчно и выполняет каждую команду.
     * Пустые строки и строки, начинающиеся с #, пропускаются (комментарии).
     *
     * <p>Если встречается команда add/add_if_max/add_if_min/update,
     * метод читает следующие 9 строк (поля объекта) и сохраняет их
     * в переменную readingLines. При следующем вызове readHumanBeing()
     * эти строки будут использованы для создания объекта.
     */
    public void executeScript() {
        try {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.println("Строка №" + lineNumber + ": " + line);

                if (line.equalsIgnoreCase("add") ||
                        line.equalsIgnoreCase("add_if_max") ||
                        line.equalsIgnoreCase("add_if_min") ||
                        line.equalsIgnoreCase("update")) {

                    String[] objectLines = new String[9];
                    for (int i = 0; i < 9; i++) { // TODO пускай там скобки будут
                        objectLines[i] = reader.readLine();
                        if (objectLines[i] == null) {
                            System.out.println("Недостаточно строк для создания объекта");
                            break;
                        }
                        objectLines[i] = objectLines[i].trim();
                    }
                    this.readingLines = objectLines;
                }

                commandManager.executeCommand(line);
            }

            System.out.println("Скрипт выполнен");
        } catch (IOException e) {
            System.out.println("Ошибка во время чтения скрипта: " + e.getMessage());
        }
    }
}
