package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.RegularCost;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AmazonUnitTest {
    @DisplayName("specification-based")
    @Test
    public void addToCartDelegationTest(){
        //Arrange
        ShoppingCart sc = mock(ShoppingCart.class);
        Item item = new Item(ItemType.OTHER, "test item", 5, 10.0);
        RegularCost rc = mock(RegularCost.class); // Could be just price rule too
        List<PriceRule> priceRuleList = new ArrayList<>(List.of(rc));

        Amazon a = new Amazon(sc, priceRuleList);

        //Act
        a.addToCart(item);

        //Assert
        verify(sc, times(1)).add(item);
    }

    @DisplayName("specification-based")
    @Test
    public void calculateSumsOutputOfAllPriceRules(){
        //Arrange
        ShoppingCart sc = mock(ShoppingCart.class);
        PriceRule rule1 = mock(PriceRule.class);
        PriceRule rule2 = mock(PriceRule.class);
        PriceRule rule3 = mock(PriceRule.class);

        List<PriceRule> priceRuleList = new ArrayList<>(List.of(rule1,rule2,rule3));

        Amazon a = new Amazon(sc, priceRuleList);

        when(rule1.priceToAggregate(sc.getItems())).thenReturn(10.0);
        when(rule2.priceToAggregate(sc.getItems())).thenReturn(15.0);
        when(rule3.priceToAggregate(sc.getItems())).thenReturn(20.5);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double expected = 10.0 + 15.0 + 20.5;
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("specification-based")
    @Test
    public void calculateWithEmptyCart() {
        //Arrange
        ShoppingCart sc = mock(ShoppingCart.class);
        PriceRule rule1 = mock(PriceRule.class);
        PriceRule rule2 = mock(PriceRule.class);

        List<PriceRule> priceRuleList = new ArrayList<>(List.of(rule1, rule2));

        Amazon a = new Amazon(sc, priceRuleList);

        when(sc.getItems()).thenReturn(new ArrayList<>());

        when(rule1.priceToAggregate(sc.getItems())).thenReturn(0.0);
        when(rule2.priceToAggregate(sc.getItems())).thenReturn(0.0);

        //Act
        List<Item> cartContent = sc.getItems();
        double finalPrice = a.calculate();

        //Assert
        double expected = 0.0 + 0.0;
        List<Item> emptyItemList = new ArrayList<>();

        assertEquals(expected, finalPrice, 0.001);
        assertEquals(emptyItemList, cartContent);
    }

    @DisplayName("specification-based")
    @Test
    public void calculateReturnsZeroWithNoRules() {
        //Arrange
        ShoppingCart sc = mock(ShoppingCart.class);

        List<PriceRule> priceRuleList = new ArrayList<>();

        Amazon a = new Amazon(sc, priceRuleList);

        //Act
        double finalPrice = a.calculate();

        //Assert
        double expected = 0.0;
        assertEquals(expected, finalPrice, 0.001);
    }

    @DisplayName("structural-based")
    @Test
    public void eachRuleCalledOnce(){

    }

    @DisplayName("stuctural-based")
    @Test
    public void getItemsCalledOnce(){
        //Arrange
        ShoppingCart sc = mock(ShoppingCart.class);
        PriceRule rule1 = mock(PriceRule.class);
        PriceRule rule2 = mock(PriceRule.class);
        PriceRule rule3 = mock(PriceRule.class);

        List<PriceRule> priceRuleList = new ArrayList<>(List.of(rule1, rule2, rule3));

        Amazon a = new Amazon(sc, priceRuleList);

        when(sc.getItems()).thenReturn(new ArrayList<>());

        //Act
        a.calculate();

        //Assert
        verify(sc, times(priceRuleList.size())).getItems();
        verify(rule1, times(1)).priceToAggregate(anyList());
    }
}
