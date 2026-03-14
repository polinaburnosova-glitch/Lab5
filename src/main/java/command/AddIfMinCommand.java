package command;

import manager.CollectionManager;
import java.util.Optional;
import model.HumanBeing;
import console.AppConsole;

public class AddIfMinCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final AppConsole console;

    public AddIfMinCommand(CollectionManager collectionManager, AppConsole console) {
        super("AddIfMinCommand", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда не принимает на вход больше одного элемента");
            return;
        }

        Optional<HumanBeing> maxElement = collectionManager.getMin();

        HumanBeing newPerson = console.readHumanBeing();

        if (newPerson == null) {
            System.out.println("Ошибка во время создания нового элемента");
            return;
        }

        if (maxElement.isEmpty()) {
            collectionManager.add(newPerson);
            System.out.println("Был добавлен элемент " + newPerson.getId());
            return;
        }

        if (newPerson.compareTo(maxElement.get()) < 0) {
            collectionManager.add(newPerson);
            System.out.println("Добавлен элемент " + newPerson.getId());
        }
        else {
            System.out.println("Элемент не был добавлен");
        }
    }
}
