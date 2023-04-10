package org.example;

import java.util.ArrayList;
import java.util.List;

public class View {

    private static final int outputLength = 70;
    private static final String headerTitle = "Welcome to the DDS for Washington's Water Treatment Facilities";


    public List<String> render(Menu menu) {
        List<String> renderedMenu = new ArrayList<>();

        renderedMenu.addAll(renderHeader(menu));

        StringBuilder menuItem = new StringBuilder();
        for (MenuItem item : menu.getItems()) {
            menuItem.append(item.character);
            menuItem.append(". ");
            menuItem.append(item.title);

            renderedMenu.add(centerString(menuItem.toString()));

            menuItem.setLength(0);
        }

        return renderedMenu;
    }


    private List<String> renderHeader(Menu menu) {
        List<String> returnValue = new ArrayList<>();

        returnValue.add("*".repeat(outputLength));
        returnValue.add(centerString(headerTitle));
        returnValue.add(centerString(menu.getTitle()));
        returnValue.add("*".repeat(outputLength));

        return returnValue;
    }


    private String centerString(String str) {
        int leadingSpaces = Math.max(0, (outputLength - str.length()) / 2);

        return " ".repeat(leadingSpaces) + str;
    }
}
