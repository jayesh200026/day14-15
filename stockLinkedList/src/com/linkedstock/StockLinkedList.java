package com.linkedstock;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author jayeshkumar Implements the Commercial data processing
 */
public class StockLinkedList {
	final static String STOCKS_FILE = "data/stockAccount.json";
	final static String RESULT_FILE = "data/stockAccountResult.json";

	private static StockAccount stock = new StockAccount(RESULT_FILE);

	public static void stockLinkedList() {
		stock.init();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the Stock broker program\nSelect an Option to proceed");

		while (true) {
			System.out.println("Main Menu");
			System.out.println("1. Buy Stocks\n2. Sell Stocks\n3. Print Stock Report\nAny other choice: Exit");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				buyStocks();
				break;
			case 2:
				sellStocks();
				break;
			case 3:
				stock.printReport();
				break;
			default:
				return;
			}
		}
	}

	/**
	 * Method to buy the stock and updating in files
	 */
	private static void buyStocks() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Select the stock to buy:");
		JSONArray stocks = read();
		Iterator<JSONObject> itr = stocks.iterator();
		int count = 1;
		while (itr.hasNext()) {
			System.out.println(count + ":");
			JSONObject stock = itr.next();
			System.out.println("Stock Name: " + stock.get("stockName"));
			System.out.println("Stock Symbol: " + stock.get("stockSymbol"));
			System.out.println("Share price: " + stock.get("sharePrice"));
			System.out.println("Number Of Shares: " + stock.get("noOfShares"));
			System.out.println();
			count++;
		}

		int stockChoice = scanner.nextInt();
		while (stockChoice >= count) {
			System.out.println("Invalid option");
			stockChoice = scanner.nextInt();
		}

		System.out.println("Enter the number of shares to buy:");
		int amount = scanner.nextInt();
		JSONObject selectedStock = (JSONObject) stocks.get(stockChoice - 1);
		while (amount > (long) selectedStock.get("noOfShares") || amount <= 0) {
			System.out.println("Enter a valid amount");
			amount = scanner.nextInt();
		}

		stock.buy(amount, (String) selectedStock.get("stockSymbol"));
		stock.save(RESULT_FILE);
	}

	/**
	 * Method to sell the stock and updating in files
	 */
	private static void sellStocks() {
		Scanner in = new Scanner(System.in);

		System.out.println("Select the stock you want to Sell");
		int count = 1;
		for (Node<CompanyShares> companyShare : stock.getCompanyShares()) {
			System.out.println(count + ":");
			System.out.println("Stock Symbol : " + companyShare.getData().getStockSymbol());
			System.out.println("Number Of Shares : " + companyShare.getData().getNoOfShares());
			System.out.println();
			count++;
		}

		int stockChoice = in.nextInt();
		while (stockChoice >= count) {
			System.out.println("Invalid option");
			stockChoice = in.nextInt();
		}

		System.out.println("Enter number of shares to sell");
		int amount = in.nextInt();
		Node<CompanyShares> selectedStock = stock.getCompanyShares().get(stockChoice - 1);
		while (amount > (long) selectedStock.getData().getNoOfShares() || amount <= 0) {
			System.out.println("Enter a valid amount");
			amount = in.nextInt();
		}

		stock.sell(amount, selectedStock.getData().getStockSymbol());
		stock.save(RESULT_FILE);
	}

	/**
	 * @return the content of file in JSONArray format
	 */
	private static JSONArray read() {
		try {
			FileReader reader = new FileReader(STOCKS_FILE);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			JSONArray stocks = (JSONArray) obj.get("stocks");
			return stocks;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		StockLinkedList.stockLinkedList();
	}
}
