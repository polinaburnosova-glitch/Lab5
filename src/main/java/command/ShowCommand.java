package command;

import manager.CollectionManager;
import  model.HumanBeing;
import java.util.Deque;

/**
 * Команда для вывода всех элементов коллекции.
 */
public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор.
     *
     * @param collectionManager менеджер коллекции
     */
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("команда show не принимает аргументы");
            return;
        }
        Deque<HumanBeing> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("Элементы коллекции:");
        int index = 1;
        for (HumanBeing person : collection) {
            System.out.println(index++ + ")" + person.toString());
        }
    }
}
