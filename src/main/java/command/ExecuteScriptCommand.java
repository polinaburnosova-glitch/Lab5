package command;

import manager.CommandManager;
import console.FileInputReader;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Команда для выполнения скрипта из файла.
 * Читает файл построчно и выполняет каждую команду.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final CommandManager commandManager;

    /**
     * Конструктор.
     *
     * @param commandManager менеджер команд
     */
    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", ": считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: команда требует на вход аргументы");
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
                System.out.println("Ошибка: файл " + fileName + "не найден");
                return;
            }

            if (!Files.isReadable(filePath)) {
                throw new AccessDeniedException("Нет прав на чтение файла " + fileName);
            }
            FileInputReader fileReader = new FileInputReader(fileName, commandManager);
            fileReader.executeScript();
        }
        catch (AccessDeniedException e) {
            System.out.println("Ошибка доступа: " + e.getMessage());
        }
        catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден, " + fileName);
        }
        catch (Exception e) {
            System.out.println("Ошибка выполнения скрипта: " + e.getMessage());
        }
    }
}
