package command;

/**
 * Абстрактный базовый класс для всех команд.
 * Содержит имя команды, её описание и абстрактный метод execute.
 * Все конкретные команды должны наследовать этот класс.
 */
public abstract class AbstractCommand {
    private final String name;
    private final String description;

    /**
     * Конструктор.
     *
     * @param name имя команды (например, "help")
     * @param description описание команды для вывода в help
     */
    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды (без имени команды)
     */
    public abstract void execute(String[] args);
}
