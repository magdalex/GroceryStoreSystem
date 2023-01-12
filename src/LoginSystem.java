import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LoginSystem {
	// don't specify if email/password is wrong for better security instead just say
	// "something" is wrong.

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// new scanner to be passed to methods
		Scanner scan = new Scanner(System.in);

		// mock database
		HashMap<String, String> database = Userdata.read();

		greetingPage(scan, database);
		scan.close();
	}

	public static void greetingPage(Scanner scan, HashMap<String, String> database)
			throws IOException, ClassNotFoundException {
		System.out.println("Do you have an existing account already?");
		System.out.println("Type Yes if you do, type No if you don't");
		String answer = scan.nextLine();
		while (true)
			if (answer.equalsIgnoreCase("yes")) {
				logIn(scan, database);
				break;

			} else if (answer.equalsIgnoreCase("no")) {
				signUp(scan, database);
				break;

			} else {
				System.out.println("Please enter a valid answer!");
				answer = scan.nextLine();
			}
	}

	public static void logIn(Scanner scan, HashMap<String, String> database)
			throws IOException, ClassNotFoundException {
		System.out.println("Please input email");
		String email = scan.nextLine();

		// email input
		int count = 1;
		while (true) {
			if (database.containsKey(email)) {
				logInPass(scan, database, email);
				break;
			} else {
				System.out.println("Please enter a valid email or type 1 if you'd like to create an account.");
				email = scan.nextLine();
				count++;
				if (count == 3) {
					System.out.println(
							"You will now be sent to the Sign-Up page due to multiple invalid email inputs" + '\n');
					signUp(scan, database);
					break;
				}
				if (email.equalsIgnoreCase("1")) {
					signUp(scan, database);
					break;
				}
			}
		}
	}

	public static void logInPass(Scanner scan, HashMap<String, String> database, String email)
			throws ClassNotFoundException {

		// password input
		System.out.println("Please enter your password");
		String password = scan.nextLine();
		int count = 1;
		while (true) {
			if (database.get(email).contentEquals(password)) {
				System.out.println("You have succesfully logged in!");

				// continue after successful login
				TestInventory.retrieveDB();
				TestInventory.Menu(scan);

				break;
			} else {
				System.out.println("Please enter a valid password.");
				password = scan.nextLine();
				count++;

				if (count == 3) {
					System.out.println(
							"Your account is now locked for 30 minutes due to multiple invalid password entries.");
					break;
				}
			}
		}

	}

	public static void signUp(Scanner scan, HashMap<String, String> database)
			throws IOException, ClassNotFoundException {
		System.out.println("Welcome to sign up");
		System.out.println("Please enter a email");
		String emailSignUp = scan.nextLine();
		while (true) {
			if (database.containsKey(emailSignUp)) {
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
		database.put(emailSignUp, passwordSignUp);
		System.out.println("Welcome! You have succesfully signed up!");
		Userdata.write(database);
		greetingPage(scan, database);
	}

}
