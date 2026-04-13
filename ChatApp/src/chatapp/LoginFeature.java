package chatapp;

import java.util.regex.Pattern;

public class LoginFeature {

    // stored user data
    private String storedUsername;
    private String storedPassword;
    private String storedFirstName;
    private String storedLastName;
    private String storedPhoneNumber;
    private String registrationMessage;

    // check if username has underscore and max 5 chars
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    // check if password meets all rules
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        
        // check each requirement
        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?~`].*");

        return length && capital && number && special;
    }

    // check if phone has +27 and 9 digits
    public boolean checkCellPhoneNumber(String phone) {
        if (phone == null) return false;
        // regex for SA phone: +27 then 9 digits
        return Pattern.matches("^\\+27[0-9]{9}$", phone);
    }

    // register user and return appropriate messages
    public String registerUser(String username, String password, String firstName, String lastName, String phone) {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        
        // check username
        if (!checkUserName(username)) {
            message.append("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n");
            valid = false;
        } else {
            message.append("Username successfully captured.\n");
        }
        
        // check password
        if (!checkPasswordComplexity(password)) {
            message.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            valid = false;
        } else {
            message.append("Password successfully captured.\n");
        }
        
        // check phone
        if (!checkCellPhoneNumber(phone)) {
            message.append("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.\n");
            valid = false;
        } else {
            message.append("Cell phone number successfully added.\n");
        }
        
        // if all good, save user data
        if (valid) {
            storedUsername = username;
            storedPassword = password;
            storedFirstName = firstName;
            storedLastName = lastName;
            storedPhoneNumber = phone;
            registrationMessage = "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nUser registered successfully.";
            return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nUser registered successfully.";
        } else {
            registrationMessage = message.toString().trim();
            return registrationMessage;
        }
    }

    // check if login details match stored data
    public boolean loginUser(String username, String password) {
        if (username == null || password == null) return false;
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    // return login status message
    public String returnLoginStatus(boolean status, String firstName, String lastName) {
        if (status) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // getters for testing
    public String getRegistrationMessage() {
        return registrationMessage;
    }
    
    public String getStoredUsername() {
        return storedUsername;
    }
    
    public String getStoredPassword() {
        return storedPassword;
    }
    
    public String getStoredFirstName() {
        return storedFirstName;
    }
    
    public String getStoredLastName() {
        return storedLastName;
    }
    
    public String getStoredPhoneNumber() {
        return storedPhoneNumber;
    }
}