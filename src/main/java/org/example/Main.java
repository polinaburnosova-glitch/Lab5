package org.example;

import manager.CollectionManager;
import manager.CommandManager;
import manager.FileManager;
import console.AppConsole;
import command.*;
import java.io.InputStreamReader;
import console.ConsoleInputReader;
import console.InputReader;

/**
 * Главный класс приложения.
 * Запускает программу, инициализирует все компоненты,
 * загружает коллекцию из файла, регистрирует команды
 * и запускает интерактивную консоль.
 */
public class Main {

    /**
     * Точка входа в программу.
     * Обрабатывает аргументы командной строки, создаёт менеджеры,
     * загружает коллекцию из файла, регистрирует команды и запускает консоль.
     * @param args аргументы командной строки:
     *             args[0] - имя файла для загрузки/сохранения коллекции (обязательный)
     *             args[1..n] - игнорируются, если указаны
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла, содержащего коллекцию");
            return;
        }

        String fileName = args[0];

        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        FileManager fileManager = new FileManager(fileName);

        InputReader inputReader = new ConsoleInputReader();

        try {
            collectionManager.initializeCollection(fileManager.loadCollection());
            System.out.println("Коллекция загружена из файла " + fileName);
        }
        catch (Exception e) {
            System.out.println("Не удалось загрузить коллекцию: " + e.getMessage());
        }
        AppConsole console = new AppConsole(commandManager);

        registerCommands(commandManager, collectionManager, fileManager, inputReader);

        console.run(new InputStreamReader(System.in));
    }

    /**
     * Регистрирует все команды в CommandManager.
     * Команды разделены на группы для удобства чтения.
     *
     * @param cmdManager   менеджер команд, в который регистрируются команды
     * @param colManager   менеджер коллекции, используемый командами
     * @param fileManager  менеджер файлов, используемый командами save и execute_script
     * @param inputReader  источник ввода для создания объектов (консоль или файл)
     */
    private static void registerCommands(CommandManager cmdManager,
                                         CollectionManager colManager,
                                         FileManager fileManager,
                                         InputReader inputReader) {


        cmdManager.registerCommand(new HelpCommand(cmdManager));
        cmdManager.registerCommand(new InfoCommand(colManager));
        cmdManager.registerCommand(new ShowCommand(colManager));
        cmdManager.registerCommand(new ClearCommand(colManager));
        cmdManager.registerCommand(new ExitCommand());
        cmdManager.registerCommand(new RemoveFirstCommand(colManager));


        cmdManager.registerCommand(new RemoveByIdCommand(colManager));
        cmdManager.registerCommand(new MinByIdCommand(colManager));
        cmdManager.registerCommand(new FilterByMoodCommand(colManager));
        cmdManager.registerCommand(new FilterStartsWithSoundtrackNameCommand(colManager));


        cmdManager.registerCommand(new AddCommand(colManager, inputReader));
        cmdManager.registerCommand(new UpdateCommand(colManager, inputReader));
        cmdManager.registerCommand(new AddIfMaxCommand(colManager, inputReader));
        cmdManager.registerCommand(new AddIfMinCommand(colManager, inputReader));

        cmdManager.registerCommand(new SaveCommand(colManager, fileManager));
        cmdManager.registerCommand(new ExecuteScriptCommand(cmdManager));

    }
}