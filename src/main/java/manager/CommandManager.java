package manager;

import command.AbstractCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, AbstractCommand> commands = new HashMap<>();

    public void registerCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

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

        public Map<String, AbstractCommand> getCommands () {
            return new HashMap<>(commands);
        }
}