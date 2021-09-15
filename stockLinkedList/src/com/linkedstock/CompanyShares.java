package com.linkedstock;

import java.util.ArrayList;
import java.util.List;

public class CompanyShares {
	List<Transaction> transactions = new ArrayList<>();

	private String stockSymbol;
	private long noOfShares;

	CompanyShares() {

	}

	CompanyShares(String stockSymbol) {
		this.stockSymbol = stockSymbol;
		this.noOfShares = 0;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}

	public List<Transaction> getList() {
		return transactions;
	}

	public void setList(List<Transaction> list) {
		this.transactions = list;
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
}
