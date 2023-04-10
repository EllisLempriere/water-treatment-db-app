package org.example;

public class BasicAuthenticator extends Authenticator implements IAuthenticator {

    public BasicAuthenticator(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }


    @Override
    public boolean authenticate() {
        boolean isAuthenticated = false;

        String userId = consoleUtils.getUserInfo("Enter User ID: ");
        String userPass = consoleUtils.getUserInfo("Enter Password: ");

        isAuthenticated = dbUtils.canConnectToDB(userId, userPass);

        if (!isAuthenticated)
            System.out.println("Invalid login info");

        return isAuthenticated;
    }
}
