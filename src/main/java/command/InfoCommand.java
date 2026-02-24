package command;

import manager.CommandManager;
import manager.CollectionManager;

public class InfoCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "информация о коллекции (тип, дата инициализации, количество элементов)");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда info не принимает аргументы");
        }

        System.out.println("Информация о коллекции:");
        System.out.println("Тип коллекции:" + collectionManager.getCollectionType());
        System.out.println("Дата инициализации коллекции:" + collectionManager.getInitializationDate());
        System.out.println("Количество элементов в коллекции:" + collectionManager.size());
    }
}
