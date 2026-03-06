package org.example;

import org.example.Amazon.*;
import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.Amazon.Cost.ItemType.ELECTRONIC;
import static org.example.Amazon.Cost.ItemType.OTHER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {
    private final Item book = new Item(OTHER, "book", 3, 10.0);
    private final Item pen = new Item(OTHER, "pen", 1, 1.25);
    private final Item laptop = new Item(ELECTRONIC, "laptop", 1, 500.0);

    private static Database db;
    private static ShoppingCart shoppingCart;

    @BeforeEach
    public void clearDatabase(){
        db = new Database();
        db.resetDatabase();
    }

    @DisplayName("specification-based")
    @Test
    public void emptyCartReturnsZero(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double expected = 0.0;
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("specification-based")
    @Test
    public void nonElectronicAndDeliveryCart(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        a.addToCart(book);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double expected = 30.0 + 5.0;
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("specification-based")
    @Test
    public void electronicCart(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        a.addToCart(laptop);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double expected = 512.50;
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("structural-based")
    @Test
    public void test4ItemsDeliveryRuleBoundaries(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        // 4 distinct items
        a.addToCart(pen);
        a.addToCart(pen);
        a.addToCart(pen);
        a.addToCart(pen);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double deliveryFee = 12.5;
        double expected = deliveryFee + (1.25 * 4);
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("structural-based")
    @Test
    public void test10ItemsDeliveryRuleBoundaries(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        // 10 distinct items
        for(int i = 0; i < 10; i++){
            a.addToCart(pen);
        }

        //Act
        double finalPrice = a.calculate();

        //Assert
        double deliveryFee = 12.5;
        double expected = deliveryFee + (1.25 * 10);
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("structural-based")
    @Test
    public void test11ItemsDeliveryRuleBoundaries(){
        //Arrange
        shoppingCart = new ShoppingCartAdaptor(db);

        PriceRule rc = new RegularCost();
        PriceRule ece = new ExtraCostForElectronics();
        PriceRule dc = new DeliveryPrice();
        List<PriceRule> rules = new ArrayList<>(List.of(rc, ece, dc));

        Amazon a = new Amazon(shoppingCart, rules);

        //11 distinct items
        for(int i = 0; i < 11; i++){
            a.addToCart(pen);
        }

        //Act
        double finalPrice = a.calculate();

        //Assert
        double deliveryFee = 20;
        double expected = deliveryFee + (1.25 * 11);
        assertEquals(expected, finalPrice, 0.001);
    }
}
