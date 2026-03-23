package command;

import manager.CollectionManager;
import console.InputReader;
import model.HumanBeing;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Запрашивает у пользователя все поля объекта HumanBeing и добавляет его.
 */
public class AddCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final InputReader inputReader;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     * @param inputReader источник ввода для создания объекта
     */
    public AddCommand(CollectionManager collectionManager, InputReader inputReader) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: на вход команде должен поступать один элемент");
            return;
        }

        System.out.println("Создается новый элемент");
        HumanBeing newPerson = inputReader.readHumanBeing();

        if (newPerson == null) {
            System.out.println("Ошибка во время создания первого элемента");
            return;
        }

        collectionManager.add(newPerson);
        System.out.println("Успешно добавлен элемент ");
    }
}
