package com.equalexperts.gsahu.tests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.equalexperts.gsahu.model.Cart;
import com.equalexperts.gsahu.model.Item;
import com.equalexperts.gsahu.util.Constants;

public class ShoppingCartTest {

	@Test
	public void testAddProduct() {
		BigDecimal unitPrice = new BigDecimal(39.99), taxRate = new BigDecimal(0.00);
		Item item;
		List<Item> itemList;

		//Get Cart instance
		Cart cart = Cart.getInstance();
		cart.clear();

		//Adding 5 Dove Soaps with unit price 39.99 to the cart
		for(int i=1; i<6; i++) {
			item = new Item(i, Constants.DOVE_SOAP, unitPrice, taxRate);
			cart.addItem(item);
		}

		//Calculate net amount of items in cart
		cart.calculateCart();

		//Checking if the cart contains 5 Dove soaps each with a unit price of 39.99
		itemList = cart.getItemMap().get(Constants.DOVE_SOAP);
		Iterator<Item> itr = itemList.iterator();
		Assert.assertEquals(5, cart.getTotalItems());
		Assert.assertEquals(5, itemList.size());		
		
		while(itr.hasNext()) {
			item = itr.next();
			Assert.assertEquals(Constants.DOVE_SOAP, item.getProductName());
			Assert.assertEquals(39.99, item.getUnitPrice().doubleValue(), 0.00);
		}

		//Checking if the net amount of cart is 199.95
		double ex = new BigDecimal(199.95).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double actual = cart.getNetAmount().doubleValue(); 
		Assert.assertEquals(ex, actual, 0.00);
	}
	
	@Test
	public void testAddProductsOfSameType() {
		BigDecimal unitPrice = new BigDecimal(39.99), taxRate = new BigDecimal(0.00);
		Item item;
		List<Item> itemList;

		//Get Cart instance
		Cart cart = Cart.getInstance();
		cart.clear();

		//Adding 5 Dove Soaps with unit price 39.99 to the cart
		for(int i=1; i<6; i++) {
			item = new Item(i, Constants.DOVE_SOAP, unitPrice, taxRate);
			cart.addItem(item);
		}

		//Adding 3 more Dove Soaps with unit price 39.99 to the cart
		for(int i=6; i<9; i++) {
			item = new Item(i, Constants.DOVE_SOAP, unitPrice, taxRate);
			cart.addItem(item);
		}

		//Calculate net amount of items in cart
		cart.calculateCart();

		//Checking if the cart contains 8 Dove soaps each with a unit price of 39.99
		itemList = cart.getItemMap().get(Constants.DOVE_SOAP);
		Iterator<Item> itr = itemList.iterator();
		Assert.assertEquals(8, cart.getTotalItems());
		Assert.assertEquals(8, itemList.size());		

		while(itr.hasNext()) {
			item = itr.next();
			Assert.assertEquals(Constants.DOVE_SOAP, item.getProductName());
			Assert.assertEquals(39.99, item.getUnitPrice().doubleValue(), 0.00);
		}

		//Checking if the net amount of items in cart is 319.92
		double ex = new BigDecimal(319.92).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double actual = cart.getNetAmount().doubleValue(); 
		Assert.assertEquals(ex, actual, 0.00);
	}
	
	@Test
	public void testRemoveAddedProduct() {
		BigDecimal unitPrice = new BigDecimal(39.99), taxRate = new BigDecimal(12.50);
		Item item;
		List<Item> itemList;

		//Get Cart instance
		Cart cart = Cart.getInstance();
		cart.clear();

		//Adding a Dove soap with unit price 39.99 to the cart
		item = new Item(1, Constants.DOVE_SOAP, unitPrice, taxRate);
		cart.addItem(item);
		
		//Calculate net amount of items in the cart
		cart.calculateCart();
		
		//Removing item from the cart
		cart.removeItem(item);
		
		//Calculate net amount of items in the cart
		cart.calculateCart();

		//Checking if cart has 0 items
		itemList = cart.getItemMap().get(Constants.DOVE_SOAP);
		Assert.assertEquals(0, cart.getTotalItems());
		Assert.assertNull(itemList);
		
		//Checking if net amount of items in cart is 0.00
		double ex = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double actual = cart.getNetAmount().doubleValue(); 
		Assert.assertEquals(ex, actual, 0.00);
	}
	
	@Test
	public void testCalculateTaxRate() {
		BigDecimal doveUnitPrice = new BigDecimal(39.99).setScale(2, RoundingMode.HALF_UP); 
		BigDecimal axeUnitPrice = new BigDecimal(99.99).setScale(2, RoundingMode.HALF_UP);
		BigDecimal taxRate = new BigDecimal(0.125);
		Item item;
		List<Item> itemList;

		//Get Cart instance
		Cart cart = Cart.getInstance();
		cart.clear();

		//Adding 2 Dove Soaps with unit price 39.99 to the cart
		for(int i=1; i<3; i++) {
			item = new Item(i, Constants.DOVE_SOAP, doveUnitPrice, taxRate);
			cart.addItem(item);
		}

		//Adding 2 Axe Deos with unit price 39.99 to the cart
		for(int i=3; i<5; i++) {
			item = new Item(i, Constants.AXE_DEO, axeUnitPrice, taxRate);
			cart.addItem(item);
		}

		//Calculate net amount of items in the cart
		cart.calculateCart();

		//Checking if the cart contains 4 items
		Assert.assertEquals(4, cart.getTotalItems());

		//Checking if the cart contains 2 Dove soaps with a unit price of 39.99
		itemList = cart.getItemMap().get(Constants.DOVE_SOAP);
		Assert.assertEquals(2, itemList.size());
		Iterator<Item> itr = itemList.iterator();

		while(itr.hasNext()) {
			item = itr.next();
			Assert.assertEquals(Constants.DOVE_SOAP, item.getProductName());
			Assert.assertEquals(39.99, item.getUnitPrice().doubleValue(), 0.00);
		}

		//Checking if the cart contains 2 Axe deos with a unit price of 99.99
		itemList = cart.getItemMap().get(Constants.AXE_DEO);
		Assert.assertEquals(2, itemList.size());
		itr = itemList.iterator();

		while(itr.hasNext()) {
			item = itr.next();
			Assert.assertEquals(Constants.AXE_DEO, item.getProductName());
			Assert.assertEquals(99.99, item.getUnitPrice().doubleValue(), 0.00);
		}

		double exTotalAmount = new BigDecimal(314.96).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double actualTotalAmount = cart.getNetAmount().doubleValue(); 
		Assert.assertEquals(exTotalAmount, actualTotalAmount, 0.00);
	}

}
