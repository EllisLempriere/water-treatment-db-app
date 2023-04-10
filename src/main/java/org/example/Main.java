package org.example;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        View view = new View();

        while (true) {
            List<String> consoleLines = view.render(menu);
            outputToConsole(consoleLines);

            Character choice = getUserChoice();
            if (choice == null)
                continue;

            MenuItemData m = menu.getSubItemData(choice);
            if (m == null)
                continue;

            if (m.isQuitItem)
                break;
            if (m.authenticator != null) {
                boolean isAuthenticated = m.authenticator.authenticate();
                if (!isAuthenticated)
                    continue;
            }

            menu.makeSelection(choice);
            if (m.action == null)
                continue;

            m.action.execute();
        }
    }


    private static void outputToConsole(List<String> lines) {
        System.out.println();

        for (String line : lines)
            System.out.println(line);
    }


    private static Character getUserChoice() {
        System.out.print("\nType in your option: ");

        Character ch = null;
        try {
            int input = System.in.read();
            while (input != -1 && input != '\n') {
                ch = (char) input;
                input = System.in.read();
            }
        } catch (IOException ignored) {
        }

        return ch;
    }
}

// TODO
// Consider creating custom exceptions instead of built-ins
// Ensure descriptive messages added to exceptions