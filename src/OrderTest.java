import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Unit tests for the Order class. 
 *
 * These tests validate:
 * - Correct calculation of totals for items and combos
 * - Combo discounts being applied properly
 * - Muffin stock management across items and combos
 * - Sold counts and revenue tracking for each FoodItem
 */
public class OrderTest {
    // Shared test fixtures
    private FoodItem muffin;       // Muffin item
    private FoodItem coffee;       // Coffee item
    private FoodItem shake;        // Shake item
    private Order order;           // Order under test

    private Combo coffeeMuffinCombo;   // Coffee + Muffin combo
    private Combo shakeMuffinCombo;    // Shake + Muffin combo

    /**
     * Set up fresh objects before each test.
     * Ensures tests are independent and do not interfere with each other.
     */
    @Before
    public void setUp() {
        muffin = new FoodItem("Muffin", 2.00, 25);   // Price $2, stock 25
        coffee = new FoodItem("Coffee", 2.50, 0);    // Price $2.50, stock irrelevant
        shake  = new FoodItem("Shake", 3.00, 0);     // Price $3.00, stock irrelevant
        order  = new Order();

        // Create common combos used across multiple tests
        coffeeMuffinCombo = new Combo("Coffee + Muffin", coffee, muffin, 1.0); // $1 discount
        shakeMuffinCombo  = new Combo("Shake + Muffin", shake, muffin, 1.0);   // $1 discount
    }

    /**
     * Nullify references after each test.
     */
    @After
    public void tearDown() {
        muffin = null;
        coffee = null;
        shake = null;
        order = null;
        coffeeMuffinCombo = null;
        shakeMuffinCombo = null;
    }

    /**
     * Verify total calculation for individual items only (no combos).
     * Expected:
     *  - 4 muffins @ $2.00 = $8.00
     *  - 2 coffees @ $2.50 = $5.00
     *  Total = $13.00
     */
    @Test
    public void calculateTotalItemsOnly() {
        assertTrue(order.addItem(muffin, 4));
        assertTrue(order.addItem(coffee, 2));
        assertEquals(13.00, order.calculateTotal(), 0.001);
    }

    /**
     * Verify that combo discount is applied correctly.
     * Combo: Coffee + Muffin = (2.00 + 2.50 - 1.00) = $3.50 per combo
     *  - 3 combos = $10.50
     */
    @Test
    public void calculateTotalWithComboDiscount() {
        assertTrue(order.addCombo(coffeeMuffinCombo, 3));
        assertEquals(10.50, order.calculateTotal(), 0.001);
    }

    /**
     * Ensure muffin stock constraints are respected across items + combos.
     * Muffin stock = 6
     *  - Order 2 muffins directly (remaining stock 4)
     *  - Order 3 Shake+Muffin combos (uses 3 more muffins, remaining stock 1)
     *  - Attempt 2 more combos should fail (requires 2 muffins > 1 left)
     *  Final muffins ordered = 5
     */
    @Test
    public void muffinStockGuardAcrossItemsAndCombos() {
        muffin = new FoodItem("Muffin", 2.00, 6);  // Override stock for this test
        Combo shakeCombo = new Combo("Shake + Muffin", shake, muffin, 1.0);

        assertTrue(order.addItem(muffin, 2));
        assertTrue(order.addCombo(shakeCombo, 3));
        assertFalse(order.addCombo(shakeCombo, 2));
        assertEquals(5, order.getMuffinsOrderedSoFar());
    }

    /**
     * Check that muffin stock reduces correctly after finalizing orders.
     * Muffin stock starts at 25.
     * Ordered: 3 muffins + 2 coffee combos + 4 shake combos = 9 muffins total.
     * Final stock = 25 - 9 = 16
     */
    @Test
    public void calculateMuffinStockAfterFinalize() {
        assertTrue(order.addItem(muffin, 3));
        assertTrue(order.addCombo(coffeeMuffinCombo, 2));
        assertTrue(order.addCombo(shakeMuffinCombo, 4));

        order.finalizeOrder();
        assertEquals(16, muffin.getStock());
    }

    /**
     * Validate that sold counts are updated correctly for all FoodItems.
     * Ordered:
     *  - Muffins: 3 direct + 2 (coffee combo) + 4 (shake combo) = 9
     *  - Coffee: 1 direct + 2 (coffee combo) = 3
     *  - Shake: 6 direct + 4 (shake combo) = 10
     */
    @Test
    public void calculateSoldCountAfterFinalize() {
        assertTrue(order.addItem(muffin, 3));
        assertTrue(order.addItem(coffee, 1));
        assertTrue(order.addItem(shake, 6));
        assertTrue(order.addCombo(coffeeMuffinCombo, 2));
        assertTrue(order.addCombo(shakeMuffinCombo, 4));

        order.finalizeOrder();

        assertEquals(9, muffin.getSoldCount());
        assertEquals(3, coffee.getSoldCount());
        assertEquals(10, shake.getSoldCount());
    }

    /**
     * Validate that revenue from combos and items is correctly summed.
     * - 2 Coffee+Muffin combos = (2 + 2.50 - 1) * 2 = 7.00
     * - 1 Shake+Muffin combo = (3 + 2 - 1) = 4.00
     * - 3 Coffees = 3 * 2.50 = 7.50
     * Expected total = 18.50
     * Check both Order total and sum of item revenues match.
     */
    @Test
    public void calculateRevenueSumAcrossItemsAndCombos() {
        assertTrue(order.addCombo(coffeeMuffinCombo, 2));
        assertTrue(order.addCombo(shakeMuffinCombo, 1));
        assertTrue(order.addItem(coffee, 3));

        double expected = 18.50;
        assertEquals(expected, order.calculateTotal(), 0.001);

        order.finalizeOrder();
        double revenueSum = coffee.getRevenue() + muffin.getRevenue() + shake.getRevenue();
        assertEquals(expected, revenueSum, 0.001);
    }
}
