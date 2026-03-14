package command;

import manager.CollectionManager;
import model.HumanBeing;
import java.util.Optional;

public class MinByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public MinByIdCommand(CollectionManager collectionManager) {
        super("MinByIdCommand", "вывести любой объект из коллекции, значение поля id которого является минимальным");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда не принимает на вход аргументы");
            return;
        }

        Optional<HumanBeing> minElement = collectionManager.getMinById();

        if (minElement.isEmpty()) {
            System.out.println("В коллекции нет элементов");
            return;
        }

        HumanBeing person = minElement.get();
        System.out.println("Элемент с минимальным id: " + person.getId());
    }
}
