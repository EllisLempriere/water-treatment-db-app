package org.example;

public abstract class Authenticator {

    protected IDbUtils dbUtils;
    protected IConsoleUtils consoleUtils;

    public Authenticator(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        if (dbUtils == null)
            throw new IllegalArgumentException("dbUtils cannot be null");
        if (consoleUtils == null)
            throw new IllegalArgumentException("consoleUtils cannot be null");

        this.dbUtils = dbUtils;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        return getClass().getCanonicalName().hashCode();
    }
}
