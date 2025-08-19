/**
 * FoodItem.java
 * 
 * Represents a single food item in the GeekCafe menu.
 * It stores details such as the item's name, price, stock (if applicable),
 * quantity sold, and total revenue generated.
 *
 * This class also provides methods to adjust stock, handle sales at 
 * both standard and discounted prices, and retrieve sales statistics.
 */
public class FoodItem {
    private String name;       // Name of the food item
    private double price;      // Standard price of the item
    private int stock;         // Available stock (mainly for muffins)
    private int soldCount;     // Total number of items sold
    private double revenue;    // Total revenue generated from sales

    /**
     * Constructs a FoodItem with a name, price, and initial stock.
     *
     * @param name  The name of the food item.
     * @param price The price of the food item.
     * @param stock The initial stock available.
     */
    public FoodItem(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.soldCount = 0;
        this.revenue = 0.0;
    }

    // ----- Getters & Setters -----

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    /**
     * Increases the stock by a given quantity.
     *
     * @param qty The quantity to add to stock.
     */
    public void addStock(int qty) {
        this.stock += qty;
    }

    /**
     * Checks if there is enough stock to sell a given quantity.
     *
     * @param qty The quantity to check against stock.
     * @return true if enough stock exists, false otherwise.
     */
    public boolean hasStock(int qty) {
        return stock >= qty;
    }

    // ----- Sale Operations -----

    /**
     * Records a sale at the standard price.
     *
     * @param qty The quantity being sold.
     */
    public void sell(int qty) {
        sell(qty, this.price);
    }

    /**
     * Records a sale with a custom sale price (e.g., discounts/combos).
     *
     * @param qty       The quantity being sold.
     * @param salePrice The price per item for this sale.
     */
    public void sell(int qty, double salePrice) {
        this.stock -= qty;
        this.soldCount += qty;
        this.revenue += qty * salePrice;
    }

    // ----- Statistics -----

    public int getSoldCount() {
        return soldCount;
    }

    public double getRevenue() {
        return revenue;
    }
}
