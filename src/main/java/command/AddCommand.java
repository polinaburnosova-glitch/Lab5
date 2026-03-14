package command;

import manager.CollectionManager;
import console.AppConsole;
import model.HumanBeing;

public class AddCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final AppConsole console;

    public AddCommand(CollectionManager collectionManager, AppConsole console) {
        super("AddCommand", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: на вход команде должен поступать один элемент");
            return;
        }

        System.out.println("Создается новый элемент");
        HumanBeing person1 = console.readHumanBeing();

        if (person1 == null) {
            System.out.println("Ошибка во время создания первого элемента");
            return;
        }

        collectionManager.add(person1);
        System.out.println("Успешно добавлен элемент " + person1.getId());
    }
}
