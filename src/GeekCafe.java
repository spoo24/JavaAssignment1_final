import java.util.*;

/**
 * GeekCafe.java
 * 
 * Represents the main controller class for The Geek Cafe application.
 * Handles menu navigation, customer orders, baking muffins, updating prices,
 * and generating sales reports.
 * 
 */
public class GeekCafe {

    /** Stores available food items keyed by lowercase name. */
    private Map<String, FoodItem> menu;

    /** Stores the list of available combos. */
    private List<Combo> combos;

    /** Scanner for user input. */
    private Scanner input;

    /**
     * Constructor initializes the menu items and available combos.
     */
    public GeekCafe() {
        input = new Scanner(System.in);

        // Initialize food items
        menu = new LinkedHashMap<>();
        menu.put("muffin", new FoodItem("Muffin", 2.0, 25));
        menu.put("shake", new FoodItem("Shake", 3.0, 0));
        menu.put("coffee", new FoodItem("Coffee", 2.5, 0));

        // Initialize combos
        combos = new ArrayList<>();
        combos.add(new Combo("Coffee + Muffin", menu.get("coffee"), menu.get("muffin"), 1.0));
        combos.add(new Combo("Shake + Muffin", menu.get("shake"), menu.get("muffin"), 1.0));
    }

    /**
     * Displays the main menu and handles user navigation.
     */
    public void printMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("===============================================================");
            System.out.println("The Geek Cafe");
            System.out.println("===============================================================");
            System.out.println("a. Order");
            System.out.println("b. Bake muffins");
            System.out.println("c. Show sales report");
            System.out.println("d. Update prices");
            System.out.println("e. Exit");
            System.out.print("Please select: ");

            String choice;
            try {
                choice = input.nextLine().trim().toLowerCase();
                if (choice.length() != 1 || !"abcde".contains(choice)) {
                    throw new OutOfRangeException();
                }
            } catch (OutOfRangeException e) {
                continue;
            }

            switch (choice) {
                case "a":
                    takeOrder();
                    break;
                case "b":
                    bakeMuffins();
                    break;
                case "c":
                    showSalesReport();
                    break;
                case "d":
                    updatePrices();
                    break;
                case "e":
                    System.out.println("Bye Bye.");
                    exit = true;
                    break;
            }
        }
    }

    /**
     * Handles the process of taking an order from a customer.
     * Allows selection of individual food items or combos.
     */
//    private void takeOrder() {
//        Order order = new Order();
//        boolean orderedBefore = false;
//
//        while (true) {
//            System.out.println("Select the food item");
//            int i = 1;
//
//            List<FoodItem> itemList = new ArrayList<>(menu.values());
//            for (FoodItem item : itemList) {
//                System.out.printf("%d. %s%n", i++, item.getName());
//            }
//            for (Combo combo : combos) {
//                System.out.printf("%d. %s (Combo)%n", i++, combo.getName());
//            }
//
//            System.out.printf("%d. Go back%n", i++);
//            if (orderedBefore) {
//                System.out.printf("%d. No more%n", i++);
//            }
//            System.out.print("Please select: ");
//
//            int maxOption = i - 1;
//            int opt;
//            try {
//                opt = Integer.parseInt(input.nextLine().trim());
//                if (opt < 1 || opt > maxOption) {
//                    throw new OutOfRangeException();
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Invalid number.\n");
//                continue;
//            } catch (OutOfRangeException e) {
//                continue;
//            }
//            // handling exit scenarios
//            // Go back
//            if (orderedBefore && opt == maxOption - 1)
//            	return;
//            if (!orderedBefore && opt == maxOption)
//                return;
//            // No more
//            if (orderedBefore && opt == maxOption)
//                break;
//
//            if (opt <= itemList.size()) {
//                // Handle food item order
//                FoodItem item = itemList.get(opt - 1);
//                int qty = promptForQuantity(item);
////                if not enough muffins returning to main menu to bake more
//                if (qty == 0) {
//                	return;
//                }
//                if (qty > 0 && !order.addItem(item, qty)) {
//                    int available = item.getStock() - order.getMuffinsOrderedSoFar();
//                    System.out.printf("Sorry, only %d %ss available, please bake more. \n", available, item.getName());
//                    return;
//                }
//            } else {
//                // Handle combo order
//                Combo combo = combos.get(opt - itemList.size() - 1);
//                int qty = promptForComboQuantity(combo);
//                if (!order.addCombo(combo, qty)) {
//                    int available = combo.getMuffin().getStock() - order.getMuffinsOrderedSoFar();
//                    System.out.printf("Sorry, only %d muffins available for combos, please bake more. \n", available);
//                    continue;
//                }
//            }
//            orderedBefore = true;
//        }
//
//        // Process payment
//        double total = order.calculateTotal();
//        if (total <= 0.0) {
//            System.out.println("Invalid order, returning to main menu.\n");
//            return;
//        }
//
//        System.out.printf("Total cost: $%.2f%n", total);
//        processPayment(order, total);
//    }

    private void takeOrder() {
        Order order = new Order();
        boolean orderedBefore = false;

        while (true) {
            System.out.println("Select the food item:");
            System.out.println("1. Muffin");
            System.out.println("2. Shake");
            System.out.println("3. Coffee");
            System.out.println("4. Coffee + Muffin (Combo)");
            System.out.println("5. Shake + Muffin (Combo)");
            System.out.println("6. Go back");
            if (orderedBefore) {
                System.out.println("7. No more");
            }
            System.out.print("Please select: ");

            int opt;
            try {
                opt = Integer.parseInt(input.nextLine().trim());
                if (!orderedBefore && (opt < 1 || opt > 6)) {
                    throw new OutOfRangeException();
                } else if (orderedBefore && (opt < 1 || opt > 7)) {
                    throw new OutOfRangeException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number.\n");
                continue;
            } catch (OutOfRangeException e) {
                continue;
            }

            // handle back/no more
            if (!orderedBefore && opt == 6) return; // Go back
            if (orderedBefore && opt == 6) return;  // Go back
            if (orderedBefore && opt == 7) break;   // No more

            switch (opt) {
                case 1: { // Muffin
                    FoodItem muffin = menu.get("muffin");
                    int qty = promptForQuantity(muffin);
                    if (qty == 0) return; // 0 = back to menu
                    if (qty > 0 && !order.addItem(muffin, qty)) {
                        int available = muffin.getStock() - order.getMuffinsOrderedSoFar();
                        System.out.printf("Sorry, only %d muffins available, please bake more.\n", available);
                        return;
                    }
                    break;
                }
                case 2: { // Shake
                    FoodItem shake = menu.get("shake");
                    int qty = promptForQuantity(shake);
                    if (qty == 0) return;
                    if (qty > 0 && !order.addItem(shake, qty)) {
                        System.out.println("Error adding shake to order.");
                        return;
                    }
                    break;
                }
                case 3: { // Coffee
                    FoodItem coffee = menu.get("coffee");
                    int qty = promptForQuantity(coffee);
                    if (qty == 0) return;
                    if (qty > 0 && !order.addItem(coffee, qty)) {
                        System.out.println("Error adding coffee to order.");
                        return;
                    }
                    break;
                }
                case 4: { // Coffee + Muffin combo
                    Combo coffeeCombo = combos.get(0); // Coffee + Muffin
                    int qty = promptForComboQuantity(coffeeCombo);
                    if (qty == 0) return;
                    if (!order.addCombo(coffeeCombo, qty)) {
                        int available = coffeeCombo.getMuffin().getStock() - order.getMuffinsOrderedSoFar();
                        System.out.printf("Sorry, only %d muffins available for combos, please bake more.\n", available);
                        return;
                    }
                    break;
                }
                case 5: { // Shake + Muffin combo
                    Combo shakeCombo = combos.get(1); // Shake + Muffin
                    int qty = promptForComboQuantity(shakeCombo);
                    if (qty == 0) return;
                    if (!order.addCombo(shakeCombo, qty)) {
                        int available = shakeCombo.getMuffin().getStock() - order.getMuffinsOrderedSoFar();
                        System.out.printf("Sorry, only %d muffins available for combos, please bake more.\n", available);
                        return;
                    }
                    break;
                }
            }

            orderedBefore = true;
        }

        // Process payment
        double total = order.calculateTotal();
        if (total <= 0.0) {
            System.out.println("Invalid order, returning to main menu.\n");
            return;
        }

        System.out.printf("Total cost: $%.2f%n", total);
        processPayment(order, total);
    }
    
    /**
     * Prompts the user for a valid quantity of a food item.
     */
    private int promptForQuantity(FoodItem item) {
        int qty;
        while (true) {
            System.out.printf("How many %ss would you like (or 0 to go back to main menu): ", item.getName());
            try {
                qty = Integer.parseInt(input.nextLine().trim());
                if (qty == 0) {
                	return 0;
                } else if (item.getName().equalsIgnoreCase("muffin") && !item.hasStock(qty)) {
                    System.out.printf("Only %d muffins available, please bake more\n", item.getStock());
                    return 0;
                } else if (qty < 0) {
                    System.out.println("Please enter a positive number.\n");
                } else {
                    return qty;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number.\n");
            }
        }
    }

    /**
     * Prompts the user for a valid quantity of a combo.
     */
    private int promptForComboQuantity(Combo combo) {
        int qty;
        while (true) {
            try {
                System.out.printf("How many %s combos would you like  (or 0 to go back to main menu): ", combo.getName());
                qty = Integer.parseInt(input.nextLine().trim());
                if (qty == 0) {
                	return 0;
                } else if (qty < 0) {
                    System.out.println("Please enter a positive number.\n");
                } else {
                    return qty;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number.\n");
            }
        }
    }

    /**
     * Handles payment processing for an order.
     */
    private void processPayment(Order order, double total) {
        while (true) {
            try {
                System.out.print("Please enter payment (or 0 to go back to main menu): ");
                double payment = Double.parseDouble(input.nextLine().trim());
                if (payment == 0) {
                    System.out.println("Order cancelled. Returning to main menu.\n");
                	return;
                } else if (payment < total) {
                    System.out.println("Insufficient payment, try again\n");
                    continue;
                }
                double change = payment - total;
                System.out.printf("Change: $%.2f%n", change);
                order.finalizeOrder();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid payment.\n");
            }
        }
    }

    /**
     * Adds 25 new muffins to stock.
     */
    private void bakeMuffins() {
        menu.get("muffin").addStock(25);
        System.out.printf("Ok, 25 Muffins added. Total muffins in cafe is now %d.%n",
                menu.get("muffin").getStock());
    }

    /**
     * Displays the sales report showing unsold muffins, 
     * sales of each item, and total revenue.
     */
    private void showSalesReport() {
        System.out.println("--------------------------------------");
        System.out.println("SALES REPORT");
        System.out.println("--------------------------------------");
        System.out.println("Unsold Muffins: " + menu.get("muffin").getStock());
        System.out.println("Total Sales:");

        int totalUnits = 0;
        double totalRevenue = 0.0;

        for (FoodItem item : menu.values()) {
            System.out.printf("%s: %d $%.2f%n", item.getName(),
                    item.getSoldCount(), item.getRevenue());
            totalUnits += item.getSoldCount();
            totalRevenue += item.getRevenue();
        }

        System.out.println("--------------------------------------");
        System.out.printf("%d $%.2f%n", totalUnits, totalRevenue);
    }

    /**
     * Allows updating the price of a food item.
     */
//    private void updatePrices() {
//        List<FoodItem> items = new ArrayList<>(menu.values());
//        int opt;
//
//        while (true) {
//            System.out.println("Select the food item:");
//            int i = 1;
//            for (FoodItem item : items) {
//                System.out.printf("%d. %s%n", i++, item.getName());
//            }
//            System.out.printf("%d. Go Back!%n", i);
//            System.out.print("Please select: ");
//
//            try {
//                opt = Integer.parseInt(input.nextLine().trim());
//                if (opt < 1 || opt > i)
//                    throw new OutOfRangeException();
//                else if (opt == i)
//                	return;
//                else
//                	break;
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Invalid input.\n");
//            } catch (OutOfRangeException e) {
//                continue;
//            }
//        }
//        
//        FoodItem item = items.get(opt - 1);
//        System.out.printf("%s currently costs $%.2f%n", item.getName(), item.getPrice());
//
//        while (true) {
//            try {
//                System.out.print("Enter new price  (or 0 to go back to main menu):: ");
//                double newPrice = Double.parseDouble(input.nextLine().trim());
//
//                if (newPrice == 0) {
//                    System.out.println("Order cancelled. Returning to main menu.\n");
//                	return;
//                } else if (newPrice < 0) {
//                    System.out.println("Please enter a positive number.\n");
//                    continue;
//                }
//                item.setPrice(newPrice);
//                System.out.printf("Price updated. %s now costs $%.2f%n", item.getName(), newPrice);
//                break;
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Invalid input.\n");
//            }
//        }
//    }
    
    /** Allows updating the price of a food item using fixed menu options */
    private void updatePrices() {
        while (true) {
            System.out.println("Select the food item to update:");
            System.out.println("1. Muffin");
            System.out.println("2. Shake");
            System.out.println("3. Coffee");
            System.out.println("4. Go Back");
            System.out.print("Please select: ");

            int opt;
            try {
                opt = Integer.parseInt(input.nextLine().trim());
                if (opt < 1 || opt > 4) {
                    throw new OutOfRangeException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input.\n");
                continue;
            } catch (OutOfRangeException e) {
                continue;
            }

            FoodItem item = null;
            switch (opt) {
                case 1: item = menu.get("muffin"); break;
                case 2: item = menu.get("shake"); break;
                case 3: item = menu.get("coffee"); break;
                case 4: return;
            }

            if (item == null) {
                System.out.println("Error: Invalid option.\n");
                continue;
            }

            System.out.printf("%s currently costs $%.2f%n", item.getName(), item.getPrice());

            while (true) {
                try {
                    System.out.print("Enter new price (or 0 to cancel & go back): ");
                    double newPrice = Double.parseDouble(input.nextLine().trim());

                    if (newPrice == 0) {
                        System.out.println("Price update cancelled. Returning to main menu.\n");
                        return;
                    } else if (newPrice < 0) {
                        System.out.println("Please enter a positive number.\n");
                        continue;
                    }

                    item.setPrice(newPrice);
                    System.out.printf("Price updated. %s now costs $%.2f%n", item.getName(), newPrice);
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input.\n");
                }
            }
        }
    }

}
