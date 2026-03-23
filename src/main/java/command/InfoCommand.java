package command;

import manager.CommandManager;
import manager.CollectionManager;

/**
 * Команда для вывода информации о коллекции.
 * Выводит тип коллекции, дату инициализации и количество элементов.
 */
public class InfoCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     */
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "информация о коллекции (тип, дата инициализации, количество элементов)");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда info не принимает аргументы");
            return;
        }

        System.out.println("Информация о коллекции:");
        System.out.println("Тип коллекции:" + collectionManager.getCollectionType());
        System.out.println("Дата инициализации коллекции:" + collectionManager.getInitializationDate());
        System.out.println("Количество элементов в коллекции:" + collectionManager.size());
    }
}
