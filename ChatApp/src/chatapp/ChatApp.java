package chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        LoginFeature login = new LoginFeature();

        String username;
        String password;
        String phone;
        String firstName;
        String lastName;

        System.out.println("===== CHAT APP REGISTRATION =====");

        // get first name
        System.out.print("Enter first name: ");
        firstName = input.nextLine();

        // get last name
        System.out.print("Enter last name: ");
        lastName = input.nextLine();

        // username loop - keeps asking until valid
        do {
            System.out.print("Enter username: ");
            username = input.nextLine();

            if (!login.checkUserName(username)) {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }

        } while (!login.checkUserName(username));

        System.out.println("Username successfully captured.\n");

        // password loop - keeps asking until valid
        do {
            System.out.print("Enter password: ");
            password = input.nextLine();

            if (!login.checkPasswordComplexity(password)) {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }

        } while (!login.checkPasswordComplexity(password));

        System.out.println("Password successfully captured.\n");

        // phone loop - keeps asking until valid
        do {
            System.out.print("Enter cell phone number (+27xxxxxxxxx): ");
            phone = input.nextLine();

            if (!login.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.");
            }

        } while (!login.checkCellPhoneNumber(phone));

        System.out.println("Cell phone number successfully added.\n");

        // register user with all details
        String registrationResult = login.registerUser(username, password, firstName, lastName, phone);
        System.out.println(registrationResult);

        // login section
        System.out.println("\n===== LOGIN =====");

        boolean loggedIn = false;

        // keep asking until login successful
        while (!loggedIn) {

            System.out.print("Enter username: ");
            String loginUser = input.nextLine();

            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            loggedIn = login.loginUser(loginUser, loginPass);

            if (loggedIn) {

                System.out.println(login.returnLoginStatus(true, firstName, lastName));

            } else {

                System.out.println("Username or password incorrect, please try again.\n");
            }
        }

        System.out.println("\nYou are now logged into ChatApp.");
    }
}