import java.util.*;

public class EntityAccount {
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
    private static boolean isValid = true;

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


    public EntityAccount() {
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

    public EntityAccount(String email, String password, String firstName, String lastName,
                         String street, String city, String postalCode, String province,
                         String country, String phoneNumber, String defaultCardFirstName,
                         String defaultCardLastName, String defaultCardNum, String defaultCardCVV,
                         String defaultCardExp, String defaultCardType) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.defaultCardFirstName = defaultCardFirstName;
        this.defaultCardLastName = defaultCardLastName;
        this.defaultCardNum = defaultCardNum;
        this.defaultCardCVV = defaultCardCVV;
        this.defaultCardExp = defaultCardExp;
        this.defaultCardType = defaultCardType;
    }


    //testing
//    public static void main(String[] args) throws Exception {
//        createAccount();
//    }

    public static void createAccount() throws Exception {
        Scanner kbd = new Scanner(System.in);

        EntityAccount account = new EntityAccount();

        System.out.println("Input email address:");
        String email = kbd.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Re-input email address:");
            email = kbd.nextLine();
        }
        account.setEmail(email.toLowerCase());

        System.out.println("Passwords, between 5 and 15 characters, must have at least one uppercase and one lowercase \n"+
                            "letter, at least one digit, and at least one of the following special characters: @#$%_-\\");
        System.out.println("Input password:");
        String password = kbd.nextLine();
        while (!isValidPassword(password)) {
            System.out.println("Re-input password:");
            password = kbd.nextLine();
        }
        account.setPassword(password);

        System.out.println("Input first name:");
        String firstName = kbd.nextLine();
        while (!isDigit(firstName)) {
            System.out.println("Re-input first name:");
            firstName = kbd.nextLine();
        }
        account.setFirstName(firstName.toLowerCase());

        System.out.println("Input last name:");
        String lastName = kbd.nextLine();
        while (!isDigit(lastName)) {
            System.out.println("Re-input last name:");
            lastName = kbd.nextLine();
        }
        account.setLastName(lastName.toLowerCase());

        System.out.println("Address\nInput street name:");
        String street = kbd.nextLine(); //check no special characters
        account.setStreet(street.toLowerCase());

        System.out.println("Input city:");
        String city = kbd.nextLine();
        while (!isDigit(city)) {
            System.out.println("Re-input city:");
            city = kbd.nextLine();
        }
        account.setCity(city.toLowerCase());

        System.out.println("Input postal code, without spaces or hyphens:");
        String postalCode = kbd.nextLine();
        while (!isZipValid(postalCode)) {
            System.out.println("Re-input postal code:");
            postalCode = kbd.nextLine();
        }
        account.setPostalCode(postalCode.toLowerCase());

        System.out.println("Input province:");
        String province = kbd.nextLine();
        while (!isDigit(province)) {
            System.out.println("Re-input province:");
            province = kbd.nextLine();
        }
        account.setProvince(province.toLowerCase());

        System.out.println("Input country:");
        String country = kbd.nextLine();
        while (!isDigit(country)) {
            System.out.println("Re-input country:");
            country = kbd.nextLine();
        }
        account.setCountry(country.toLowerCase());

        System.out.println("Input phone number with no spaces:");
        String phoneNumber = kbd.nextLine();
        while (!isPhoneValid(phoneNumber)) {
            System.out.println("Re-input phone number:");
            phoneNumber = kbd.nextLine();
        }
        account.setPhoneNumber(phoneNumber);

        boolean loop = true;
        do{
            System.out.println("Default payment\nInput card holder's first name:");
            String defaultCardFirstName = kbd.nextLine();
            while (!isDigit(defaultCardFirstName)) {
                System.out.println("Re-input first name:");
                defaultCardFirstName = kbd.nextLine();
            }
            account.setDefaultCardFirstName(defaultCardFirstName.toLowerCase());

            System.out.println("Input card holder's last name:");
            String defaultCardLastName = kbd.nextLine();
            while (!isDigit(defaultCardLastName)) {
                System.out.println("Re-input last name:");
                defaultCardLastName = kbd.nextLine();
            }
            account.setDefaultCardLastName(defaultCardLastName.toLowerCase());

            System.out.println("Input card number:");
            String defaultCardNum = kbd.nextLine();
            while (!isCharacter(defaultCardNum) || defaultCardNum.length() != 16) {
                if (defaultCardNum.length() != 16) {
                    System.out.println("Error: Not enough digits.");
                }
                System.out.println("Re-input card number with no spaces or hyphens:");
                defaultCardNum = kbd.nextLine();
            }
            account.setDefaultCardNum(defaultCardNum);

            System.out.println("Input card CVV:");
            String defaultCardCVV = kbd.nextLine();
            while (!isCharacter(defaultCardCVV) || defaultCardCVV.length() != 3) {
                System.out.println("Re-input CVV:");
                defaultCardCVV = kbd.nextLine();
            }
            account.setDefaultCardCVV(defaultCardCVV);

            System.out.println("Input card expiration date [MM/YY]:");
            String defaultCardExp = kbd.nextLine();
            while (!isValidExpFormat(defaultCardExp) || !isValidExp(defaultCardExp)) {
                System.out.println("Error: Re-input expiration date or type \"RESTART\" to enter a new card:");
                defaultCardExp = kbd.nextLine();
                if (defaultCardExp.equalsIgnoreCase("restart")) {
                    break;
                }
            }
            if(isValidExpFormat(defaultCardExp) && isValidExp(defaultCardExp)){
                loop = false;
            }
            account.setDefaultCardExp(defaultCardExp);
        }while(loop);

        System.out.println("Input type of card [VISA/MASTERCARD ONLY]:");
        String defaultCardType = kbd.nextLine();
        while (!defaultCardType.equalsIgnoreCase("visa") && !defaultCardType.equalsIgnoreCase("mastercard")) {
            System.out.println("Re-input card type:");
            defaultCardType = kbd.nextLine();
        }
        account.setDefaultCardType(defaultCardType.toLowerCase());

        //System.out.println(account);

    }

    //make sure @ is present, short emails (up to 20 before @) only
    public static boolean isValidEmail(String email) {
        isValid = true;
        String regex = "^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(regex)) {
            System.out.println("Invalid email.");
            isValid = false;
        }
        return isValid;
    }

    //check format of date
    public static boolean isValidExpFormat(String exp) {
        isValid = true;
        if (!exp.matches("(?:0[1-9]|1[0-2])/[0-9]{2}")) { // [XX/XX], [01-12]/[01-99]
            isValid = false;
        }
        return isValid;
    }

    //check if already expired
    public static boolean isValidExp(String exp) throws Exception{
        Scanner kbd = new Scanner(System.in);
        isValid = true;
        try {
            if ((Integer.parseInt(exp.substring(exp.length() - 2))) < 23) {
                System.out.println("Card expired.");
                isValid = false;
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Invalid format.");
            isValid = true;
        }
        return isValid;
    }

    //enforce strong password
    public static boolean isValidPassword(String password){
        isValid = true;
        String upperCase = "(.*[A-Z].*)";
        String lowerCase = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String special = "(.*[@,#,$,%,_(\\-),*].*$)";
        if(password.length() < 5 || password.length() > 15){
            System.out.println("Password must be more than 5 and less than 15 characters in length.");
            isValid = false;
        }
        if(!password.matches(upperCase)){
            System.out.println("Password must have at least one uppercase character.");
            isValid = false;
        }
        if (!password.matches(lowerCase)){
            System.out.println("Password must have at least one lowercase character.");
            isValid = false;
        }
        if (!password.matches(numbers)) {
            System.out.println("Password must have at least one number.");
            isValid = false;
        }
        if(!password.matches(special)){
            System.out.println("Password must have at least one of the following characters: @#$%_-");
            isValid = false;
        }
        return isValid;
    }

    //make sure no digits in letters
    public static boolean isDigit(String input) {
        isValid = true;
        String letters = "[A-zA-Z -]+";
        if(!input.matches(letters)){
            System.out.println("Error: Input can only be letters.");
            isValid = false;
        }
        return isValid;
    }

    //make sure no letters in numbers
    public static boolean isCharacter(String input) {
        isValid = true;
        String regex = "\\d+";
        if(!input.matches(regex)){
            System.out.println("Error: Input can only be digits.");
            isValid = false;
        }
        return isValid;
    }

    //LETTER/NUMBER/LETTER NUMBER/LETTER/NUMBER format only
    public static boolean isZipValid(String zip) {
        isValid = true;
        String regex = "^(.*[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9].*$)";
        if(!zip.matches(regex)){
            System.out.println("Error: Not a valid postal code.");
            isValid = false;
        }
        return isValid;
    }

    //10 digits needed, no area +1 area code, i.e. 5146663333
    public static boolean isPhoneValid(String input){
        String letters = "[a-zA-Z]+";
        isValid = true;
        if(input.length() != 10){
            System.out.println("Not a phone number.");
            isValid = false;
        } else if(input.matches(letters)){
            System.out.println("Not a phone number.");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return "\nAccount information:\nEmail: " + email + ", password: " + password + ", First name: " + firstName +
                ", Last name: " + lastName + "\n" + "Street: " + street + ", City: " + city +
                ", Postal code: " + postalCode + ", Province: " + province + "\n" +
                "Country: " + country + ", Phone number: " + phoneNumber + "\n" +
                "Card First name: " + defaultCardFirstName + ", Card Last name: " + defaultCardLastName +
                ", Card Number: " + defaultCardNum + "\n" + "Card CVV: " + defaultCardCVV +
                ", Card Expiration: " + defaultCardExp + ", Card Type: " + defaultCardType + "\n";
    }
}
