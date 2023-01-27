import java.util.Scanner;

public class Checkout {

    public static void Menu(Scanner scan, EntityCart cart, EntityAccount account) throws ClassNotFoundException {
        System.out.println();
        boolean loop = true;
        String payCardType = "";
        String payCardNum = "";
        int payCardExpMonth = 0;
        int payCardExpYear = 0;
        String input = "";
        // payment type
        while (loop) {
            System.out.println(
                    "Choose your payment type:\n\t1.Visa\n\t2.Mastercard\n\t3.American Express\n\t4.My saved payment info\n\t5.Return to shopping");
            String key = scan.nextLine();
            switch (key) {
                case "1":
                    payCardType = "Visa";
                    loop = false;
                    break;
                case "2":
                    payCardType = "Mastercard";
                    loop = false;
                    break;
                case "3":
                    payCardType = "America Express";
                    loop = false;
                    break;
                case "4":
                    payCardType = "default";
                    loop = false;
                case "5":
                    Shop.Menu(scan, account);
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input, try again.");
                    break;
            }
        }
        loop = true;
        // card number
        while (loop && !payCardType.contentEquals("default")) {
            System.out.println("Enter your card number:");
            payCardNum = scan.nextLine();
            if (payCardNum.length() != 16) {
                System.out.println("Card number is invalid.");
            } else {
                loop = false;
            }
        }
        loop = true;
        while (loop && !payCardType.contentEquals("default")) {
            System.out.println("Enter the expiration year:");
            input = scan.nextLine();
            try {
                payCardExpYear = Integer.parseInt(input);
                if (payCardExpYear > 2050 || payCardExpYear < 2022) {
                    System.out.println("Not a valid expiration year");
                    throw new Exception();
                }
                System.out.println("Enter the expiration month number:");
                input = scan.nextLine();
                try {
                    payCardExpMonth = Integer.parseInt(input);
                    if (payCardExpMonth > 12 || payCardExpMonth < 1) {
                        System.out.println("Not a valid expiration month");
                        throw new Exception();
                    }
                    loop = false;
                } catch (Exception e) {
                    System.out.println("Invalid input, try again.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                break;
            }

        }
        loop = true;
        System.out.println("---FUNCTION NEEDS TO BE IMPLEMENTED---");
    }

}
