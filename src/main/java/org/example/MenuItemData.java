package org.example;

public class MenuItemData {

    public final boolean isQuitItem;
    public final IAuthenticator authenticator;
    public final IAction action;


    public MenuItemData(boolean isQuitItem, IAuthenticator authenticator, IAction action) {
        this.isQuitItem = isQuitItem;
        this.authenticator = authenticator;
        this.action = action;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        final MenuItemData other = (MenuItemData) obj;

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
        hash = 53 * hash + (this.isQuitItem ? 1 : 0);
        hash = 53 * hash + (this.authenticator != null ? this.authenticator.hashCode() : 0);
        hash = 53 * hash + (this.action != null ? this.action.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String className = authenticator != null ? authenticator.getClass().getTypeName() : "null";
        return "MenuItemData(" + isQuitItem + ", " + className + ")";
    }
}
