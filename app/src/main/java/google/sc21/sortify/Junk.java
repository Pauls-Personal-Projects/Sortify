package google.sc21.sortify;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Junk {
    private String name;
    private List<String> aliases = new LinkedList<>();
    private String guidance;
    private String link;
    private float accuracy;

    public Junk(String junkName, List<String> junkAliases, String junkGuidance, String junkURL) {
        name = junkName;
        aliases = junkAliases;
        guidance = junkGuidance;
        link = junkURL;
    }
    private Junk() {}

    public static Junk newItemFromCSV(Scanner scanner) {
        Junk item = new Junk();
        item.name = scanner.next();
        item.link = scanner.next();
        String temp = scanner.next().toLowerCase();
        item.aliases.add(temp);
        if (item.aliases != null && item.aliases.get(0).contains("\"")) {
            item.aliases.set(0,item.aliases.get(0).substring(1));
            item.aliases.add(scanner.next().toLowerCase());
            while (!(item.aliases.get(item.aliases.size() - 1).contains("\""))) {
                item.aliases.add(scanner.next().toLowerCase());
            }
            item.aliases.set((item.aliases.size() - 1),item.aliases.get(item.aliases.size() - 1).substring(0, item.aliases.get(item.aliases.size() - 1).length()-1));
        }
        temp = scanner.next().toLowerCase();
        item.guidance = temp;
        if (item.guidance.contains("\"")) {
            item.guidance = item.guidance.substring(1);
            item.guidance = item.guidance + scanner.next();
            while (!(item.guidance.contains("\""))) {
                item.guidance += scanner.next();
            }
            item.guidance = item.guidance.substring(0, (item.guidance.length()-1));
        }
        item.accuracy = 0;
        return item;
    }

    // Returns how accurate the match is
    public float matches(String label) {
        String input = label.toLowerCase();
        float accuracy = 0;
        char[] inputArray = input.toCharArray();
        if (name.toLowerCase().contains(input)) {
            int matchingLetters = 0;
            char[] compareArray = name.toLowerCase().toCharArray();
            int startPosition = name.toLowerCase().indexOf(input);
            int compareLength = name.length();
            int inputPosition = 0;
            int comparePosition = startPosition;
            boolean stillMatches = true;
            while (stillMatches && comparePosition < compareLength && inputPosition < input.length()) {
                if (inputArray[inputPosition] == compareArray[comparePosition]) {
                    matchingLetters++;
                } else {
                    stillMatches = false;
                }
                comparePosition++;
                inputPosition++;
            }
            accuracy = (float)matchingLetters/(float)compareLength;
        } else if (aliases.contains(input)) {
            for (int alias=0; alias < aliases.size(); alias++) {
                int matchingLetters = 0;
                char[] compareArray = aliases.get(alias).toCharArray();
                int startPosition = aliases.get(alias).indexOf(input);
                int compareLength = aliases.get(alias).length();
                int inputPosition = 0;
                int comparePosition = startPosition;
                boolean stillMatches = true;
                while (stillMatches && comparePosition < compareLength && inputPosition < input.length()) {
                    if (inputArray[inputPosition] == compareArray[comparePosition]) {
                        matchingLetters++;
                    } else {
                        stillMatches = false;
                    }
                    inputPosition++;
                    comparePosition++;
                }
                if (matchingLetters/aliases.get(alias).length() > accuracy) {
                    accuracy = (float)matchingLetters/(float)compareLength;
                }
            }
        }
        return accuracy;
    }

    public String returnName() {return name;}
    public List<String> returnAliases() {return aliases;}
    public String returnInfo() {return guidance;}
    public void setAccuracy(float value) {accuracy = value;}
    public float returnAccurcay() {return accuracy;}


}
