package console;

import manager.CommandManager;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.*;


public class AppConsole {
    private final CommandManager commandManager;
    private boolean isRunning = true;
    private final Scanner scanner;

    public AppConsole(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.scanner = new Scanner(System.in);
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

    public HumanBeing readHumanBeing() {
        try {
            System.out.println("Введите имя:");
            String name = scanner.nextLine().trim();

            System.out.println("Введите координату x:");
            double x = Double.parseDouble(scanner.nextLine().trim());

            System.out.println("Введите координату y:");
            float y = Float.parseFloat(scanner.nextLine().trim());

            Coordinates coordinates = new Coordinates(x, y);

            System.out.println("realHero (true/false):");
            boolean realHero = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.println("hasToothpick (true/false):");
            boolean hasToothpick = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.println("impactSpeed (макс. 657):");
            float impactSpeed = Float.parseFloat(scanner.nextLine().trim());

            System.out.println("soundtrackName:");
            String soundtrackName = scanner.nextLine().trim();

            System.out.println("weaponType (HAMMER, SHOTGUN, KNIFE, BAT):");
            WeaponType weaponType = WeaponType.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.println("mood (Enter - пропустить):");
            String moodInput = scanner.nextLine().trim();
            Mood mood = moodInput.isEmpty() ? null : Mood.valueOf(moodInput.toUpperCase());

            System.out.println("car.cool (true/false):");
            boolean cool = Boolean.parseBoolean(scanner.nextLine().trim());
            Car car = new Car(cool);

            return new HumanBeing(null, name, coordinates, realHero, hasToothpick,
                    impactSpeed, soundtrackName, weaponType, mood, car);

        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return null;
        }
    }

    public void stop() {
        isRunning = false;
    }
}

