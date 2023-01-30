import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginSystem {
	static List<EntityAccount> accounts = new ArrayList<EntityAccount>();
	public static final String dbConnection = "jdbc:sqlserver://vanier-grocery-service.database.windows.net:1433;database=VanierGroceryService;user=remyAzure@vanier-grocery-service;password=Vanier1212;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// new scanner to be passed to methods
		Scanner scan = new Scanner(System.in);
		greetingPage(scan);
		scan.close();
	}

	public static void greetingPage(Scanner scan)
			throws IOException, ClassNotFoundException {
		System.out.println("Do you have an existing account already?");
		System.out.println("Type Yes if you do, type No if you don't");
		String answer = scan.nextLine();
		while (true)
			if (answer.equalsIgnoreCase("yes")) {
				logIn(scan);
				break;
			} else if (answer.equalsIgnoreCase("no")) {
				signUp(scan);
				break;
			} else {
				System.out.println("Please enter a valid answer!");
				answer = scan.nextLine();
			}
	}

	public static void logIn(Scanner scan)
			throws IOException, ClassNotFoundException {
		retrieveAccounts();
		int count = 0;

		while (true) {
			// prompt user
			System.out.println("Please input email");
			String email = scan.nextLine();
			System.out.println("Please enter your password");
			String password = scan.nextLine();
			// try getting account
			final String email2 = email;
			EntityAccount account = null;
			try {
				account = accounts.stream().filter(e -> e.getEmail().equalsIgnoreCase(email2)).findFirst().get();
			} catch (Exception e) {

			}

			// check if it's time to enable account
			try {
				if (!account.isAccountIsEnabled()) {
					if (account.getUnlockTime().before(new Date(new java.util.Date().getTime()))) {
						account.setAccountIsEnabled(true);
					}
					// TODO:update database lock
				}
			} catch (Exception e) {

			}

			// check account exists and is enabled
			if (account != null) {
				if (account.isAccountIsEnabled()) {
					if (account.getPassword().contentEquals(password)) {
						System.out.println("You have succesfully logged in!");
						EntityProduct.shopMenu(scan, account);
					} else {
						// bad password, same as account doesn't exists but locks the account after 3x
						System.out.println("Invalid login, try again.");
						count++;
						if (count == 3) {
							accounts.stream().filter(e -> e.getEmail().equalsIgnoreCase(email2)).findFirst().get()
									.setAccountIsEnabled(false);
							java.util.Date unlock = new java.util.Date();
							unlock.setTime(unlock.getTime() + 1800000);
							Date unlockSQL = new Date(unlock.getTime());
							accounts.stream().filter(e -> e.getEmail().equalsIgnoreCase(email2)).findFirst().get()
									.setUnlockTime(unlockSQL);
							// TODO:update database lock
							System.out.println(
									"You will now be sent to the Sign-Up page due to multiple invalid login.");
							signUp(scan);
							break;
						}
					}
				} else {
					System.out.println("Invalid login, try again.");
					count++;
					if (count == 3) {
						System.out.println(
								"You will now be sent to the Sign-Up page due to multiple invalid login.");
						signUp(scan);
						break;
					}
				}

				//
				// account doesn't exist
			} else {
				System.out.println("Invalid login, try again.");
				count++;
				if (count == 3) {
					System.out.println(
							"You will now be sent to the Sign-Up page due to multiple invalid login.");
					signUp(scan);
					break;
				}
			}
		}
	}

	public static void signUp(Scanner scan) throws IOException, ClassNotFoundException {
		System.out.println("Welcome to sign up");
		System.out.println("Please enter a email");
		String emailSignUp = scan.nextLine();
		while (true) {
			final String email2 = emailSignUp;
			EntityAccount account = null;
			try {
				account = accounts.stream().filter(e -> e.getEmail().equalsIgnoreCase(email2)).findFirst().get();
			} catch (Exception e) {
			}
			if (account != null) {
				System.out.println("This email is already in use... Please try again");
				emailSignUp = scan.nextLine();
			} else {
				break;
			}
		}
		System.out.println("Please enter a password at least 5 characters long");
		String passwordSignUp = scan.nextLine();
		while (true) {
			if (passwordSignUp.length() < 5) {
				System.out.println("Password too short... Please try again");
				passwordSignUp = scan.nextLine();
			} else {
				break;
			}
		}
		System.out.println("Enter your first name:");
		String firstName = scan.nextLine();
		System.out.println("Enter your last name:");
		String lastName = scan.nextLine();
		System.out.println("Enter your phone number:");
		String phoneNumber = scan.nextLine();
		System.out.println("Enter your address number:");
		String addressNumber = scan.nextLine();
		System.out.println("Enter your address Street:");
		String addressStreet = scan.nextLine();
		System.out.println("Enter your appartment number (enter 'none' if you don't have one):");
		String addressApt = scan.nextLine();
		if (addressApt.equalsIgnoreCase("none"))
			addressApt = null;
		System.out.println("Enter your city:");
		String addressCity = scan.nextLine();
		System.out.println("Enter your postal code:");
		String AddressZip = scan.nextLine();
		System.out.println("Enter your province:");
		String addressState = scan.nextLine();
		Date creationDate = new Date(new java.util.Date().getTime());
		EntityAccount newAccount = new EntityAccount(emailSignUp, passwordSignUp, firstName, lastName, phoneNumber,
				creationDate, false, creationDate, addressNumber, addressStreet, addressApt, addressCity, AddressZip,
				addressState, addressCity, AddressZip, addressState);
		accounts.add(newAccount);
		AddAccountToDB(newAccount);
		System.out.println("Welcome! You have succesfully signed up!");
		// return to start
		greetingPage(scan);
	}

	public static void retrieveAccounts() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = LoginSystem.dbConnection;
		ResultSet resultSet = null;
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {
			// Create and execute a SELECT SQL statement.
			String selectSql = "SELECT * FROM Accounts";
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
				EntityAccount account = new EntityAccount();
				account.setEmail(resultSet.getString(1));
				account.setPassword(resultSet.getString(2));
				account.setFirstName(resultSet.getString(3));
				account.setLastName(resultSet.getString(4));
				account.setPhoneNumber(resultSet.getString(5));
				account.setAddressNumber(resultSet.getString(6));
				account.setAddressStreet(resultSet.getString(7));
				account.setAddressApt(resultSet.getString(8));
				account.setAddressCity(resultSet.getString(9));
				account.setAddressZip(resultSet.getString(10));
				account.setAddressState(resultSet.getString(11));
				account.setPayCardNumber(resultSet.getString(12));
				account.setPayCardType(resultSet.getString(13));
				account.setPayCardExp(resultSet.getString(14));
				account.setCreationDate(Date.valueOf(resultSet.getString(15)));
				account.setAccountIsEnabled(resultSet.getString(16).equalsIgnoreCase("true") ? true : false);
				try {
					account.setUnlockTime(Date.valueOf(resultSet.getString(17)));
				} catch (Exception e) {
					account.setUnlockTime(null);
				}
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void AddAccountToDB(EntityAccount newAccount) throws ClassNotFoundException {
		// alias for cleaner code
		EntityAccount a = newAccount;
		// connection stuff
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try (Connection connection = DriverManager.getConnection(LoginSystem.dbConnection);
				Statement statement = connection.createStatement();) {
			// Create and execute an insert SQL statement.
			String sql = "insert into Accounts(emailAccount,password,firstName,lastName,phoneNumber,addressNumber,";
			sql += "addressStreet,";
			sql += "addressCity,";
			sql += "addressZip,";
			sql += "addressState,";
			sql += "creationDate,";
			sql += "accountEnabled";
			sql += ")Values('";
			sql += a.getEmail() + "','" + a.getPassword() + "','" + a.getFirstName() + "','" + a.getLastName() + "','"
					+ a.getPhoneNumber() + "','" + a.getAddressNumber() + "','" + a.getAddressStreet() + "','"
					+ a.getAddressCity() + "','" + a.getAddressZip() + "','" + a.getAddressState() + "','"
					+ a.getCreationDate() + "','true');";
			int rowsUpdated = statement.executeUpdate(sql);
			if (rowsUpdated != 1)
				throw new SQLException("zero row updated");
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
