package command;

import manager.CollectionManager;
import java.util.Optional;
import model.HumanBeing;
import console.InputReader;

/**
 * Команда для добавления элемента, если он больше максимального.
 * Создаёт новый элемент и добавляет его, если он превышает максимальный.
 */
public class AddIfMaxCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final InputReader inputReader;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     * @param inputReader источник ввода для создания объекта
     */
    public AddIfMaxCommand(CollectionManager collectionManager, InputReader inputReader) {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда не принимает на вход больше одного элемента");
            return;
        }

        Optional<HumanBeing> maxElement = collectionManager.getMax();

        HumanBeing newPerson = inputReader.readHumanBeing();

        if (newPerson == null) {
            System.out.println("Ошибка во время создания нового элемента");
            return;
        }

        if (maxElement.isEmpty()) {
            collectionManager.add(newPerson);
            System.out.println("Был добавлен элемент " + newPerson.getId());
            return;
        }

        if (newPerson.compareTo(maxElement.get()) > 0) {
            collectionManager.add(newPerson);
            System.out.println("Добавлен элемент " + newPerson.getId());
        }
        else {
            System.out.println("Элемент не был добавлен");
        }
    }
}
