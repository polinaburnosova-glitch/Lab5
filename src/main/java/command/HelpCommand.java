package command;

import manager.CommandManager;
import manager.CollectionManager;
import java.util.Map;

/**
 * Команда для вывода справки по доступным командам.
 * Выводит список всех зарегистрированных команд с их описаниями.
 */
public class HelpCommand extends AbstractCommand {
    private final CommandManager commandManager;

    /**
     * Конструктор.
     *
     * @param commandManager менеджер команд
     */

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
        System.out.println("Достyпные команды: ");
        Map<String, AbstractCommand> commands = commandManager.getCommands();
        for (AbstractCommand command : commands.values()) {
            System.out.println(command.getName());
            System.out.println(command.getDescription());
        }
    }
}
