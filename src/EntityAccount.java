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
    private String defaultPayment;
    private String phoneNumber;

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

    public String getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(String defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public EntityAccount(String email,
                         String password,
                         String firstName,
                         String lastName,
                         String street,
                         String city,
                         String postalCode,
                         String province,
                         String country,
                         String defaultPayment,
                         String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
        this.defaultPayment = defaultPayment;
        this.phoneNumber = phoneNumber;
    }

    public static void createAccount(){
        Scanner kbd = new Scanner(System.in);

        System.out.println("Input email:");
        String email = kbd.nextLine();
        while(!isValidEmail(email)){
            System.out.println("Re-input email:");
            email = kbd.nextLine();
        }

        System.out.println("Input password:");
        String password = kbd.nextLine();
        while(!isValidPassword(password)) {
            System.out.println("Re-input password:");
            password = kbd.nextLine();
        }

        System.out.println("First name:");
        String firstName = kbd.nextLine();
        while(!isDigit(firstName)){
            System.out.println("Re-input first name:");
            firstName = kbd.nextLine();
        }

        System.out.println("Last name:");
        String lastName = kbd.nextLine();
        while(!isDigit(lastName)){
            System.out.println("Re-input last name:");
            lastName = kbd.nextLine();
        }

        System.out.println("Address:\nStreet name:");
        String street = kbd.nextLine();

        System.out.println("City:");
        String city = kbd.nextLine();
        while(!isDigit(city)){
            System.out.println("Re-input city:");
            city = kbd.nextLine();
        }

        System.out.println("Postal code:");
        String postalCode = kbd.nextLine();
        while(!isZipValid(postalCode)){
            System.out.println("Re-input zip:");
            postalCode = kbd.nextLine();
        }

        System.out.println("Province:");
        String province = kbd.nextLine();
        while(!isDigit(province)){
            System.out.println("Re-input province:");
            province = kbd.nextLine();
        }

        System.out.println("Country:");
        String country = kbd.nextLine();
        while(!isDigit(country)){
            System.out.println("Re-input country:");
            country = kbd.nextLine();
        }

        System.out.println("Default payment:"); //need to match with payment entity
        String defaultPayment = kbd.nextLine();

        System.out.println("Phone number with no spaces:");
        String phoneNumber = kbd.nextLine();
        while(!isPhoneValid(phoneNumber)){
            System.out.println("Re-input phone:");
            phoneNumber = kbd.nextLine();
        }

        EntityAccount account = new EntityAccount(email,
                password, firstName, lastName, street,
                city, postalCode, province, country,
                defaultPayment, phoneNumber);
    }

    //make sure @ is present, short emails (up to 20 before @) only
    public static boolean isValidEmail(String email){
        boolean isValid = true;
        String regex = "^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!email.matches(regex)){
            System.out.println("Invalid email.");
            isValid = false;
        }
        return isValid;
    }


    //enforce strong password
    public static boolean isValidPassword(String password){
        boolean isValid = true;
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
        boolean isValid = true;
        String letters = "[A-zA-Z -]+";
        if(!input.matches(letters)){
            System.out.println("Error: Input can only be letters.");
            isValid = false;
        }
        return isValid;
    }

    //make sure no letters in numbers
    public static boolean isCharacter(String input) {
        boolean isValid = true;
        String regex = "\\d+";
        if(!input.matches(regex)){
            System.out.println("Error: Input can only be digits.");
            isValid = false;
        }
        return isValid;
    }

    public static boolean isZipValid(String zip) {
        boolean isValid = true;
        //LETTER/NUMBER/LETTER NUMBER/LETTER/NUMBER format only
        String regex = "^(.*[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9].*$)";
        if(!zip.matches(regex)){
            System.out.println("Error: Not a valid postal code.");
            isValid = false;
        }
        return isValid;
    }

    public static boolean isPhoneValid(String input){
        String letters = "[a-zA-Z]+";
        boolean isValid = true;
        if(input.length() != 10){
            System.out.println("Not a phone number.");
            isValid = false;
        } else if(input.matches(letters)){
            System.out.println("Not a phone number.");
            isValid = false;
        }
        return isValid;
    }

//    public static void main(String[] args){
//        createAccount();
//
//        Scanner kbd = new Scanner(System.in);
//
//        System.out.println("input email:");
//        String email = kbd.next();
//        while(!isValidEmail(email)){
//            System.out.println("re-input email:");
//            email = kbd.next();
//        }
//
//        System.out.println("input password:");
//        String password = kbd.next();
//        while(!isValidPassword(password)) {
//            System.out.println("re-input password:");
//            password = kbd.next();
//        }
//
//        System.out.println("input letters:");
//        String letters = kbd.next();
//        while(!isDigit(letters)){
//            System.out.println("re-input letters:");
//            letters = kbd.next();
//        }
//
//        System.out.println("input digits:");
//        String digits = kbd.next();
//        while(!isCharacter(digits)){
//            System.out.println("re-input digits:");
//            digits = kbd.next();
//        }
//
//        System.out.println("input zip:");
//        String zip = kbd.next();
//        while(!isZipValid(zip)){
//            System.out.println("re-input zip:");
//            zip = kbd.next();
//        }
//
//        System.out.println("input phone:");
//        String phone = kbd.next();
//        while(!isPhoneValid(phone)){
//            System.out.println("re-input phone:");
//            phone = kbd.next();
//        }
//    }

}
