import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {

    // instance variables
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String postalCode;
    private String province;
    private String country;
    private String phoneNumber;
    private String defaultCardFirstName;
    private String defaultCardLastName;
    private String defaultCardNum;
    private String defaultCardCVV;
    private String defaultCardExp;
    private String defaultCardType;
    private int pointBalance;

    // getter and setters
    public String getDefaultCardFirstName() {
        return defaultCardFirstName;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public void setPointBalance(int pointBalance) {
        this.pointBalance = pointBalance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDefaultFirstCardName() {
        return defaultCardFirstName;
    }

    public void setDefaultCardFirstName(String defaultCardFirstName) {
        this.defaultCardFirstName = defaultCardFirstName;
    }

    public String getDefaultCardLastName() {
        return defaultCardLastName;
    }

    public void setDefaultCardLastName(String defaultCardLastName) {
        this.defaultCardLastName = defaultCardLastName;
    }

    public String getDefaultCardNum() {
        return defaultCardNum;
    }

    public void setDefaultCardNum(String defaultCardNum) {
        this.defaultCardNum = defaultCardNum;
    }

    public String getDefaultCardCVV() {
        return defaultCardCVV;
    }

    public void setDefaultCardCVV(String defaultCardCVV) {
        this.defaultCardCVV = defaultCardCVV;
    }

    public String getDefaultCardExp() {
        return defaultCardExp;
    }

    public void setDefaultCardExp(String defaultCardExp) {
        this.defaultCardExp = defaultCardExp;
    }

    public String getDefaultCardType() {
        return defaultCardType;
    }

    public void setDefaultCardType(String defaultCardType) {
        this.defaultCardType = defaultCardType;
    }

    // constructor
    public Account() {
        this.email = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.street = "";
        this.city = "";
        this.postalCode = "";
        this.province = "";
        this.country = "";
        this.phoneNumber = "";
        this.defaultCardFirstName = "";
        this.defaultCardLastName = "";
        this.defaultCardNum = "";
        this.defaultCardCVV = "";
        this.defaultCardExp = "";
        this.defaultCardType = "";
    }

    @Override
    public String toString() {
        return "\nAccount information:\nEmail: " + email + ", First name: " + firstName + ", Last name: " + lastName
                + ", Phone number: "
                + phoneNumber + "\n" + "Street: " + street + ", City: " + city + "\n" + "Postal code: "
                + postalCode + ", Province: " + province + ", Country: " + country + "\n" + "Card First name: "
                + defaultCardFirstName + ", Card Last name: "
                + defaultCardLastName + ", Card Number: " + "**** **** **** " + defaultCardNum.substring(12) + "\n"
                + "Card Expiration: " + defaultCardExp + ", Card Type: " + defaultCardType;
    }

    // STATIC METHODS
    // --------------------------------------------------------------------------------------------------

    public static void createAccount(Scanner scan) throws ClassNotFoundException {
        Account account = new Account();
        emailFN(scan, account);
        passwordFN(scan, account);
        nameFN(scan, account);
        addressFN(scan, account);
        phoneFN(scan, account);
        defaultCardFN(scan, account);
        try {
            updateDB(account);
            Main.logIn(scan);
        } catch (Exception e) {
            System.out.println("Couldn't add new account to database");
        }
    }

    public static void editAccount(Scanner scan, Account account) throws ClassNotFoundException {
        account = Account.getFromDB(account.getEmail(), account.getPassword());

        boolean loop = true;
        while (loop) {
            System.out.println(account);
            System.out.print("Accumulated points: $");
            System.out.printf("%.2f\n\n", account.getPointBalance() / 100.00);
            System.out.println(
                    "--- Manage Account ---\n\t1.Change password\n\t2.Change name\n\t3.Change address\n\t4.Change phone number\n\t5.Change default payment\n\t6.See orders\n\t7.Go back to menu");
            String input = scan.nextLine();
            switch (input) {
                case "1" -> passwordFN(scan, account);
                case "2" -> nameFN(scan, account);
                case "3" -> addressFN(scan, account);
                case "4" -> phoneFN(scan, account);
                case "5" -> defaultCardFN(scan, account);
                case "6" -> {
                    System.out.println("Retrieving all past orders, this may take a while...");
                    ArrayList<Order> orders = Order.getAllOrders(account);
                    orders.forEach(o -> System.out.println(o.shortString()));
                    System.out.println("Enter the OrderID you would like to see:");
                    String selected = "";
                    while (true) {
                        String input2 = scan.nextLine();
                        try {
                            selected = orders.stream().filter(s -> s.getOrderID().equalsIgnoreCase(input2)).findFirst()
                                    .get().getOrderID();
                        } catch (Exception e) {
                        }
                        if (selected.equalsIgnoreCase(""))
                            System.out.println("Invalid input, try again.");
                        else
                            break;
                    }
                    final String selected2 = selected;
                    try {
                        System.out.println(orders.stream().filter(o -> o.getOrderID().equalsIgnoreCase(selected2))
                                .findFirst().get());
                    } catch (Exception e) {
                    }

                }
                case "7" -> {
                    loop = false;
                }
                default -> System.out.println("Invalid input, try again.");
            }
        }
        updateDB(account); // save changes
    }

    private static void phoneFN(Scanner scan, Account account) {
        System.out.println("Input phone number with no spaces:");
        String phoneNumber = scan.nextLine();
        while (!isPhoneValid(phoneNumber)) {
            System.out.println("Re-input phone number:");
            phoneNumber = scan.nextLine();
        }
        account.setPhoneNumber(phoneNumber);
    }

    private static void defaultCardFN(Scanner scan, Account account) {
        boolean loop = true;
        do {
            System.out.println("Default payment\nInput card holder's first name:");
            String defaultCardFirstName = scan.nextLine();
            while (!isDigit(defaultCardFirstName)) {
                System.out.println("Re-input first name:");
                defaultCardFirstName = scan.nextLine();
            }
            account.setDefaultCardFirstName(defaultCardFirstName.toLowerCase());

            System.out.println("Input card holder's last name:");
            String defaultCardLastName = scan.nextLine();
            while (!isDigit(defaultCardLastName)) {
                System.out.println("Re-input last name:");
                defaultCardLastName = scan.nextLine();
            }
            account.setDefaultCardLastName(defaultCardLastName.toLowerCase());

            System.out.println("Input card number:");
            String defaultCardNum = scan.nextLine();
            while (!isCharacter(defaultCardNum) || defaultCardNum.length() != 16) {
                if (defaultCardNum.length() != 16) {
                    System.out.println("Error: Not enough digits.");
                }
                System.out.println("Re-input card number with no spaces or hyphens:");
                defaultCardNum = scan.nextLine();
            }
            account.setDefaultCardNum(defaultCardNum);

            System.out.println("Input card CVV:");
            String defaultCardCVV = scan.nextLine();
            while (!isCharacter(defaultCardCVV) || defaultCardCVV.length() != 3) {
                System.out.println("Re-input CVV:");
                defaultCardCVV = scan.nextLine();
            }
            account.setDefaultCardCVV(defaultCardCVV);

            System.out.println("Input card expiration date [MM/YY]:");
            String defaultCardExp = scan.nextLine();
            while (!isValidExpFormat(defaultCardExp) || !isValidExp(defaultCardExp)) {
                System.out.println("Error: Re-input expiration date or type \"RESTART\" to enter a new card:");
                defaultCardExp = scan.nextLine();
                if (defaultCardExp.equalsIgnoreCase("restart")) {
                    break;
                }
            }
            if (isValidExpFormat(defaultCardExp) && isValidExp(defaultCardExp)) {
                loop = false;
            }
            account.setDefaultCardExp(defaultCardExp);
        } while (loop);

        System.out.println("Input type of card [VISA/MASTERCARD ONLY]:");
        String defaultCardType = scan.nextLine();
        while (!defaultCardType.equalsIgnoreCase("visa") && !defaultCardType.equalsIgnoreCase("mastercard")) {
            System.out.println("Re-input card type:");
            defaultCardType = scan.nextLine();
        }
        account.setDefaultCardType(defaultCardType.toLowerCase());
    }

    private static void addressFN(Scanner scan, Account account) {
        System.out.println("Address\nInput street number, name and apartment number:");
        String street = scan.nextLine(); // check no special characters
        account.setStreet(street.toLowerCase());

        System.out.println("Input city:");
        String city = scan.nextLine();
        while (!isDigit(city)) {
            System.out.println("Re-input city:");
            city = scan.nextLine();
        }
        account.setCity(city.toLowerCase());

        System.out.println("Input postal code, without spaces or hyphens:");
        String postalCode = scan.nextLine();
        while (!isZipValid(postalCode)) {
            System.out.println("Re-input postal code:");
            postalCode = scan.nextLine();
        }
        account.setPostalCode(postalCode.toLowerCase());

        System.out.println("Input province:");
        String province = scan.nextLine();
        while (!isDigit(province)) {
            System.out.println("Re-input province:");
            province = scan.nextLine();
        }
        account.setProvince(province.toLowerCase());

        System.out.println("Input country:");
        String country = scan.nextLine();
        while (!isDigit(country)) {
            System.out.println("Re-input country:");
            country = scan.nextLine();
        }
        account.setCountry(country.toLowerCase());
    }

    private static void nameFN(Scanner scan, Account account) {
        System.out.println("Input first name:");
        String firstName = scan.nextLine();
        while (!isDigit(firstName)) {
            System.out.println("Re-input first name:");
            firstName = scan.nextLine();
        }
        account.setFirstName(firstName.toLowerCase());

        System.out.println("Input last name:");
        String lastName = scan.nextLine();
        while (!isDigit(lastName)) {
            System.out.println("Re-input last name:");
            lastName = scan.nextLine();
        }
        account.setLastName(lastName.toLowerCase());
    }

    private static void passwordFN(Scanner scan, Account account) {
        System.out.println("Passwords, 8 to 16 characters, must have at least 2 uppercase and 2 lowercase \n"
                + "letter, at least 2 digit, and at least 2 special characters.\\");
        System.out.println("Input password:");
        String password = scan.nextLine();
        while (!isValidPassword(password)) {
            System.out.println("Re-input password:");
            password = scan.nextLine();
        }
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        account.setPassword(sha256hex);
    }

    private static void emailFN(Scanner scan, Account account) throws ClassNotFoundException {
        System.out.println("--- Signup ---\nInput email address:");
        String email = scan.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Email is invalid or already registered. Re-input email address:");
            email = scan.nextLine();
        }
        account.setEmail(email.toLowerCase());
    }

    // make sure @ is present, short emails (up to 20 before @) only
    public static boolean isValidEmail(String email) throws ClassNotFoundException {
        if (email.matches("^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            boolean exist = true;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = Main.dbConnection;
            ResultSet rs;
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                    Statement statement = connection.createStatement()) {
                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Accounts WHERE emailAccount = '" + email + "';";
                rs = statement.executeQuery(selectSql);
                if (rs.isBeforeFirst()) {
                    System.out.println("Email already registered.");
                    return !exist;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        System.out.println("Invalid email.");
        return false;
    }

    // check format of date
    public static boolean isValidExpFormat(String exp) {
        // [XX/XX], [01-12]/[01-99]
        return exp.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    // check if already expired
    public static boolean isValidExp(String exp) {
        try {
            if ((Integer.parseInt(exp.substring(exp.length() - 2))) < LocalDate.now().getYear() - 2000
                    || (Integer.parseInt(exp.substring(0, 2))) < LocalDate.now().getMonthValue()) {
                System.out.println("Card expired.");
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid format.");
        }
        return true;
    }

    // enforce strong password
    public static boolean isValidPassword(String password) {
        boolean passwordValid = true;
        if (password.length() < 8 || password.length() > 16) {
            System.out.println("Password must be  8 to 16 characters in length.");
            passwordValid = false;
        }
        if (!password.matches("(.*[A-Z].*[A-Z].*)")) {
            System.out.println("Password must have at least 2 uppercase characters.");
            passwordValid = false;
        }
        if (!password.matches("(.*[a-z].*[a-z].*)")) {
            System.out.println("Password must have at least 2 lowercase characters.");
            passwordValid = false;
        }
        if (!password.matches("(.*[0-9].*[0-9].*)")) {
            System.out.println("Password must have at least 2 numbers.");
            passwordValid = false;
        }
        if (!password.matches(".*[^A-Za-z0-9].*[^A-Za-z0-9].*")) {
            System.out.println("Password must have at least two special characters");
            passwordValid = false;
        }
        return passwordValid;
    }

    // make sure no digits in letters
    public static boolean isDigit(String input) {
        if (input.matches("[A-zA-Z -]+"))
            return true;
        System.out.println("Error: Input can only be letters.");
        return false;
    }

    // make sure no letters in numbers
    public static boolean isCharacter(String input) {
        if (input.matches("\\d+"))
            return true;
        System.out.println("Error: Input can only be digits.");
        return false;
    }

    // LETTER/NUMBER/LETTER NUMBER/LETTER/NUMBER format only
    public static boolean isZipValid(String zip) {
        if (zip.matches("^(.*[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9].*$)"))
            return true;
        System.out.println("Error: Not a valid postal code.");
        return false;
    }

    // 10 digits needed, no area +1 area code, i.e. 5146663333
    public static boolean isPhoneValid(String input) {
        if (input.matches("\\d+")) {
            if (input.length() > 11 || input.length() < 10) {
                System.out.println("Not a phone number.");
                return false;
            } else if (!input.matches("\\d+")) {
                System.out.println("Not a phone number.");
                return false;
            }
            return true;
        }
        System.out.println("Not a phone number.");
        return false;
    }

    // DB methods
    public static void updateDB(Account a) throws ClassNotFoundException { // update or insert
        boolean exist = false;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet rs;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Accounts WHERE emailAccount = '" + a.email + "';";
            rs = statement.executeQuery(selectSql);
            if (rs.isBeforeFirst()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        if (exist) {
            try (Connection connection = DriverManager.getConnection(Main.dbConnection);
                    Statement statement = connection.createStatement()) {
                // Create and execute an insert SQL statement.
                String sql = "UPDATE Accounts SET  emailAccount = '" + a.email + "', password = '" + a.password
                        + "', firstName = '" + a.firstName + "', lastName = '" + a.lastName + "', addressStreet = '"
                        + a.street + "', addressCity = '" + a.city + "', addressZip = '" + a.postalCode
                        + "', addressState = '" + a.province + "', addressCountry = '" + a.country
                        + "', phoneNumber = '" + a.phoneNumber + "', cardFirstName = '" + a.defaultCardFirstName
                        + "', cardLastName = '" + a.defaultCardLastName + "', cardNumber = '" + a.defaultCardNum
                        + "', cardCVV = '" + a.defaultCardCVV + "', cardExp = '" + a.defaultCardExp + "', cardType = '"
                        + a.defaultCardType + "', pointBalance = " + a.pointBalance + " WHERE emailAccount = '"
                        + a.email + "' ";
                int rowsUpdated = statement.executeUpdate(sql);
                if (rowsUpdated < 1)
                    throw new SQLException("zero row updated");
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = DriverManager.getConnection(Main.dbConnection);
                    Statement statement = connection.createStatement()) {
                // Create and execute an insert SQL statement.
                String sql = "insert into Accounts(emailAccount,password,firstName,lastName,addressStreet,addressCity,addressZip,addressState,addressCountry,phoneNumber,cardFirstName,cardLastName,cardNumber,cardCVV,cardExp,cardType)Values('"
                        + a.email + "','" + a.password + "','" + a.firstName + "','" + a.lastName + "','" + a.street
                        + "','" + a.city + "','" + a.postalCode + "','" + a.province + "','" + a.country + "','"
                        + a.phoneNumber + "','" + a.defaultCardFirstName + "','" + a.defaultCardLastName + "','"
                        + a.defaultCardNum + "','" + a.defaultCardCVV + "','" + a.defaultCardExp + "','"
                        + a.defaultCardType + "');";
                int rowsUpdated = statement.executeUpdate(sql);
                if (rowsUpdated < 1)
                    throw new SQLException("zero row updated");
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Account getFromDB(String email, String password) throws ClassNotFoundException { // get or return null
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet rs;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Accounts WHERE emailAccount = '" + email + "' AND password = '" + password
                    + "';";
            rs = statement.executeQuery(selectSql);
            Account account = new Account();
            if (rs.next()) {
                account.setEmail(rs.getString(1));
                account.setPassword(rs.getString(2));
                account.setFirstName(rs.getString(3));
                account.setLastName(rs.getString(4));
                account.setStreet(rs.getString(5));
                account.setCity(rs.getString(6));
                account.setPostalCode(rs.getString(7));
                account.setProvince(rs.getString(8));
                account.setCountry(rs.getString(9));
                account.setPhoneNumber(rs.getString(10));
                account.setDefaultCardFirstName(rs.getString(11));
                account.setDefaultCardLastName(rs.getString(12));
                account.setDefaultCardNum(rs.getString(13));
                account.setDefaultCardCVV(rs.getString(14));
                account.setDefaultCardExp(rs.getString(15));
                account.setDefaultCardType(rs.getString(16));
                account.setPointBalance(rs.getInt(17));
                return account;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        return null;
    }
}