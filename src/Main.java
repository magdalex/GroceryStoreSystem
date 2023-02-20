import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String dbConnection = "jdbc:sqlserver://vanier-grocery-service.database.windows.net:1433;database=VanierGroceryService;user=remyAzure@vanier-grocery-service;password=Vanier1212;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println(
                "   _____                                _____ _                  _____           _                 \n"
                        +

                        "  / ____|                              / ____| |                / ____|         | |                \n"
                        +

                        " | |  __ _ __ ___   ___ ___ _ __ _   _| (___ | |_ ___  _ __ ___| (___  _   _ ___| |_ ___ _ __ ___  \n"
                        +

                        " | | |_ | '__/ _ \\ / __/ _ \\ '__| | | |\\___ \\| __/ _ \\| '__/ _ \\\\___ \\| | | / __| __/ _ \\ '_ ` _ \\ \n"
                        +

                        " | |__| | | | (_) | (_|  __/ |  | |_| |____) | || (_) | | |  __/____) | |_| \\__ \\ ||  __/ | | | | |\n"
                        +

                        "  \\_____|_|  \\___/ \\___\\___|_|   \\__, |_____/ \\__\\___/|_|  \\___|_____/ \\__, |___/\\__\\___|_| |_| |_|\n"
                        +

                        "                                  __/ |                                 __/ |                      \n"
                        +

                        "                                 |___/                                 |___/                       ");
        // display address
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); // you get the IP as a String
            System.out.println("\n" + ip + " is your IP address. You have access to the internet.\n");
        } catch (Exception e) {
            System.out.println("There seems to be some network issues.");
        }
        testdb();
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
            String sha256hex = DigestUtils.sha256Hex(password);
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

    public static void testdb() {
        String Url = dbConnection;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Checking Database connection...");
            Connection connection = DriverManager.getConnection(Url);

            System.out.println("Connection to '"
                    + connection.getMetaData().getDatabaseProductName() + "' successful.\n");
        } catch (Exception e) {
            System.out.println("Unable to make connection with Database. Make sure your IP address is authorized.");
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (Exception e1) {
            }
            System.exit(1);
        }
    }

}
