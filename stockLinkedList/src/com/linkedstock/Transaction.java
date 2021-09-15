package com.linkedstock;

public class Transaction {
	final static String BUY = "buy";
	final static String SELL = "sell";

	private String state;
	private String dateTime;
	private long noOfShares;

	Transaction() {

	}

	Transaction(String dateTime, long noOfShares, String state) {
		this.dateTime = dateTime;
		this.noOfShares = noOfShares;
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public long getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}

}
