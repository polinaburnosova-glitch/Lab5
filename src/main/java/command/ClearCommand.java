package command;

import manager.CollectionManager;

/**
 * Команда для очистки коллекции.
 * Удаляет все элементы из коллекции.
 */
public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("команда clear не принимает аргументы");
            return;
        }

        collectionManager.clear();
        System.out.println("Коллекция очищена успешно");
    }
}
