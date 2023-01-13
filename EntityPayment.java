import java.security.SecureRandom;

public class EntityPayment {

	private String orderID;
	private String paymentID;
	private double totalCost;
	private String defaultCardNum;
	private String defaultCardCVV;
	private String defaultCardExp;
	private String defaultCardType;
	private String email;
	private String paymentCardNum;
	private String paymentCardCVV;
	private String paymentCardExp;
	private String paymentCardType;
	boolean isPaid = false;

	public EntityPayment(EntityAccount account, EntityOrder order) {
		this.totalCost = account.totalCost;
		this.orderID = order.orderID;
		this.defaultCardNum = account.cardNum;
		this.defaultCardType = account.cardType;
		this.defaultCardCVV = account.defaultCardCVV;
		this.defaultCardExp = account.defaultCardExp;
		this.email = account.email;
		this.isPaid = order.isPaid;
	}

	public void paymentProcess() {
		System.out.println("Your total cost is :" + totalCost + "$" + '\n' + '\n');

		System.out.println(
				"Please select payment option by pressing 1 for default payment method or 2 to enter a new payment method");
		int option = 0;
		if (option == 1) {
			paymentCardNum = defaultCardNum;
			defaultCardCVV = defaultCardCVV;
			paymentCardType = defaultCardType;

		} else if (option == 2) {
			System.out.println("Please enter the card type");
			paymentCardType = null;

			System.out.println("Please enter card id");
			paymentCardNum = null;

			System.out.println("Please enter card security code");
			paymentCardCode = null;

			System.out.println("Please enter card expiration date in [  ] format");
			paymentCardCode = null;

		}
		System.out.println("Press enter to complete payment");

		System.out.println("Payment COMPlETE!");
		isPaid = true;

	}

	public static String paymentIdGenerator() {
		String prevId = "ORDER000001";
		String data = prevId.replace("ORDER", "");
		int id = Integer.parseInt(data);
		id = id + 1;
		
		String newId = "";

		String newId = "ORDER";
		if (id >= 1 && id <= 9) {
			newId = numberData + "00000" + id;
		} else if (id >= 10 && id <= 99) {

			newId = numberData + "0000" + id;
		} else if (id >= 100 && id <= 999) {

			newId = numberData + "000" + id;
		} else if (id >= 1000 && id <= 9999) {

			newId = numberData + "00" + id;
		} else if (id >= 10000 && id <= 99999) {

			newId = numberData + "0" + id;
		}
		return newId;

	}

}