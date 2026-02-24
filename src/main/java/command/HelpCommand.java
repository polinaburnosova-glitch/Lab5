package command;

import manager.CommandManager;
import manager.CollectionManager;
import java.util.Map;

public class HelpCommand extends AbstractCommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "вывести все доступные команды");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("команда help не принимает аргументы");
            return;
        }
        System.out.println("Достыпные команды: ");
        Map<String, Command> commands = commandManager.getCommands();
        for (Command command : commands.values()) {
            System.out.println(command.getName());
            System.out.println(command.getDescription());
        }
    }
}
