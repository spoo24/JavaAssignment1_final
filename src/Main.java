/**
 * Main.java
 * Entry point for the GeekCafe application.
 * 
 * This class launches the program by creating a GeekCafe object
 * and displaying its main menu.
 * 
 */
public class Main {
    /**
     * Main method - starts the GeekCafe application.
     * @param args Command line arguments (not used here).
     */
    public static void main(String[] args) {
        GeekCafe cafe = new GeekCafe();
        cafe.printMainMenu();
    }
}

//@todo not enough muffins need to go back to main menu the first one (c) I hadn't thought of this. Either automatically Cancel an order to bake or proceed with baking more muffins for the existing order, either option is acceptable.)
//@todo add a readme file
//@todo add a exit condition for every menu options like payment 

