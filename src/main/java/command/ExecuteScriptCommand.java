package command;

import manager.CommandManager;
import console.FileInputReader;
import console.InputReader;
import java.io.*;
import java.nio.file.*;

/**
 * Команда для выполнения скрипта из файла.
 *
 * <p>Скрипт представляет собой текстовый файл, в котором каждая строка содержит команду.
 * Команды могут быть как обычными (help, info, show, save, exit), так и с параметрами
 * в формате: команда(параметр1, параметр2, ...).
 *
 * <p>Поддерживаемые команды с параметрами:
 * <ul>
 *   <li>add(имя, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, cool)</li>
 *   <li>update(имя, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, cool)</li>
 *   <li>add_if_max(имя, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, cool)</li>
 *   <li>add_if_min(имя, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, cool)</li>
 * </ul>
 * @see FileInputReader
 * @see CommandManager
 * @see AddCommand
 * @see UpdateCommand
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final CommandManager commandManager;

    /**
     * Конструктор команды execute_script.
     *
     * @param commandManager менеджер команд для выполнения
     */
    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет скрипт из указанного файла.
     * @param args аргументы команды: args[0] — имя файла со скриптом
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла");
            return;
        }
        if (args.length > 1) {
            System.out.println("Ошибка: команда принимает на вход только один аргумент");
            return;
        }

        String fileName = args[0];
        Path filePath = Paths.get(fileName);

        try {
            if (!Files.exists(filePath)) {
                System.out.println("Ошибка: файл '" + fileName + "' не найден");
                return;
            }
            if (!Files.isReadable(filePath)) {
                System.out.println("Ошибка: нет прав на чтение файла");
                return;
            }


            FileInputReader fileReader = new FileInputReader(fileName, commandManager);

            InputReader oldAddReader = null;
            InputReader oldUpdateReader = null;
            InputReader oldAddIfMaxReader = null;
            InputReader oldAddIfMinReader = null;

            AddCommand addCmd = (AddCommand) commandManager.getCommands().get("add");
            if (addCmd != null) {
                oldAddReader = addCmd.getInputReader();
                addCmd.setInputReader(fileReader);
            }

            UpdateCommand updateCmd = (UpdateCommand) commandManager.getCommands().get("update");
            if (updateCmd != null) {
                oldUpdateReader = updateCmd.getInputReader();
                updateCmd.setInputReader(fileReader);
            }

            AddIfMaxCommand addIfMaxCmd = (AddIfMaxCommand) commandManager.getCommands().get("add_if_max");
            if (addIfMaxCmd != null) {
                oldAddIfMaxReader = addIfMaxCmd.getInputReader();
                addIfMaxCmd.setInputReader(fileReader);
            }

            AddIfMinCommand addIfMinCmd = (AddIfMinCommand) commandManager.getCommands().get("add_if_min");
            if (addIfMinCmd != null) {
                oldAddIfMinReader = addIfMinCmd.getInputReader();
                addIfMinCmd.setInputReader(fileReader);
            }

            fileReader.executeScript();

            if (addCmd != null && oldAddReader != null) {
                addCmd.setInputReader(oldAddReader);
            }
            if (updateCmd != null && oldUpdateReader != null) {
                updateCmd.setInputReader(oldUpdateReader);
            }
            if (addIfMaxCmd != null && oldAddIfMaxReader != null) {
                addIfMaxCmd.setInputReader(oldAddIfMaxReader);
            }
            if (addIfMinCmd != null && oldAddIfMinReader != null) {
                addIfMinCmd.setInputReader(oldAddIfMinReader);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка во время выполнения скрипта: " + e.getMessage());
        }
    }
}