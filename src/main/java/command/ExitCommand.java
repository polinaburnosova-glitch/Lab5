package command;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("команда exit не принимает аргументы");
            return;
        }

        System.out.println("Выполнение программы завершено");
        System.exit(0);
    }
}
