/**
 * Combo.java
 * 
 * Represents a combo deal consisting of a beverage (coffee or shake) 
 * and a muffin, with a discount applied to the combined price.
 * 
 * Each Combo object keeps track of:
 * - The combo name
 * - The included beverage (FoodItem)
 * - The included muffin (FoodItem)
 * - The discount percentage (or value)
 * 
 * This class is primarily used for grouping items together 
 * to simplify sales and applying discounts.
 */
public class Combo {
    private String name;
    private FoodItem beverage; // The beverage in the combo (coffee or shake)
    private FoodItem muffin;   // The muffin included in the combo
    private double discount;   // Discount applied to total combo price

    /**
     * Creates a new Combo.
     * 
     * @param name     The name of the combo
     * @param beverage The beverage included in the combo
     * @param muffin   The muffin included in the combo
     * @param discount The discount applied (e.g., 0.10 for 10% discount)
     */
    public Combo(String name, FoodItem beverage, FoodItem muffin, double discount) {
        this.name = name;
        this.beverage = beverage;
        this.muffin = muffin;
        this.discount = discount;
    }

    /**
     * Gets the name of the combo.
     * 
     * @return Combo name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the beverage included in the combo.
     * 
     * @return Beverage FoodItem
     */
    public FoodItem getBeverage() {
        return beverage;
    }

    /**
     * Gets the muffin included in the combo.
     * 
     * @return Muffin FoodItem
     */
    public FoodItem getMuffin() {
        return muffin;
    }

    /**
     * Gets the discount applied to the combo.
     * 
     * @return Discount value
     */
    public double getDiscount() {
        return discount;
    }
}
