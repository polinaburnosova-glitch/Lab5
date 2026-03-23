package console;

import manager.CommandManager;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Консоль для интерактивного взаимодействия с пользователем.
 * Запускает главный цикл программы, читает команды пользователя
 * и передаёт их в CommandManager для выполнения.
 */
public class AppConsole {  // Убираем "implements InputReader"
    private final CommandManager commandManager;
    private boolean isRunning = true;
    private Scanner scanner;

    /**
     * Конструктор.
     *
     * @param commandManager менеджер команд для выполнения
     */
    public AppConsole(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Запускает основной цикл чтения команд.
     * Работает до тех пор, пока isRunning = true и есть строки ввода.
     *
     * @param inStream поток ввода (обычно System.in)
     */
    public void run(InputStreamReader inStream) {
        this.scanner = new Scanner(inStream);

        System.out.println("Введите 'help' для вывода списка команд");

        while (isRunning && this.scanner.hasNextLine()) {
            try {
                System.out.print("> ");
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

    /**
     * Останавливает работу консоли.
     */
    public void stop() {
        isRunning = false;
    }
}