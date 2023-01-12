import java.sql.Date;

public class EntityAccount {
    // personal details
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    // security
    private Date creationDate;
    private boolean accountIsEnabled;
    private Date unlockTime;
    // address
    private String addressNumber;
    private String addressStreet;
    private String addressApt;
    private String addressCity;
    private String addressZip;
    private String addressState;
    // payment info
    private String payCardNumber;
    private String payCardType;
    private String payCardExp;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isAccountIsEnabled() {
        return accountIsEnabled;
    }

    public void setAccountIsEnabled(boolean accountIsEnabled) {
        this.accountIsEnabled = accountIsEnabled;
    }

    public Date getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime) {
        this.unlockTime = unlockTime;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressApt() {
        return addressApt;
    }

    public void setAddressApt(String addressApt) {
        this.addressApt = addressApt;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getPayCardNumber() {
        return payCardNumber;
    }

    public void setPayCardNumber(String payCardNumber) {
        this.payCardNumber = payCardNumber;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
    }

    public String getPayCardExp() {
        return payCardExp;
    }

    public void setPayCardExp(String payCardExp) {
        this.payCardExp = payCardExp;
    }

    @Override
    public String toString() {
        return "EntityAccount [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName="
                + lastName + ", phoneNumber=" + phoneNumber + ", creationDate=" + creationDate + ", AccountIsEnabled="
                + accountIsEnabled + ", unlockTime=" + unlockTime + ", addressNumber=" + addressNumber
                + ", addressStreet=" + addressStreet + ", addressApt=" + addressApt + ", addressCity=" + addressCity
                + ", addressZip=" + addressZip + ", addressState=" + addressState + ", payCardNumber=" + payCardNumber
                + ", payCardType=" + payCardType + ", payCardExp=" + payCardExp + "]";
    }

    public EntityAccount() {
    }

    public EntityAccount(String email, String password, String firstName, String lastName, String phoneNumber,
            Date creationDate, boolean accountIsEnabled, Date unlockTime, String addressNumber, String addressStreet,
            String addressApt, String addressCity, String addressZip, String addressState, String payCardNumber,
            String payCardType, String payCardExp) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.accountIsEnabled = accountIsEnabled;
        this.unlockTime = unlockTime;
        this.addressNumber = addressNumber;
        this.addressStreet = addressStreet;
        this.addressApt = addressApt;
        this.addressCity = addressCity;
        this.addressZip = addressZip;
        this.addressState = addressState;
        this.payCardNumber = payCardNumber;
        this.payCardType = payCardType;
        this.payCardExp = payCardExp;
    }
}
