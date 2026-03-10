package console;

import manager.CommandManager;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppConsole {
    private final CommandManager commandManager;
    private boolean isRunning = true;

    public AppConsole(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void run(InputStreamReader inStream) {
        Scanner scanner = new Scanner(inStream);

        System.out.println("Введите 'help' для вывода списка команд");

        while (isRunning && scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();

                if (input.isBlank()) {
                    continue;
                }

                commandManager.executeCommand(input);

            } catch (NoSuchElementException e) {
                System.out.println("Ошибка ввода, программа завершена");
                System.exit(1);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}

