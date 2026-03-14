package org.example;

import manager.CollectionManager;
import manager.CommandManager;
import manager.FileManager;
import console.AppConsole;
import command.*;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла, содержащего коллекцию");
            return;
        }

        String fileName = args[0];

        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        FileManager fileManager = new FileManager(fileName);

        try {
            collectionManager.initializeCollection(fileManager.loadCollection());
            System.out.println("Коллекция загружена из файла " + fileName);
        }
        catch (Exception e) {
            System.out.println("Не удалось загрузить коллекцию: " + e.getMessage());
        }

        registerCommands(commandManager, collectionManager, fileManager);


        AppConsole console = new AppConsole(commandManager);
        console.run(new InputStreamReader(System.in));
    }

    private static void registerCommands(CommandManager cmdManager,
                                         CollectionManager colManager,
                                         FileManager fileManager) {


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


        AppConsole console = new AppConsole(cmdManager);  // Создаем консоль
        cmdManager.registerCommand(new AddCommand(colManager, console));
        cmdManager.registerCommand(new UpdateCommand(colManager, console));
        cmdManager.registerCommand(new AddIfMaxCommand(colManager, console));
        cmdManager.registerCommand(new AddIfMinCommand(colManager, console));

        cmdManager.registerCommand(new SaveCommand(colManager, fileManager));
        cmdManager.registerCommand(new ExecuteScriptCommand(cmdManager, console));

    }
}