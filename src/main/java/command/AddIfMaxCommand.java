package command;

import manager.CollectionManager;
import java.util.Optional;
import model.HumanBeing;
import console.InputReader;

/**
 * Команда для добавления элемента в коллекцию, если его значение превышает наибольший элемент.
 *
 * <p>Команда создаёт новый объект HumanBeing и добавляет его в коллекцию только в том случае,
 * если новый объект больше (по id) текущего максимального элемента коллекции.
 * Если коллекция пуста, элемент добавляется без проверки.
 *
 * <p>Сравнение выполняется с использованием метода {@link HumanBeing#compareTo(HumanBeing)},
 * который сравнивает элементы по id. Таким образом, "больше" означает "имеет больший id".
 *
 * <p>Формат команды: add_if_max
 * <br>После вызова команды пользователь вводит все поля объекта по очереди.
 *
 * @see CollectionManager
 * @see InputReader
 * @see AddIfMinCommand
 */
public class AddIfMaxCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private InputReader inputReader;

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

    /**
     * Устанавливает источник ввода.
     * Используется при выполнении скриптов для временной замены источника.
     *
     * @param inputReader новый источник ввода
     */
    public void setInputReader(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    /**
     * Возвращает текущий источник ввода.
     *
     * @return текущий источник ввода
     */
    public InputReader getInputReader() {
        return inputReader;
    }

    /**
     * Выполняет команду добавления элемента с условием "больше максимального".
     *
     * <p>Алгоритм работы:
     * <ol>
     *   <li>Проверяет, что команда не принимает аргументов</li>
     *   <li>Получает максимальный элемент коллекции (через {@link CollectionManager#getMax()})</li>
     *   <li>Создаёт новый объект HumanBeing через InputReader</li>
     *   <li>Если коллекция пуста — добавляет элемент без проверки</li>
     *   <li>Если новый объект больше максимального (compareTo() > 0) — добавляет элемент</li>
     *   <li>В противном случае выводит сообщение об отказе</li>
     * </ol>
     *
     * @param args аргументы команды (не должны передаваться)
     */
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
