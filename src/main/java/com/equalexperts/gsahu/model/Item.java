package com.equalexperts.gsahu.model;

import java.math.BigDecimal;

public class Item {
	private int id;
	private String productName;
	private BigDecimal unitPrice;
	private BigDecimal taxRate;
	private BigDecimal taxAmount;
	
	public Item(int id, String productName, BigDecimal unitPrice, BigDecimal taxRate) {
		this.id = id;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.taxRate = taxRate;
		this.taxAmount = unitPrice.multiply(taxRate);
	}
	
	public int getId() {
		return id;
	}
	public String getProductName() {
		return productName;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

}