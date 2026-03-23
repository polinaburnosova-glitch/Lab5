package manager;

import command.AbstractCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Менеджер для управления командами.
 * Регистрирует команды и выполняет их по строке ввода пользователя.
 *
 * @author Your Name
 * @version 1.0
 * @see AbstractCommand
 * @see console.AppConsole
 */
public class CommandManager {
    private final Map<String, AbstractCommand> commands = new HashMap<>();

    /**
     * Регистрирует команду в менеджере.
     *
     * @param command команда для регистрации
     */
    public void registerCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

    /**
     * Выполняет команду по строке ввода.
     * Разбирает строку на имя команды и аргументы, находит соответствующую
     * команду и вызывает её метод execute.
     *
     * @param userInput строка ввода пользователя
     */
    public void executeCommand(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return;
        }

        String[] inputParts = userInput.trim().split("\\s+");
        String commandName = inputParts[0].toLowerCase();

        String[] commandArguments = Arrays.copyOfRange(inputParts, 1, inputParts.length);

        AbstractCommand command = commands.get(commandName);
        if (command == null) {
            System.out.println("Команда '" + commandName + "' не найдена");
            System.out.println("Введите команду 'help' для вывода списка всех команд");
            return;
        }

        try {
            command.execute(commandArguments);
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    /**
     * Возвращает копию словаря зарегистрированных команд.
     *
     * @return копия словаря команд
     */
        public Map<String, AbstractCommand> getCommands () {
            return new HashMap<>(commands);
        }
}