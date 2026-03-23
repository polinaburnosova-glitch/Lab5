package command;

import manager.CollectionManager;
import model.HumanBeing;

/**
 * Команда для удаления первого элемента из коллекции.
 */
public class RemoveFirstCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда не принимает аргументы");
            return;
        }

        if (collectionManager.size() == 0) {
            System.out.println("Коллекция пуста, удаление невозможно");
            return;
        }

        HumanBeing removed = collectionManager.removeFirst();

        if (removed != null) {
            System.out.println("Первый элемент удален: " + removed);
        }
        else {
            System.out.println("Элемент не удалось удалить");
        }
    }
}
