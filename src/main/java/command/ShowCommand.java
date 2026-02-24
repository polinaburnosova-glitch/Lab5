package command;

import manager.CollectionManager;
import  model.HumanBeing;
import java.util.ArrayDeque;

public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("команда show не принимает аргументы");
            return;
        }
        ArrayDeque<HumanBeing> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("Элементы коллекции:");
        int index = 1;
        for (HumanBeing person : collection) {
            System.out.println(index++ + ")" + personToString(person));
        }
    }

    private String personToString(HumanBeing person) {
        StringBuilder sb = new StringBuilder();
        sb.append("HumanBeing{");
        sb.append("id=").append(person.getId());
        sb.append(", name='").append(person.getName()).append("'");
        sb.append(", coordinates=(").append(person.getCoordinates().getX())
                .append(", ").append(person.getCoordinates().getY()).append(")");
        sb.append(", creationDate=").append(person.getCreationDate());
        sb.append(", realHero=").append(person.getRealHero());
        sb.append(", hasToothpick=").append(person.getHasToothpick());
        sb.append(", impactSpeed=").append(person.getImpactSpeed());
        sb.append(", soundtrackName='").append(person.getSoundtrackName()).append("'");
        sb.append(", weaponType=").append(person.getWeaponType());
        sb.append(", mood=").append(person.getMood());
        sb.append(", car=").append(person.getCar().getCool() ? "cool" : "not cool");
        sb.append("}");
        return sb.toString();
    }
}
