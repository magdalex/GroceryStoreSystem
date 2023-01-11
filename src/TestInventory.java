import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestInventory {
    // class for initially seeding the product database
    // or for testing the logic using a temporary makeshift database
    private static List<EntityProduct> inventory;

    public static void seed() {
        inventory = new ArrayList<EntityProduct>();
        // Fruits
        inventory.add(new EntityProduct(pID(), "Gala Apple", "A sweet, juicy, crunchy fruit. approximately 200g.", 0.88,
                "Fruit", 299));
        inventory.add(new EntityProduct(pID(), "Empire Apple", "A sweet, juicy, crunchy fruit. approximately 195g.",
                0.81, "Fruit", 187));
        inventory.add(new EntityProduct(pID(), "Banana", "A sweet, soft yellow fruit. approximately 315g.", 0.63,
                "Fruit", 411));
        // Meat
        inventory.add(new EntityProduct(pID(), "Chicken", "Raw meat, tastes like chicken. 450g.", 6.99,
                "Meat", 84));
        inventory.add(new EntityProduct(pID(), "Porc", "Raw meat, tastes like chicken. 450g.", 3.99,
                "Meat", 84));
        inventory.add(new EntityProduct(pID(), "Beef", "Raw meat, doesn't taste like chicken. 450g.", 7.99,
                "Meat", 84));
        // Dairy
        inventory.add(new EntityProduct(pID(), "Cheese", "Milk that's gone bad. Very tasty. 225g.", 3.77,
                "Dairy", 34));
        inventory.add(new EntityProduct(pID(), "Milk", "White liquid, 3.25% fat content. 1L.", 1.83,
                "Dairy", 34));
        inventory.add(new EntityProduct(pID(), "Milk", "White liquid, 2% fat content. 2L.", 3.22,
                "Dairy", 17));
    }

    private static String pID() {
        Random r = new Random();
        String pID = "";
        for (int i = 0; i < 10; i++) {
            pID += r.nextInt(0, 10);
        }
        return pID;
    }

    public static List<EntityProduct> search(String keyword, boolean searchName, boolean searchDescription,
            boolean searchCategory) {
        List<EntityProduct> productResults = new ArrayList<EntityProduct>();

        for (EntityProduct entityProduct : inventory) {
            if (searchName) {
                if (entityProduct.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                    productResults.add(entityProduct);
            }
            if (searchCategory) {
                if (!productResults.contains(entityProduct)) {
                    if (entityProduct.getProductCategory().toLowerCase().contains(keyword.toLowerCase())) {
                        productResults.add(entityProduct);
                    }
                }
            }
            if (searchDescription) {
                if (!productResults.contains(entityProduct)) {
                    if (entityProduct.getProductDescription().toLowerCase().contains(keyword.toLowerCase())) {
                        productResults.add(entityProduct);
                    }
                }
            }
        }
        return productResults;
    }

    public static void Menu(Scanner scan) {
        String key;
        Boolean loop = true;
        while (loop) {
            System.out.println("\nSelect an option:\n\t1.List all products\n\t2.Search\n\t3.Exit");
            key = scan.nextLine();
            switch (key) {
                case "1":
                    System.out.println("\n");
                    inventory.forEach(System.out::println);
                    loop = false;
                    break;
                case "2":
                    System.out.println("Enter the keyword:");
                    key = scan.nextLine();
                    search(key, true, true, true).forEach(System.out::println);
                    loop = false;
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    System.out.println("Bad input, try again.");
                    break;
            }
        }

    }
}
