package chatapp;

import org.testng.annotations.*;
import org.testng.Assert;

public class LoginFeatureNGTest {

    private LoginFeature login;

    @BeforeMethod
    public void setUp() {
        login = new LoginFeature();
    }

    @AfterMethod
    public void tearDown() {
        login = null;
    }

    // test valid username
    @Test
    public void testUsernameCorrectlyFormatted() {
        System.out.println("\n=== Testing correctly formatted username: kyl_1 ===");
        boolean result = login.checkUserName("kyl_1");
        System.out.println("checkUserName result: " + result);
        Assert.assertTrue(result, "Username should contain underscore and be ≤5 characters");
        
        String registerResult = login.registerUser("kyl_1", "Ch&sec@ke99!", "Kyle", "Smith", "+27838968976");
        System.out.println("registerUser result: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Username successfully captured"), 
            "should confirm username capture");
    }

    // test invalid username
    @Test
    public void testUsernameIncorrectlyFormatted() {
        System.out.println("\n=== Testing incorrectly formatted username: kyle!!!!!! ===");
        boolean result = login.checkUserName("kyle!!!!!!");
        System.out.println("checkUserName result: " + result);
        Assert.assertFalse(result, "username without underscore or >5 chars is invalid");
        
        String registerResult = login.registerUser("kyle!!!!!!", "Ch&sec@ke99!", "Kyle", "Smith", "+27838968976");
        System.out.println("registerUser result: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length."), 
            "should show exact username error");
    }

    // test valid password
    @Test
    public void testPasswordMeetsComplexity() {
        System.out.println("\n=== Testing valid password: Ch&sec@ke99! ===");
        boolean result = login.checkPasswordComplexity("Ch&sec@ke99!");
        System.out.println("checkPasswordComplexity result: " + result);
        Assert.assertTrue(result, "password should meet all rules");
        
        String registerResult = login.registerUser("kyl_1", "Ch&sec@ke99!", "Kyle", "Smith", "+27838968976");
        System.out.println("registerUser result: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Password successfully captured"), 
            "should confirm password capture");
    }

    // test invalid password
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        System.out.println("\n=== Testing simple password: password ===");
        boolean result = login.checkPasswordComplexity("password");
        System.out.println("checkPasswordComplexity result: " + result);
        Assert.assertFalse(result, "simple password should fail");
        
        String registerResult = login.registerUser("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        System.out.println("registerUser result: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character."), 
            "should show exact password error");
    }

    // test valid phone
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        System.out.println("\n=== Testing valid cell: +27838968976 ===");
        boolean result = login.checkCellPhoneNumber("+27838968976");
        System.out.println("checkCellPhoneNumber result: " + result);
        Assert.assertTrue(result, "+27 and 9 digits should be valid");
        
        String registerResult = login.registerUser("kyl_1", "Ch&sec@ke99!", "Kyle", "Smith", "+27838968976");
        System.out.println("registerUser result: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Cell phone number successfully added"), 
            "should confirm phone capture");
    }

    // test invalid phone
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        System.out.println("\n=== Testing invalid cell: 08966553 ===");
        boolean result = login.checkCellPhoneNumber("08966553");
        System.out.println("checkCellPhoneNumber result: " + result);
        Assert.assertFalse(result, "without +27 should be invalid");
        
        // test more invalid formats
        Assert.assertFalse(login.checkCellPhoneNumber("08966553"), "missing +27");
        Assert.assertFalse(login.checkCellPhoneNumber("+2783896"), "too short");
        Assert.assertFalse(login.checkCellPhoneNumber("+278389689761"), "too long");
        Assert.assertFalse(login.checkCellPhoneNumber("+267838968976"), "wrong country code");
        
        String registerResult = login.registerUser("kyl_1", "Ch&sec@ke99!", "Kyle", "Smith", "08966553");
        System.out.println("registerUser result with invalid phone: \n" + registerResult);
        Assert.assertTrue(registerResult.contains("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again."), 
            "should show exact phone error");
        System.out.println("All invalid format tests passed");
    }

    // test successful login
    @Test
    public void testLoginSuccessful() {
        System.out.println("\n=== Testing successful login ===");
        
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String firstName = "Kyle";
        String lastName = "Smith";
        String phone = "+27838968976";
        
        login.registerUser(username, password, firstName, lastName, phone);
        System.out.println("User registered");
        
        boolean loginResult = login.loginUser(username, password);
        System.out.println("loginUser result: " + loginResult);
        Assert.assertTrue(loginResult, "correct credentials should work");
        
        String statusMessage = login.returnLoginStatus(true, firstName, lastName);
        System.out.println("Status message: " + statusMessage);
        Assert.assertEquals(statusMessage, 
            "Welcome Kyle, Smith it is great to see you again.",
            "success message format correct");
    }

    // test failed login
    @Test
    public void testLoginFailed() {
        System.out.println("\n=== Testing failed login ===");
        
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String firstName = "Kyle";
        String lastName = "Smith";
        String phone = "+27838968976";
        
        login.registerUser(username, password, firstName, lastName, phone);
        System.out.println("User registered");
        
        boolean wrongPassword = login.loginUser(username, "wrongpassword");
        System.out.println("wrong password: " + wrongPassword);
        Assert.assertFalse(wrongPassword, "wrong password fails");
        
        boolean wrongUsername = login.loginUser("wrong_", password);
        System.out.println("wrong username: " + wrongUsername);
        Assert.assertFalse(wrongUsername, "wrong username fails");
        
        String statusMessage = login.returnLoginStatus(false, firstName, lastName);
        System.out.println("Failure message: " + statusMessage);
        Assert.assertEquals(statusMessage, 
            "Username or password incorrect, please try again.",
            "failure message correct");
    }

    // test complete flow with exact messages
    @Test
    public void testCompleteRegistrationFlowWithExactMessages() {
        System.out.println("\n=== Testing complete flow with exact messages ===");
        
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String phone = "+27838968976";
        String firstName = "Kyle";
        String lastName = "Smith";
        
        String registerResult = login.registerUser(username, password, firstName, lastName, phone);
        String expectedMessage = "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nUser registered successfully.";
        
        System.out.println("Expected: \n" + expectedMessage);
        System.out.println("Actual: \n" + registerResult);
        
        Assert.assertEquals(registerResult, expectedMessage, 
            "success message must match exactly");
        
        Assert.assertTrue(login.loginUser(username, password), "login should work");
        System.out.println("✓ complete flow passed");
    }

    // test with POE test data
    @Test
    public void testWithExactPOETestData() {
        System.out.println("\n=== Testing with POE test data ===");
        
        String testUsername = "kyl_1";
        String testPassword = "Ch&sec@ke99!";
        String testPhone = "+27838968976";
        String invalidUsername = "kyle!!!!!!";
        String invalidPassword = "password";
        String invalidPhone = "08966553";
        
        // test valid
        Assert.assertTrue(login.checkUserName(testUsername), 
            "kyl_1 should be valid");
        Assert.assertTrue(login.checkPasswordComplexity(testPassword), 
            "Ch&sec@ke99! should be valid");
        Assert.assertTrue(login.checkCellPhoneNumber(testPhone), 
            "+27838968976 should be valid");
        
        // test invalid
        Assert.assertFalse(login.checkUserName(invalidUsername), 
            "kyle!!!!!! should be invalid");
        Assert.assertFalse(login.checkPasswordComplexity(invalidPassword), 
            "password should be invalid");
        Assert.assertFalse(login.checkCellPhoneNumber(invalidPhone), 
            "08966553 should be invalid");
        
        System.out.println("✓ all POE test data passed");
    }

    // test returnLoginStatus format
    @Test
    public void testReturnLoginStatusWithPOEData() {
        System.out.println("\n=== Testing returnLoginStatus format ===");
        
        String firstName = "Kyle";
        String lastName = "Smith";
        
        String successMessage = login.returnLoginStatus(true, firstName, lastName);
        System.out.println("Success: " + successMessage);
        Assert.assertEquals(successMessage, 
            "Welcome Kyle, Smith it is great to see you again.",
            "format: Welcome <first>, <last> it is great to see you again");
        
        String failureMessage = login.returnLoginStatus(false, firstName, lastName);
        System.out.println("Failure: " + failureMessage);
        Assert.assertEquals(failureMessage, 
            "Username or password incorrect, please try again.",
            "failure message correct");
    }

    // test invalid username only
    @Test
    public void testRegisterUserInvalidUsername() {
        System.out.println("\n=== Testing invalid username only ===");
        
        String result = login.registerUser("kyle!!!!!!", "Ch&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\nPassword successfully captured.\nCell phone number successfully added.";
        
        System.out.println("Expected: \n" + expected);
        System.out.println("Actual: \n" + result);
        
        Assert.assertEquals(result, expected, 
            "username error but password and phone ok");
    }

    // test invalid password only
    @Test
    public void testRegisterUserInvalidPassword() {
        System.out.println("\n=== Testing invalid password only ===");
        
        String result = login.registerUser("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        String expected = "Username successfully captured.\nPassword is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\nCell phone number successfully added.";
        
        System.out.println("Expected: \n" + expected);
        System.out.println("Actual: \n" + result);
        
        Assert.assertEquals(result, expected, 
            "password error but username and phone ok");
    }

    // test invalid phone only
    @Test
    public void testRegisterUserInvalidPhone() {
        System.out.println("\n=== Testing invalid phone only ===");
        
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "Kyle", "Smith", "08966553");
        String expected = "Username successfully captured.\nPassword successfully captured.\nCell phone number incorrectly formatted or does not contain international code; please correct the number and try again.";
        
        System.out.println("Expected: \n" + expected);
        System.out.println("Actual: \n" + result);
        
        Assert.assertEquals(result, expected, 
            "phone error but username and password ok");
    }

    // test data stored correctly
    @Test
    public void testStoredUserData() {
        System.out.println("\n=== Testing stored user data ===");
        
        String username = "kyl_1";
        String password = "Ch&sec@ke99!";
        String firstName = "Kyle";
        String lastName = "Smith";
        String phone = "+27838968976";
        
        login.registerUser(username, password, firstName, lastName, phone);
        
        Assert.assertEquals(login.getStoredUsername(), username, "username matches");
        Assert.assertEquals(login.getStoredPassword(), password, "password matches");
        Assert.assertEquals(login.getStoredFirstName(), firstName, "first name matches");
        Assert.assertEquals(login.getStoredLastName(), lastName, "last name matches");
        Assert.assertEquals(login.getStoredPhoneNumber(), phone, "phone matches");
        
        System.out.println("✓ all user data stored correctly");
    }
}