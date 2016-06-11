package controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by qzmp on 11.06.2016.
 */
public class DataLoader {
    public static LinkedList<String> loadNames() {
        LinkedList<String> names = new LinkedList<String>();

        try {
            Scanner scanner = new Scanner(new FileReader("VPN Comparison - Detailed.csv"));
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNext()) {
                names.add(scanner.next());
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }
}
