package command;

public abstract class AbstractCommand implements Command{
    private final String name;
    private final String description;

    public AbstractCommand(String description, String name) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
