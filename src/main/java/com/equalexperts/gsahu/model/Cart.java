package com.equalexperts.gsahu.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
	private static Cart cart;
	private int totalItems;
	private BigDecimal subTotal;
	private BigDecimal totalTax;
	private BigDecimal netAmount;
	private Map<String, List<Item>> itemMap;

	public Cart(Map<String, List<Item>> itemMap, BigDecimal subTotal, int totalItems, BigDecimal totalTax) {
		this.itemMap = itemMap;
		this.subTotal = subTotal;
		this.totalItems = totalItems;
		this.totalTax = totalTax;
	}

	public int getTotalItems() {
		return totalItems;
	}
	
	public BigDecimal getTotalTax() {
		return totalTax.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getSubTotal() {
		return subTotal.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getNetAmount() {
		return netAmount.setScale(2, RoundingMode.HALF_UP);
	}

	public Map<String, List<Item>> getItemMap() {
		return itemMap;
	}

	public static Cart getInstance() {
		if (cart == null) {
			cart = new Cart(new HashMap<String, List<Item>>(), new BigDecimal(0.00), 0, new BigDecimal(0.00));
		}

		return cart;
	}

	public void addItem(Item item) {
		List<Item> itemList;
		String productName = item.getProductName();
		
		if(itemMap.containsKey(productName)) {
			itemList = itemMap.get(productName);
			itemList.add(item);
			itemMap.put(productName, itemList);
		} else {
			itemList = new ArrayList<Item>();
			itemList.add(item);
			itemMap.put(productName, itemList);
		}
		
		subTotal = subTotal.add(item.getUnitPrice());
		totalTax = totalTax.add(item.getTaxAmount());
		++totalItems;
	}
	
	public void removeItem(Item item) {
		String productName = item.getProductName();
		List<Item> itemList = itemMap.get(productName);
		
		if(itemList.size() == 1) {
			itemMap.remove(productName);
		} else {
			itemList.remove(item);
			itemMap.put(productName, itemList);
		}
		
		subTotal = subTotal.subtract(item.getUnitPrice());
		totalTax = totalTax.subtract(item.getTaxAmount());
		--totalItems;
	}
	
	public void calculateCart() {
		netAmount = getSubTotal().add(getTotalTax());
	}
	
	public void clear() {
		itemMap.clear();
		subTotal = new BigDecimal(0.00);
		totalItems = 0;
		totalTax = new BigDecimal(0.00);
	}
}