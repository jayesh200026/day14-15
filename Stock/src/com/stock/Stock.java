package com.stock;

public class Stock {
	String name;
	double price;
	long noOfShare;
	double totalValue;

	Stock(String name, double price, long noOfShare) {
		this.name = name;
		this.price = price;
		this.noOfShare = noOfShare;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public String toString() {
		return "Stock [name=" + name + ", price=" + price + ", noOfShare=" + noOfShare + ", totalValue=" + totalValue
				+ "]";
	}

}
