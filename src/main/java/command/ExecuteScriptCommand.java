package command;

import manager.CommandManager;
import console.AppConsole;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecuteScriptCommand extends AbstractCommand {
    private final CommandManager commandManager;
    private final AppConsole console;

    public ExecuteScriptCommand(CommandManager commandManager, AppConsole console) {
        super("ExecuteScriptCommand", ": считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.commandManager = commandManager;
        this.console = console;
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
            executeScriptFile(fileName);
        }
        catch (AccessDeniedException e) {
            System.out.println("Ошибка доступа: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Ошибка ывполнения скрипта: " + e.getMessage());
        }
    }

    private void executeScriptFile(String fileName) {
        System.out.println("Выполняется скрипт из файла: " + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.println("Строка " + lineNumber + ", Выполнение: " + line);

                commandManager.executeCommand(line);

                if (line.equalsIgnoreCase("exit")) {
                    System.out.println("Команда exit завершила выполнение скрипта");
                    break;
                }
            }
            System.out.println("Скрипт " + fileName + " выполнен");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}
