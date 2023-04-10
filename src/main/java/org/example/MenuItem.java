package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuItem {

    public final char character;
    public final String title;
    private List<MenuItem> subItems = new ArrayList<>();
    private MenuItem parentItem;
    public final boolean isQuitItem;
    public final IAuthenticator authenticator;
    public final IAction action;



    public MenuItem(char character, String title, boolean quit, IAuthenticator authenticator, IAction action) {
        if (title == null)
            throw new IllegalArgumentException();

        this.character = character;
        this.title = title;
        this.isQuitItem = quit;
        this.authenticator = authenticator;
        this.action = action;
    }


    public MenuItem(char character, String title) {
        this(character, title, false, null, null);
    }


    public MenuItem(char character, String title, boolean isQuitItem) {
        this(character, title, isQuitItem, null, null);
    }


    public void addSubItems(List<MenuItem> items) {
        if (items == null)
            throw new IllegalArgumentException();

        for (MenuItem item : items) {
            if (item == null)
                continue;
            subItems.add(item);
            item.parentItem = this;
        }
    }


    public List<MenuItem> getSubItems() {
        return Collections.unmodifiableList(subItems);
    }


    public MenuItem getParentItem() {
        return parentItem;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        final MenuItem other = (MenuItem) obj;

        if (this.character != other.character)
            return false;

        if (!this.title.equals(other.title))
            return false;

        if (!this.subItems.equals(other.subItems))
            return false;

        if (this.isQuitItem != other.isQuitItem)
            return false;

        if ((this.authenticator != null && other.authenticator == null) ||
                (this.authenticator == null && other.authenticator != null))
            return false;

        if (this.authenticator != null && !this.authenticator.equals(other.authenticator))
            return false;

        if ((this.action != null && other.action == null) ||
                (this.action == null && other.action != null))
            return false;

        if (this.action != null && !this.action.equals(other.action))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.character;
        hash = 53 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 53 * hash + (this.subItems.hashCode());
        hash = 53 * hash + (this.isQuitItem ? 1 : 0);
        hash = 53 * hash + (this.authenticator != null ? this.authenticator.hashCode() : 0);
        hash = 53 * hash + (this.action != null ? this.action.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("org.example.MenuItem('");
        sb.append(character);
        sb.append("', \"");
        sb.append(title);
        sb.append("\", subItemCount:");
        sb.append(subItems.size());
        sb.append(", ");
        sb.append(isQuitItem);
        sb.append(")");

        return sb.toString();
    }
}
