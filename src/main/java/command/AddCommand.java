package command;

import manager.CollectionManager;
import console.InputReader;
import model.HumanBeing;

/**
 * Команда для добавления нового элемента в коллекцию.
 *
 * <p>Команда создаёт новый объект HumanBeing и добавляет его в коллекцию.
 * Все поля объекта запрашиваются у пользователя по очереди.
 *
 * <p>Формат команды: add
 * <br>После вызова команды пользователь вводит все поля объекта построчно:
 * <ol>
 *   <li>Имя (строка, не может быть пустым)</li>
 *   <li>Координата X (число)</li>
 *   <li>Координата Y (число)</li>
 *   <li>realHero (true/false)</li>
 *   <li>hasToothpick (true/false)</li>
 *   <li>impactSpeed (число, максимум 657)</li>
 *   <li>soundtrackName (строка, не может быть пустым)</li>
 *   <li>weaponType (HAMMER/SHOTGUN/KNIFE/BAT)</li>
 *   <li>mood (SORROW/LONGING/GLOOM/APATHY или Enter для пропуска)</li>
 *   <li>car.cool (true/false)</li>
 * </ol>
 * @see CollectionManager
 * @see InputReader
 * @see UpdateCommand
 */
public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private InputReader inputReader;

    /**
     * Конструктор команды add.
     *
     * @param collectionManager менеджер коллекции, в которую будет добавлен элемент
     * @param inputReader источник ввода для чтения полей объекта
     */
    public AddCommand(CollectionManager collectionManager, InputReader inputReader) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
    }

    /**
     * Устанавливает источник ввода (используется для скриптов)
     */
    public void setInputReader(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    /**
     * Возвращает текущий источник ввода
     */
    public InputReader getInputReader() {
        return inputReader;
    }

    /**
     * Выполняет команду добавления элемента.
     *
     * <p>Алгоритм работы:
     * <ol>
     *   <li>Проверяет, что команда не принимает аргументов</li>
     *   <li>Запрашивает создание объекта через InputReader</li>
     *   <li>При успешном создании добавляет объект в коллекцию через {@link CollectionManager#add(HumanBeing)}</li>
     *   <li>Выводит сообщение об успешном добавлении</li>
     * </ol>
     *
     * @param args аргументы команды (не должны передаваться)
     */
    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Ошибка: команда add не принимает аргументов");
            return;
        }

        HumanBeing newPerson = inputReader.readHumanBeing();

        if (newPerson == null) {
            System.out.println("Ошибка при создании элемента");
            return;
        }

        collectionManager.add(newPerson);
        System.out.println("Элемент успешно добавлен");
    }
}