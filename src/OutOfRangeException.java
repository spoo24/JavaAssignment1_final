/**
 * OutOfRangeException.java
 * 
 * Represents a custom exception class to handle invalid menu selections.
 * Thrown when the user selects an option outside the allowed range.
 */
public class OutOfRangeException extends Exception {

    /**
     * Default constructor that prints a user-friendly error message.
     */
    public OutOfRangeException() {
        System.out.println("Error: Please select valid options.\n");
    }
}
