package command;

import manager.CollectionManager;
import manager.FileManager;
import model.HumanBeing;
import java.util.Deque;

public class SaveCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager ) {
        super("SaveCommand", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда не принимает на вход аргументов");
            return;
        }
        try {
            Deque<HumanBeing> collection = collectionManager.getCollection();
            fileManager.saveCollection(collection);
            System.out.println("Коллекция сохранена в файл");
        }
        catch (Exception e) {
            System.out.println("Ошибка во время сохранения коллекции" + e.getMessage());
        }
    }
}
