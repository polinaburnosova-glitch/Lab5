package command;

import manager.CollectionManager;
import model.HumanBeing;
import java.util.Deque;

public class FilterStartsWithSoundtrackNameCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterStartsWithSoundtrackNameCommand(CollectionManager collectionManager) {
        super("FilterStartsWithSoundtrackNameCommand", "вывести элементы, значение поля soundtrackName которых начинается с заданной подстроки");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: команда требует аргумент на вход");
            return;
        }

        if (args.length > 1) {
            System.out.println("Ошибка: команда принимает на вход только один аргумент");
            return;
        }

        String prefix = args[0];

        Deque<HumanBeing> filtered = collectionManager.filterSoundtrackName(prefix);

        if (filtered.isEmpty()) {
            System.out.println("Элементы с " + prefix + " не найдены");
            return;
        }

        System.out.println("Элементы с " + prefix + ":");
        int index = 1;
        for (HumanBeing person : filtered) {
            System.out.println(index++ + ") " + person);
        }
    }
}
