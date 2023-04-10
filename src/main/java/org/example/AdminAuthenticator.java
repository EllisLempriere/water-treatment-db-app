package org.example;

public class AdminAuthenticator extends Authenticator implements IAuthenticator {

    private final String adminId = "<Fill Admin Id Here>";
    private final String adminPass = "<Fill Admin Password Here>";

    public AdminAuthenticator(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }


    @Override
    public boolean authenticate() {
        boolean isAuthenticated = false;

        String userId = consoleUtils.getUserInfo("Enter User ID: ");
        String userPass = consoleUtils.getUserInfo("Enter Password: ");

        // TODO - implement more specific test for admin access
        // To satisfy business requirements the current implementation should suffice
        // We only ever expect one admin user
        isAuthenticated = dbUtils.canConnectToDB(userId, userPass);

        if (isAdminCredentials(userId, userPass) && !isAuthenticated)
            System.out.println("Invalid login info");

        return isAuthenticated;
    }


    private boolean isAdminCredentials(String userId, String userPass) {
        return userId.equals(adminId) && userPass.equals(adminPass);
    }
}
