package manager;

import command.Command;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Несуществующая команда");
            System.out.println("Введите help для списка всех команд");
            return;
        }
        try {
            command.execute(args);
        }
        catch (Exception e) {
            System.out.println("Ошибка при выполнении команды");
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }


}
