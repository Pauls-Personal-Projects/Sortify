package google.sc21.sortify;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Junk {
    private String name;
    private List<String> aliases = new LinkedList<String>();
    private String binColour;

    public Junk(String junkName, List<String> junkAliases, String junkBinColour) {
        name = junkName;
        aliases = junkAliases;
        binColour = junkBinColour;
    }
    private Junk() {}

    public static Junk newItemFromCSV(Scanner scanner) {
        Junk item = new Junk();
        item.name = scanner.next();
        String temp = scanner.next().toLowerCase();
        if (temp != null) {
            item.aliases.add(temp);
            if (item.aliases != null && item.aliases.get(0).contains("\"")) {
                item.aliases.set(0,item.aliases.get(0).substring(1));
                item.aliases.add(scanner.next().toLowerCase());
                while (!(item.aliases.get(item.aliases.size() - 1).contains("\""))) {
                    item.aliases.add(scanner.next().toLowerCase());
                }
                item.aliases.set((item.aliases.size() - 1),item.aliases.get(item.aliases.size() - 1).substring(0, item.aliases.get(item.aliases.size() - 1).length()-1));
            }
        }
        item.binColour = scanner.next();
        return item;
    }

    public boolean matches(String label) {
        if (name.toLowerCase().contains(label.toLowerCase()) || aliases.contains(label.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public String returnName() {return name;}
    public List<String> returnAliases() {return aliases;}
    public String returnInfo() {return binColour;
    }

}
