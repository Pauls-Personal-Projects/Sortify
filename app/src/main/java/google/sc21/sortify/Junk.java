package google.sc21.sortify;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Junk {
    private String name;
    private List<String> aliases = new LinkedList<String>();
    private String guidance;
    private String link;

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
        temp = scanner.next().toLowerCase();
        if (temp != null) {
            item.guidance = temp;
            if (item.guidance != null && item.guidance.contains("\"")) {
                item.guidance = item.guidance.substring(1);
                item.guidance = item.guidance + scanner.next();
                while (!(item.guidance.contains("\""))) {
                    item.guidance = item.guidance + scanner.next();
                }
                item.guidance = item.guidance.substring(0, (item.guidance.length()-1));
            }
        }
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
    public String returnInfo() {return guidance;
    }

}
