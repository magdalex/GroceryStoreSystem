import java.util.Scanner;

public class Main {
    public static final String dbConnection = "jdbc:sqlserver://vanier-grocery-service.database.windows.net:1433;database=VanierGroceryService;user=remyAzure@vanier-grocery-service;password=Vanier1212;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

/* 
    MAIN METHOD...
    Scanner is being initialized passed through greetingPage then closed.
*/
    public static void main(String[] args) throws ClassNotFoundException {
        // new scanner to be passed to methods
        Scanner scan = new Scanner(System.in);
        greetingPage(scan);
        scan.close();
    }

/* 
    GREETING PAGE...
    Method takes in the Scanner object...
*/

    public static void greetingPage(Scanner scan) throws ClassNotFoundException {
        System.out.println("Do you have an existing account already?");
        System.out.println("Type Yes if you do, type No if you don't");
        String answer = scan.nextLine();
        while (true) 
    
        if (answer.equalsIgnoreCase("yes")) {  // Checking if answer is "yes"

            logIn(scan); // Passing the scanner object into the logIn method...
            break;
        } else if (answer.equalsIgnoreCase("no")) { // Checking if answer is "no"
            Account.createAccount(scan); // If no... scanner is passed into the account class, createAccount method... 
            break;
        } else {
            System.out.println("Please enter a valid answer!");
            answer = scan.nextLine(); // else is looped back to while loop
        }
    }


/* 
    logIn PAGE...
    Method takes in the Scanner object...
*/
    public static void logIn(Scanner scan) throws ClassNotFoundException {
        int count = 1;
        while (true) {
            // prompt user
            System.out.println("--- Login ---\nPlease input email");
            String email = scan.nextLine();
            System.out.println("Please enter your password");
            String password = scan.nextLine();
            // try getting account
            Account account = Account.getFromDB(email, password); // getFromDB method is run with email and pw strings being passed through...
            if (count == 3) {
                System.out.println("You will now be sent to the Sign-Up page due to multiple invalid login.");
                Account.createAccount(scan); // if log in invalid 3x then scanner is sent to createAccount method and it is run
                break;
            }
            if (account != null) {      // Checks if account is null after getFromDB method if not then logged in...
                System.out.println("You have successfully logged in!");
                Product.shopMenu(scan, account);        // Scanner is now passed into Product class shopMenu method.
                break;
            } else {
                System.out.println("Invalid login, try again.");
                count++;        // count increases if invalid log-in.
            }
        }
    }
}

