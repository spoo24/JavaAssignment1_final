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
