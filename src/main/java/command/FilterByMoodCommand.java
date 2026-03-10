package command;

import manager.CollectionManager;
import model.Mood;
import model.HumanBeing;
import java.util.Arrays;
import java.util.Deque;

public class FilterByMoodCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    public FilterByMoodCommand(CollectionManager collectionManager) {
        super("FilterByMood", "вывести элементы, значение поля mood которых равно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: команда требует на вход аргумент");
            System.out.println("Список доступных значений: " + Arrays.toString(Mood.values()));
            return;
        }

        if (args.length > 1) {
            System.out.println("Ошибка: команда принимает на вход только один аргумент");
            return;
        }

        Mood mood;
        try {
            mood = Mood.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + args[0] + " не существует");
            System.out.println("Доступные значения: " + Arrays.toString(Mood.values()));
            return;
        }

        Deque<HumanBeing> filtered = collectionManager.filterByMood(mood);

        if (filtered.isEmpty()) {
            System.out.println("Элемент не найден");
            return;
        }

        System.out.println("Элементы с настроением " + mood + ":");
        int index = 1;
        for (HumanBeing person : filtered) {
            System.out.println(index++ + ") " + person);
        }
    }
}
