package console;

import manager.CommandManager;
import model.*;
import java.io.*;

/**
 * Класс для чтения и выполнения команд из файла-скрипта.
 *
 * <p>Поддерживает два типа команд:
 * <ul>
 *   <li>Обычные команды (без параметров): help, info, show, clear, save, exit</li>
 *   <li>Команды с параметрами: add, add_if_max, add_if_min, update</li>
 * </ul>
 *
 * <p>Команды с параметрами записываются в формате:
 * <pre>
 * add(имя, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, cool)
 * </pre>
 * @see InputReader
 * @see CommandManager
 */
public class FileInputReader implements InputReader {


    private final CommandManager commandManager;


    private final BufferedReader reader;

    /**
     * Временно сохранённый объект HumanBeing для передачи в команду.
     * Используется, когда команда (add, update и т.д.) требует объект,
     * который уже был прочитан из файла.
     */
    private HumanBeing presentPerson;

    /**
     * Конструктор. Открывает файл для чтения.
     *
     * @param fileName имя файла со скриптом
     * @param commandManager менеджер команд для выполнения
     * @throws FileNotFoundException если файл с указанным именем не найден
     */
    public FileInputReader(String fileName, CommandManager commandManager) throws FileNotFoundException {
        this.commandManager = commandManager;
        this.reader = new BufferedReader(new FileReader(fileName));
        this.presentPerson = null;
    }

    /**
     * Возвращает объект HumanBeing для выполнения команды.
     *
     * <p>Если есть сохранённый объект (presentPerson), возвращает его и очищает.
     * В противном случае возвращает null (скрипт не использует построчный ввод).
     *
     * @return объект HumanBeing или null
     */
    @Override
    public HumanBeing readHumanBeing() {
        if (presentPerson != null) {
            HumanBeing person = presentPerson;
            presentPerson = null;
            return person;
        }
        return null;
    }

    /**
     * Создаёт объект HumanBeing из массива строк-параметров.
     *
     * <p>Ожидает массив из 10 элементов в следующем порядке:
     * <ol>
     *   <li>name - имя человека</li>
     *   <li>x - координата X</li>
     *   <li>y - координата Y</li>
     *   <li>realHero - true/false</li>
     *   <li>hasToothpick - true/false</li>
     *   <li>impactSpeed - скорость (макс 657)</li>
     *   <li>soundtrackName - название саундтрека</li>
     *   <li>weaponType - тип оружия (HAMMER/SHOTGUN/KNIFE/BAT)</li>
     *   <li>mood - настроение (SORROW/LONGING/GLOOM/APATHY или пустая строка)</li>
     *   <li>cool - крутая ли машина (true/false)</li>
     * </ol>
     *
     * @param args массив строк с параметрами
     * @return созданный объект HumanBeing, или null при ошибке парсинга
     */
    private HumanBeing createHumanBeingFromArgs(String[] args) {
        try {
            if (args.length < 10) {
                System.out.println("Недостаточно параметров. Необходимо ввести 10, получено: " + args.length);
                return null;
            }

            String name = args[0].trim();
            double x = Double.parseDouble(args[1].trim());
            float y = Float.parseFloat(args[2].trim());
            Coordinates coordinates = new Coordinates(x, y);

            boolean realHero = Boolean.parseBoolean(args[3].trim());
            boolean hasToothpick = Boolean.parseBoolean(args[4].trim());
            float impactSpeed = Float.parseFloat(args[5].trim());
            String soundtrackName = args[6].trim();

            WeaponType weaponType = WeaponType.valueOf(args[7].trim().toUpperCase());

            String moodLine = args[8].trim();
            Mood mood = moodLine.isEmpty() ? null : Mood.valueOf(moodLine.toUpperCase());

            boolean cool = Boolean.parseBoolean(args[9].trim());
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
     * <p>Читает файл построчно. Пустые строки и строки, начинающиеся с '#',
     * игнорируются (комментарии).
     *
     * <p>Если строка содержит скобки, она интерпретируется как команда с параметрами
     * в формате: команда(параметр1, параметр2, ...). Параметры извлекаются,
     * создаётся объект HumanBeing, который сохраняется для команды.
     *
     * <p>Если строка не содержит скобок, она выполняется как обычная команда
     * (help, info, show, exit и т.д.).
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

                if (line.contains("(") && line.contains(")")) {

                    int bracketIndex = line.indexOf('(');
                    String cmdName = line.substring(0, bracketIndex).trim().toLowerCase();

                    int start = line.indexOf('(');
                    int end = line.lastIndexOf(')');
                    String params = line.substring(start + 1, end);

                    String[] args = params.split(",");
                    for (int i = 0; i < args.length; i++) {
                        args[i] = args[i].trim();
                    }

                    HumanBeing person = createHumanBeingFromArgs(args);
                    if (person != null) {
                        System.out.println("Объект создан: " + person.getName());
                        presentPerson = person;
                        commandManager.executeCommand(cmdName);
                    } else {
                        System.out.println("Объект не создан");
                    }
                } else {
                    commandManager.executeCommand(line);
                }
            }

            System.out.println("Скрипт выполнен");
        } catch (IOException e) {
            System.out.println("Ошибка во время чтения скрипта: " + e.getMessage());
        }
    }
}
