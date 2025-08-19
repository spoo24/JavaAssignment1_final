### Assignment 1 – GeekCafe Ordering System

This project implements a café ordering system in Java. It covers both Part A (Procedural Implementation) and Part B (Object-Oriented Implementation) of the assignment requirements

Core classes:

- FoodItem.java – represents a food item with price, stock, sold count, and revenue.

- Combo.java – represents a combo (e.g., coffee + muffin) with a discount.

- Order.java – manages items and combos in a single order, calculates totals, and finalizes sales.

- GeekCafe.java - menu-driven interface for running the café simulation. 

- Main.java – main class.

- OutOfRangeException.java – custom exception used for menu input validation.

- OrderTest.java - validates calculation logic (totals, discounts, stock updates).

How to Run

> 1. Compile the source code:

```
> javac Combo.java
> javac FoodItem.java
> javac GeekCafe.java
> javac Main.java
> javac Order.java
> javac OutOfRangeException.java
```

> 2. Run the main program.
```
> java Main
```
This will launch the menu-driven application in the console.

> 3. Run JUnit Tests

Make sure JUnit 4 is on your classpath (paths may differ depending on your Eclipse installation). Example:

```
> javac -cp ".;C:\Users\spoor\.p2\pool\plugins\org.junit_4.13.2.v20240929-1000.jar;C:\Users\spoor\.p2\pool\plugins\org.hamcrest_3.0.0.jar" OrderTest.java


> java -cp ".;C:\Users\spoor\.p2\pool\plugins\org.junit_4.13.2.v20240929-1000.jar;C:\Users\spoor\.p2\pool\plugins\org.hamcrest_3.0.0.jar" org.junit.runner.JUnitCore OrderTest
```
