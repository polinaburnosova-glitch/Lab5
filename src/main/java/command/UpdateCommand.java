package command;

import manager.CollectionManager;
import console.AppConsole;
import model.HumanBeing;

public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final AppConsole console;

    public UpdateCommand(CollectionManager collectionManager, AppConsole console) {
        super("UpdateCommand", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: команда требует на взод аргумент");
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
            System.out.println("Ошибка: элемент " + id + "не найден");
            return;
        }

        HumanBeing updatedPerson = console.readHumanBeing();

        if (updatedPerson == null) {
            System.out.println("Ошибка во время слздания элемента");
            return;
        }

        collectionManager.updateById(id, updatedPerson);
        System.out.println("Элемент" + id + "обновлен");
    }
}
