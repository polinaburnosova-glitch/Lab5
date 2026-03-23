package console;

import manager.CommandManager;
import model.HumanBeing;
import java.io.*;

/**
 * Реализация {@link InputReader} для выполнения скриптов из файла.
 * Читает файл построчно и выполняет команды через CommandManager.
 */
public class FileInputReader implements InputReader {
    private final CommandManager commandManager;
    private final BufferedReader reader;

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
    }

    /**
     * Прямое чтение объекта не поддерживается для скриптов.
     * Используйте {@link #executeScript()} для выполнения скрипта.
     *
     * @throws UnsupportedOperationException всегда
     */
    @Override
    public HumanBeing readHumanBeing() {
        throw new UnsupportedOperationException("FileInputReader не поддерживает прямое чтение объектов");
    }

    /**
     * Выполняет скрипт из файла.
     * Читает файл построчно, пропускает пустые строки и комментарии (#),
     * выполняет каждую команду через CommandManager.
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

                System.out.println("[Строка " + lineNumber + "] " + line);
                commandManager.executeCommand(line);
            }

            System.out.println("Скрипт выполнен");
        } catch (IOException e) {
            System.out.println("Ошибка чтения скрипта: " + e.getMessage());
        }
    }
}
