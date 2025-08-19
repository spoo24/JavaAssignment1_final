import java.util.*;

/**
 * Order.java
 * 
 * Represents a customer's order at Geek Cafe.
 * 
 * An Order can contain both individual food items and combos.
 * Muffins are stock-limited, so their availability must be
 * checked before confirming an order.
 *
 * Pricing Rules:
 * - Regular food items are charged at their listed price.
 * - Combo items apply a $1 discount split equally across items 
 *   ($0.50 off the beverage, $0.50 off the muffin).
 *
 * Sales tracking ensures that combo purchases are still
 * recorded against the individual items, but at discounted rates.
 */
public class Order {

    /** Stores regular food items and their ordered quantities */
    private Map<FoodItem, Integer> itemOrder = new HashMap<>();

    /** Stores combos and their ordered quantities */
    private Map<Combo, Integer> comboOrder = new HashMap<>();

    /** Tracks the number of muffins ordered so far (to avoid overselling stock) */
    private int muffinsOrderedSoFar = 0;

    /**
     * Adds a regular food item to the order.
     *
     * @param item the food item
     * @param qty  the quantity requested
     * @return true if successfully added, false if stock is insufficient
     */
    public boolean addItem(FoodItem item, int qty) {
        if (item.getName().equalsIgnoreCase("muffin")) {
            int available = item.getStock() - muffinsOrderedSoFar;
            if (qty > available) {
                return false;
            }
            muffinsOrderedSoFar += qty;
        }
        itemOrder.put(item, itemOrder.getOrDefault(item, 0) + qty);
        return true;
    }

    /**
     * Adds a combo to the order.
     * Validates that enough muffins are available for the combo.
     *
     * @param combo the combo being ordered
     * @param qty   the quantity requested
     * @return true if successfully added, false if not enough muffins are available
     */
    public boolean addCombo(Combo combo, int qty) {
        FoodItem muffin = combo.getMuffin();
        int available = muffin.getStock() - muffinsOrderedSoFar;
        if (qty > available) {
            return false;
        }
        muffinsOrderedSoFar += qty;
        comboOrder.put(combo, comboOrder.getOrDefault(combo, 0) + qty);
        return true;
    }

    /**
     * Calculates the total cost of the order, applying discounts
     * for combos and full price for individual items.
     *
     * @return total order price
     */
    public double calculateTotal() {
        double total = 0.0;

        // Regular items at full price
        for (Map.Entry<FoodItem, Integer> e : itemOrder.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }

        // Combos: each item discounted by $0.50
        for (Map.Entry<Combo, Integer> e : comboOrder.entrySet()) {
            Combo c = e.getKey();
            int qty = e.getValue();
            double beveragePrice = c.getBeverage().getPrice() - 0.50;
            double muffinPrice = c.getMuffin().getPrice() - 0.50;
            total += (beveragePrice + muffinPrice) * qty;
        }

        return total;
    }

    /**
     * Finalizes the order:
     * - Updates sales/revenue tracking for food items and combos.
     * - Applies discounted pricing for items inside combos.
     */
    public void finalizeOrder() {
        // Regular items at full price
        for (Map.Entry<FoodItem, Integer> e : itemOrder.entrySet()) {
            e.getKey().sell(e.getValue());
        }

        // Combo items sold individually with adjusted prices
        for (Map.Entry<Combo, Integer> e : comboOrder.entrySet()) {
            Combo c = e.getKey();
            int qty = e.getValue();

            double beveragePrice = c.getBeverage().getPrice() - 0.50;
            double muffinPrice = c.getMuffin().getPrice() - 0.50;

            c.getBeverage().sell(qty, beveragePrice);
            c.getMuffin().sell(qty, muffinPrice);
        }
    }

    /**
     * Returns how many muffins are already included in this order.
     * Used to prevent overselling stock.
     *
     * @return muffins ordered so far
     */
    public int getMuffinsOrderedSoFar() {
        return muffinsOrderedSoFar;
    }
}
