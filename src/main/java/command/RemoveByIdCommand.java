package command;

import manager.CollectionManager;

/**
 * Команда для удаления элемента по его id.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: необходимо указать id удаляемого элемента");
            return;
        }

        if (args.length > 1) {
            System.out.println("Ошибка: команда принимает только один аргумент");
            return;
        }

        try {
            Long id = Long.parseLong(args[0]);

            if (id <= 0) {
                System.out.println("Ошибка: id не может быть отрицательным");
                return;
            }

            boolean removed = collectionManager.removeById(id);

            if (removed) {
                System.out.println("Элемент " + id + " удален");
            } else {
                System.out.println("Элемент " + id + " не найден");

            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: id элемента должен быть целым положительным числом");
        }
    }
}

