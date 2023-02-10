import java.util.Scanner;

public class Main {
    public static final String dbConnection = "jdbc:sqlserver://vanier-grocery-service.database.windows.net:1433;database=VanierGroceryService;user=remyAzure@vanier-grocery-service;password=Vanier1212;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    public static void main(String[] args) throws ClassNotFoundException {
        // new scanner to be passed to methods
        Scanner scan = new Scanner(System.in);
        greetingPage(scan);
        scan.close();
    }

    public static void greetingPage(Scanner scan) throws ClassNotFoundException {
        System.out.println("Do you have an account? (yes/no)");
        String answer = scan.nextLine();
        while (true) {
            if (answer.equalsIgnoreCase("yes")) {
                logIn(scan);
                break;
            } else if (answer.equalsIgnoreCase("no")) {
                Account.createAccount(scan);
                break;
            } else {
                System.out.println("Please enter a valid answer!");
                answer = scan.nextLine();
            }
        }
    }

    public static void logIn(Scanner scan) throws ClassNotFoundException {
        int count = 1;
        while (true) {
            // prompt user
            System.out.println("--- Login ---\nPlease input email");
            String email = scan.nextLine();
            System.out.println("Please enter your password");
            String password = scan.nextLine();
            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
            // try getting account
            Account account = Account.getFromDB(email, sha256hex);
            if (count == 3) {
                System.out.println("You will now be sent to the Sign-Up page due to multiple invalid login.");
                Account.createAccount(scan);
                break;
            }
            if (account != null) {
                System.out.println("You have successfully logged in!");
                Product.shopMenu(scan, account);
                break;
            } else {
                System.out.println("Invalid login, try again.");
                count++;
            }
        }
    }
}

