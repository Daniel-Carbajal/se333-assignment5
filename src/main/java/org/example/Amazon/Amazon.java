package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;

import java.util.List;

public class Amazon {
    // Dependencies to mock for unit tests
    private final List<PriceRule> rules;
    private final ShoppingCart carts;

    public Amazon(ShoppingCart carts, List<PriceRule> rules) {
        this.carts = carts;
        this.rules = rules;
    }

    // Sum price of all items in a shopping cart using their price rules
    public double calculate() {
        double finalPrice = 0;

        // For each price rule (RegularCost, ExtraCostForElectronics, DeliveryPrice)
        for (PriceRule rule : rules) {
            // Calculate and add that price rules total based on all items in cart
            finalPrice += rule.priceToAggregate(carts.getItems());
        }

        return finalPrice;
    }

    public void addToCart(Item item){
        carts.add(item);
    }
}
