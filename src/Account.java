import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
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

    public static void editAccount(Scanner scan, Account account) throws ClassNotFoundException {
        ArrayList<Payment> payments = Payment.getAllPayments(account.getEmail());//get list of all payments
        ArrayList<Order> orders = new ArrayList<>();
        payments.forEach(p -> orders.add(new Order(p.getOrderID())));//get list of all orders

        // todo: menu for editing account details, viewing open orders/payments. code goes here.

        updateDB(account); //save changes
        Product.shopMenu(scan, account); // return to menu
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
    public static void createAccount(Scanner scan) throws ClassNotFoundException {
        Account account = new Account();

        System.out.println("--- Signup ---\nInput email address:");
        String email = scan.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Email is invalid or already registered. Re-input email address:");
            email = scan.nextLine();
        }
        account.setEmail(email.toLowerCase());

        System.out.println("Passwords, between 5 and 15 characters, must have at least one uppercase and one lowercase \n" + "letter, at least one digit, and at least one of the following special characters: @#$%_-\\");
        System.out.println("Input password:");
        String password = scan.nextLine();
        while (!isValidPassword(password)) {
            System.out.println("Re-input password:");
            password = scan.nextLine();
        }
        account.setPassword(password);

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

        System.out.println("Address\nInput street number, name and apartment number:");
        String street = scan.nextLine(); //check no special characters
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

        System.out.println("Input phone number with no spaces:");
        String phoneNumber = scan.nextLine();
        while (!isPhoneValid(phoneNumber)) {
            System.out.println("Re-input phone number:");
            phoneNumber = scan.nextLine();
        }
        account.setPhoneNumber(phoneNumber);

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
        try {
            updateDB(account);
            Main.logIn(scan);
        } catch (Exception e) {
            System.out.println("Couldn't add new account to database");
        }
    }

    //make sure @ is present, short emails (up to 20 before @) only
    public static boolean isValidEmail(String email) throws ClassNotFoundException {
        if (email.matches("^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            boolean exist = false;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = Main.dbConnection;
            ResultSet rs;
            try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Accounts WHERE emailAccount = '" + email + "';";
                rs = statement.executeQuery(selectSql);
                if (rs.isBeforeFirst()) {
                    exist = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (exist)
                return false;
            return true;
        }
        System.out.println("Invalid email.");
        return false;
    }

    //check format of date
    public static boolean isValidExpFormat(String exp) {
        // [XX/XX], [01-12]/[01-99]
        return exp.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    //check if already expired
    public static boolean isValidExp(String exp) {
        try {
            if ((Integer.parseInt(exp.substring(exp.length() - 2))) < LocalDate.now().getYear()-2000 || (Integer.parseInt(exp.substring(0, 2))) < LocalDate.now().getMonthValue()) {
                System.out.println("Card expired.");
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid format.");
        }
        return true;
    }

    //enforce strong password
    public static boolean isValidPassword(String password) {
        if (password.length() < 5 || password.length() > 15) {
            System.out.println("Password must be more than 5 and less than 15 characters in length.");
            return false;
        }
        if (!password.matches("(.*[A-Z].*)")) {
            System.out.println("Password must have at least one uppercase character.");
            return false;
        }
        if (!password.matches("(.*[a-z].*)")) {
            System.out.println("Password must have at least one lowercase character.");
            return false;
        }
        if (!password.matches("(.*[0-9].*)")) {
            System.out.println("Password must have at least one number.");
            return false;
        }
        if (!password.matches("(.*[@,#,$,%,_(\\-),*].*$)")) {
            System.out.println("Password must have at least one of the following characters: @#$%_-");
            return false;
        }
        return true;
    }

    //make sure no digits in letters
    public static boolean isDigit(String input) {
        if (input.matches("[A-zA-Z -]+")) return true;
        System.out.println("Error: Input can only be letters.");
        return false;
    }

    //make sure no letters in numbers
    public static boolean isCharacter(String input) {
        if (input.matches("\\d+")) return true;
        System.out.println("Error: Input can only be digits.");
        return false;
    }

    //LETTER/NUMBER/LETTER NUMBER/LETTER/NUMBER format only
    public static boolean isZipValid(String zip) {
        if (zip.matches("^(.*[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9].*$)")) return true;
        System.out.println("Error: Not a valid postal code.");
        return false;
    }

    //10 digits needed, no area +1 area code, i.e. 5146663333
    public static boolean isPhoneValid(String input) {
        if (input.length() != 10) {
            System.out.println("Not a phone number.");
            return false;
        } else if (input.matches("[a-zA-Z]+")) {
            System.out.println("Not a phone number.");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nAccount information:\nEmail: " + email + ", password: " + password + ", First name: " + firstName + ", Last name: " + lastName + "\n" + "Street: " + street + ", City: " + city + ", Postal code: " + postalCode + ", Province: " + province + "\n" + "Country: " + country + ", Phone number: " + phoneNumber + "\n" + "Card First name: " + defaultCardFirstName + ", Card Last name: " + defaultCardLastName + ", Card Number: " + defaultCardNum + "\n" + "Card CVV: " + defaultCardCVV + ", Card Expiration: " + defaultCardExp + ", Card Type: " + defaultCardType + "\n";
    }

    // DB methods
    public static void updateDB(Account a) throws ClassNotFoundException { //update or insert
        boolean exist = false;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet rs;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
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
            try (Connection connection = DriverManager.getConnection(Main.dbConnection); Statement statement = connection.createStatement()) {
                // Create and execute an insert SQL statement.
                String sql = "UPDATE Accounts SET  emailAccount = '" + a.email + "', password = '" + a.password + "', firstName = '" + a.firstName + "', lastName = '" + a.lastName + "', addressStreet = '" + a.street + "', addressCity = '" + a.city + "', addressZip = '" + a.postalCode + "', addressState = '" + a.province + "', addressCountry = '" + a.country + "', phoneNumber = '" + a.phoneNumber + "', cardFirstName = '" + a.defaultCardFirstName + "', cardLastName = '" + a.defaultCardLastName + "', cardNumber = '" + a.defaultCardNum + "', cardCVV = '" + a.defaultCardCVV + "', cardExp = '" + a.defaultCardExp + "', cardType = '" + a.defaultCardType + "' WHERE emailAccount = '" + a.email + "' ";
                int rowsUpdated = statement.executeUpdate(sql);
                if (rowsUpdated < 1) throw new SQLException("zero row updated");
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = DriverManager.getConnection(Main.dbConnection); Statement statement = connection.createStatement()) {
                // Create and execute an insert SQL statement.
                String sql = "insert into Accounts(emailAccount,password,firstName,lastName,addressStreet,addressCity,addressZip,addressState,addressCountry,phoneNumber,cardFirstName,cardLastName,cardNumber,cardCVV,cardExp,cardType)Values('" + a.email + "','" + a.password + "','" + a.firstName + "','" + a.lastName + "','" + a.street + "','" + a.city + "','" + a.postalCode + "','" + a.province + "','" + a.country + "','" + a.phoneNumber + "','" + a.defaultCardFirstName + "','" + a.defaultCardLastName + "','" + a.defaultCardNum + "','" + a.defaultCardCVV + "','" + a.defaultCardExp + "','" + a.defaultCardType + "');";
                int rowsUpdated = statement.executeUpdate(sql);
                if (rowsUpdated < 1) throw new SQLException("zero row updated");
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Account getFromDB(String email, String password) throws ClassNotFoundException { //get or return null
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet rs;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Accounts WHERE emailAccount = '" + email + "' AND password = '" + password + "';";
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
            }
            return account;
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }
}