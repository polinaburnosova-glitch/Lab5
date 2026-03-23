package command;

import manager.CollectionManager;
import console.InputReader;
import model.HumanBeing;

/**
 * Команда для обновления элемента коллекции по его id.
 * Запрашивает у пользователя новые значения полей и обновляет элемент.
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final InputReader inputReader;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     * @param inputReader источник ввода для создания объекта
     */
    public UpdateCommand(CollectionManager collectionManager, InputReader inputReader) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
    }

    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: команда требует на вход аргумент");
            return;
        }

        if (args.length > 1) {
            System.out.println("Ошибка: команда принимает только один аргумент");
            return;
        }

        long id;
        try {
            id = Long.parseLong(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Ошибка: id - положительное число");
            return;
        }

        HumanBeing existingPerson = collectionManager.getById(id);
        if (existingPerson == null) {
            System.out.println("Ошибка: элемент " + id + " не найден");
            return;
        }

        HumanBeing updatedPerson = inputReader.readHumanBeing();

        if (updatedPerson == null) {
            System.out.println("Ошибка во время создания элемента");
            return;
        }

        collectionManager.updateById(id, updatedPerson);
        System.out.println("Элемент " + id + " обновлен");
    }
}
