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
    private FoodItem m;  // Muffin
    private FoodItem c;  // Coffee
    private FoodItem s;  // Shake
    private Order o;     // Order under test

    private Combo coffeeCombo;  // Coffee + Muffin combo
    private Combo shakesCombo;  // Shake + Muffin combo

    /**
     * Set up fresh objects before each test.
     * Ensures tests are independent and do not interfere with each other.
     */
    @Before
    public void setUp() {
        m = new FoodItem("Muffin", 2.00, 25);   // Price $2, stock 25
        c = new FoodItem("Coffee", 2.50, 0);    // Price $2.50, stock irrelevant
        s = new FoodItem("Shake", 3.00, 0);     // Price $3.00, stock irrelevant
        o = new Order();

        // Create common combos used across multiple tests
        coffeeCombo = new Combo("Coffee + Muffin", c, m, 1.0); // $1 discount
        shakesCombo = new Combo("Shake + Muffin", s, m, 1.0);  // $1 discount
    }

    /**
     *  nullifing references  after each test.
     */
    @After
    public void tearDown() {
        m = null;
        c = null;
        s = null;
        o = null;
        coffeeCombo = null;
        shakesCombo = null;
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
        assertTrue(o.addItem(m, 4));
        assertTrue(o.addItem(c, 2));
        assertEquals(13.00, o.calculateTotal(), 0.001);
    }

    /**
     * Verify that combo discount is applied correctly.
     * Combo: Coffee + Muffin = (2.00 + 2.50 - 1.00) = $3.50 per combo
     *  - 3 combos = $10.50
     */
    @Test
    public void calculateTotalWithComboDiscount() {
        assertTrue(o.addCombo(coffeeCombo, 3));
        assertEquals(10.50, o.calculateTotal(), 0.001);
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
        m = new FoodItem("Muffin", 2.00, 6);  // Override stock for this test
        Combo combo = new Combo("Shake + Muffin", s, m, 1.0);

        assertTrue(o.addItem(m, 2));
        assertTrue(o.addCombo(combo, 3));
        assertFalse(o.addCombo(combo, 2));
        assertEquals(5, o.getMuffinsOrderedSoFar());
    }

    /**
     * Check that muffin stock reduces correctly after finalizing orders.
     * Muffin stock starts at 12.
     * Ordered: 3 muffins + 2 coffee combos + 4 shake combos = 9 muffins total.
     * Final stock = 12 - 9 = 3
     */
    @Test
    public void calculateMuffinStock() {
        assertTrue(o.addItem(m, 3));
        assertTrue(o.addCombo(coffeeCombo, 2));
        assertTrue(o.addCombo(shakesCombo, 4));

        o.finalizeOrder();
        assertEquals(16, m.getStock());
    }

    /**
     * Validate that sold counts are updated correctly for all FoodItems.
     * Ordered:
     *  - Muffins: 3 direct + 2 (coffee combo) + 4 (shake combo) = 9
     *  - Coffee: 1 direct + 2 (coffee combo) = 3
     *  - Shake: 6 direct + 4 (shake combo) = 10
     */
    @Test
    public void calculateSoldCount() {
        assertTrue(o.addItem(m, 3));
        assertTrue(o.addItem(c, 1));
        assertTrue(o.addItem(s, 6));
        assertTrue(o.addCombo(coffeeCombo, 2));
        assertTrue(o.addCombo(shakesCombo, 4));

        o.finalizeOrder();

        assertEquals(9, m.getSoldCount());
        assertEquals(3, c.getSoldCount());
        assertEquals(10, s.getSoldCount());
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
    public void calculateRevenueSum() {
        assertTrue(o.addCombo(coffeeCombo, 2));
        assertTrue(o.addCombo(shakesCombo, 1));
        assertTrue(o.addItem(c, 3));

        double expected = 18.50;
        assertEquals(expected, o.calculateTotal(), 0.001);

        o.finalizeOrder();
        double revenueSum = c.getRevenue() + m.getRevenue() + s.getRevenue();
        assertEquals(expected, revenueSum, 0.001);
    }
}

