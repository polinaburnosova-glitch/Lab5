package manager;

import model.*;
import java.io.*;
import java.util.ArrayDeque;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {
    private final String fileName;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public ArrayDeque<HumanBeing> loadCollection() throws IOException {
        ArrayDeque<HumanBeing> collection = new ArrayDeque<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Файл не найден, создается пустая коллекция");
            return collection;
        }

        if (!file.canRead()) {
            throw new IOException("Нет прав для чтения файла");
        }

        StringBuilder xmlContent = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                xmlContent.append(new String(buffer, 0, bytesRead, "UTF-8"));
            }
        }

        String xml = xmlContent.toString().trim();
        if (xml.isEmpty() || !xml.startsWith("<?xml")) {
            return collection;
        }
        return parseXmlToCollection(xml);
    }

    public void saveCollection(ArrayDeque<HumanBeing> collection) throws IOException {
        File file = new File(fileName);
        if (file.exists() && !file.canWrite()) {
            throw new IOException("Нет прав для записи в файл");
        }

        StringBuilder xml  = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<collection>\n");

        for (HumanBeing person : collection) {
            xml.append(humanToXml(person));
        }
        xml.append("</collection>");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(xml.toString().getBytes("UTF-8"));
            fos.flush();
        }
    }

    private ArrayDeque<HumanBeing> parseXmlToCollection(String xml) {
        ArrayDeque<HumanBeing> collection = new ArrayDeque<>();
        String[] parts = xml.split("</?humanbeing>");

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty() || !part.contains("<")) {
                continue;
            }
            try {
                HumanBeing person = parseHumanBeing(part);
                if (person != null) {
                    collection.add(person);
                }
            }
            catch (Exception e) {
                System.err.println("Ошибка во время парсинга элемента");
            }
        }
        return collection;
    }

    private HumanBeing parseHumanBeing(String xml) {
        try {
            Long id = Long.parseLong(extractTag(xml, "id"));
            String name = extractTag(xml, "name");

            String coordsXml = extractTag(xml, "coordinates");
            Double x = Double.parseDouble(extractTag(coordsXml, "x"));
            Float y = Float.parseFloat(extractTag(coordsXml, "y"));
            Coordinates coordinates = new Coordinates(x, y);

            String creationDateStr = extractTag(xml, "creationDate");
            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr, DATE_FORMATTER);

            Boolean realHero = Boolean.parseBoolean(extractTag(xml, "realHero"));
            Boolean hasToothpick = Boolean.parseBoolean(extractTag(xml, "hasTopthpick"));
            float impactSpeed = Float.parseFloat(extractTag(xml, "impactSpeed"));
            String soundtrackName = extractTag(xml, "soundtrackName");

            WeaponType weaponType = WeaponType.valueOf(extractTag(xml, "weaponType"));
            String moodStr = extractTagNullable(xml, "mood");
            Mood mood = null;
            if (moodStr != null && !moodStr.isEmpty()) {
                mood = Mood.valueOf(moodStr);
            }

            String carXml = extractTag(xml, "car");
            Boolean cool = Boolean.parseBoolean(extractTag(carXml, "cool"));
            Car car = new Car(cool);

            HumanBeing person = new HumanBeing(id, name, coordinates, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car);
            person.setCreationDate(creationDate);
            return person;
        }
        catch (Exception e){
            System.err.println("Ошибка парсинга HumanBeing");
            return null;
        }
    }

    private String humanToXml(HumanBeing person) {
        StringBuilder xml = new StringBuilder();
        xml.append("  <humanbeing>\n");
        xml.append("    <id>").append(person.getId()).append("</id>\n");
        xml.append("    <name>").append(escapeXml(person.getName())).append("</name>\n");
        xml.append("    <coordinates>\n");
        xml.append("      <x>").append(person.getCoordinates().getX()).append("</x>\n");
        xml.append("      <y>").append(person.getCoordinates().getY()).append("</y>\n");
        xml.append("    </coordinates>\n");
        xml.append("    <creationDate>").append(person.getCreationDate().format(DATE_FORMATTER)).append("</creationDate>\n");
        xml.append("    <realHero>").append(person.getRealHero()).append("</realHero>\n");
        xml.append("    <hasToothpick>").append(person.getHasToothpick()).append("</hasToothpick>\n");
        xml.append("    <impactSpeed>").append(person.getImpactSpeed()).append("</impactSpeed>\n");
        xml.append("    <soundtrackName>").append(escapeXml(person.getSoundtrackName())).append("</soundtrackName>\n");
        xml.append("    <weaponType>").append(person.getWeaponType()).append("</weaponType>\n");

        if (person.getMood() != null) {
            xml.append("    <mood>").append(person.getMood()).append("</mood>\n");
        } else {
            xml.append("    <mood></mood>\n");
        }

        xml.append("    <car>\n");
        xml.append("      <cool>").append(person.getCar().getCool()).append("</cool>\n");
        xml.append("    </car>\n");
        xml.append("  </humanbeing>\n");

        return xml.toString();
    }


    private String extractTag(String xml, String tagName) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";

        int start = xml.indexOf(openTag);
        if (start == -1) return "";

        int end = xml.indexOf(closeTag, start);
        if (end == -1) return "";

        return xml.substring(start + openTag.length(), end).trim();
    }

    private String extractTagNullable(String xml, String tagName) {
        String value = extractTag(xml, tagName);
        return value.isEmpty() ? null : value;
    }

    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
