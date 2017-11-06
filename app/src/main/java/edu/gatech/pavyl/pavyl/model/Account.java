package edu.gatech.pavyl.pavyl.model;

public class Account {
    /** The username of this account */
    private String username;

    /** The password of this account */
    private String password;

    /** The type of this account */
    private AccountType type;

    /**
     * Creates a new account from a username and password, defaulting to "User" account type.
     * @param user - account's username
     * @param pass - account's password
     */
    public Account(String user, String pass) {
        this(AccountType.USER, user, pass);
    }

    /**
     * Creates a new account from a username, password and account type
     * @param t - the type of account
     * @param user - account's username
     * @param pass - account's password
     */
    public Account(AccountType t, String user, String pass) {
        type = t;
        username = user;
        password = pass;
    }

    public static enum AccountType {
        USER,
        ADMIN;
    }
}
