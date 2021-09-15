package com.commercial;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author jayeshkumar In this class all the mathematics will take place i.e
 *         buying,seling saving and printing stock.
 */
public class StockAccount implements StockInterface {
	private String fileName;
	private JSONArray stocksData;
	List<CompanyShares> companyShares = new ArrayList<>();

	StockAccount(String fileName) {
		this.fileName = fileName;
	}

	public JSONArray getStocksData() {
		return stocksData;
	}

	public String getFileName() {
		return fileName;
	}

	public List<CompanyShares> getCompanyShares() {
		return companyShares;
	}

	public void setCompanyShares(List<CompanyShares> companyShares) {
		this.companyShares = companyShares;
	}

	/**
	 * Initializes the file
	 */
	public void init() {
		List<CompanyShares> companySharesList = new ArrayList<CompanyShares>();
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(fileName);
			JSONObject jsonObj = (JSONObject) parser.parse(reader);
			JSONArray companyShares = (JSONArray) jsonObj.get("companyShares");
			Iterator<JSONObject> iterator = companyShares.iterator();

			while (iterator.hasNext()) {
				CompanyShares companyShare = new CompanyShares();
				JSONObject compShare = iterator.next();
				companyShare.setStockSymbol(compShare.get("stockSymbol").toString());
				companyShare.setNoOfShares((long) compShare.get("noOfShares"));

				JSONArray transactions = (JSONArray) compShare.get("transactions");
				Iterator<JSONObject> iterator2 = transactions.iterator();

				List<Transaction> transactionList = new ArrayList<Transaction>();
				while (iterator2.hasNext()) {
					Transaction transaction1 = new Transaction();
					JSONObject transaction = iterator2.next();
					transaction1.setDateTime(transaction.get("DateTime").toString());
					transaction1.setNoOfShares((long) transaction.get("noOfShares"));
					transaction1.setState((String) transaction.get("State"));
					transactionList.add(transaction1);
				}
				companyShare.setList(transactionList);
				companySharesList.add(companyShare);
			}
			this.companyShares = companySharesList;
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param symbol               = symbol of share
	 * @param noOfShares=number    of shares person bought or soldd
	 * @param companyShare=company share value
	 * @param state=buy/sell
	 * 
	 *                             Updates the values after buying and selling
	 */
	private void update(String symbol, long noOfShares, CompanyShares companyShare, String state) {
		readStock();
		long prevShares = companyShare.getNoOfShares();
		if (state == Transaction.BUY) {
			companyShare.setNoOfShares(prevShares + noOfShares);
		} else {
			companyShare.setNoOfShares(prevShares - noOfShares);
		}
		long millis = System.currentTimeMillis(); // To get the current time in milli seconds
		Date dateTime = new Date(millis);
		Transaction transaction = new Transaction(dateTime.toString(), noOfShares, state);// invokles parameterized
																							// constructor
		companyShare.addTransaction(transaction);
		companyShares.add(companyShare);

		Iterator<JSONObject> iterator = stocksData.iterator();

		while (iterator.hasNext()) {
			JSONObject stock = iterator.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				prevShares = (long) stock.get("noOfShares");
				stock.remove("noOfShares");
				if (state == Transaction.BUY) {
					stock.put("noOfShares", prevShares - noOfShares);
				} else {
					stock.put("noOfShares", prevShares + noOfShares);
				}
			}
		}

		try {
			FileWriter writer = new FileWriter("data/stockAccount.json"); // writting to output file
			JSONObject result = new JSONObject();
			result.put("stocks", stocksData);
			writer.write(result.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads the json file and stores the content in jsonarray stockdata
	 */
	private void readStock() {
		FileReader reader;
		try {
			reader = new FileReader("data/stockAccount.json");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			stocksData = (JSONArray) obj.get("stocks");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private double valueof(CompanyShares companyShare) {
		readStock();
		Iterator<JSONObject> iterator = stocksData.iterator();
		double sharePrice = 0.0;
		while (iterator.hasNext()) {
			JSONObject stock = iterator.next();
			if (stock.get("stockSymbol").equals(companyShare.getStockSymbol())) {
				sharePrice = (double) stock.get("sharePrice");
			}
		}

		return sharePrice * companyShare.getNoOfShares();
	}

	@Override
	public double valueOf() {
		double value = 0;
		for (CompanyShares companyShare : companyShares) {
			value += valueof(companyShare);
		}
		return value;
	}

	/**
	 * Buying of stock is implemented here
	 */
	@Override
	public void buy(int amount, String symbol) {
		readStock();
		Iterator<JSONObject> itr = stocksData.iterator();

		long noOfShares = 0;
		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				noOfShares = (long) stock.get("noOfShares");
			}
		}
		if (amount > noOfShares) {
			System.out.println("Insufficient Shares");
		} else {
			CompanyShares newCompanyShare = null;
			for (CompanyShares companyShare : companyShares) {
				if (companyShare.getStockSymbol().equals(symbol)) {
					newCompanyShare = companyShare;
					companyShares.remove(companyShare);
					break;
				}
			}
			if (newCompanyShare == null) {
				newCompanyShare = new CompanyShares(symbol);
			}
			update(symbol, amount, newCompanyShare, Transaction.BUY);

		}
	}

	/**
	 * Selling of stock is implemented here
	 */
	@Override
	public void sell(int amount, String symbol) {
		readStock();
		long noOfShares = 0;

		Iterator<JSONObject> itr = stocksData.iterator();

		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				noOfShares = (long) stock.get("noOfShares");
			}
		}

		if (noOfShares == 0 || amount > noOfShares) {
			System.out.println("Insufficient Shares");
		} else {
			CompanyShares selectedShare = null;
			for (CompanyShares companyShare : companyShares) {
				if (companyShare.getStockSymbol().equals(symbol)) {
					selectedShare = companyShare;
					companyShares.remove(companyShare);
					break;
				}
			}
			if (selectedShare != null) {
				update(symbol, amount, selectedShare, Transaction.SELL);
			}
		}

	}

	/**
	 * Saves the detail of stock after buying or selling including transaction time.
	 */
	@Override
	public void save(String filename) {
		JSONArray compShares = new JSONArray();
		for (CompanyShares companyShare : companyShares) {
			String stockSymbol = companyShare.getStockSymbol();
			long numberOfShares = companyShare.getNoOfShares();
			JSONArray transactions = new JSONArray();
			for (Transaction transaction : companyShare.getList()) {
				JSONObject transactionObject = new JSONObject();
				transactionObject.put("DateTime", transaction.getDateTime().toString());
				transactionObject.put("noOfShares", transaction.getNoOfShares());
				transactionObject.put("State", transaction.getState());
				transactions.add(transactionObject);
			}
			JSONObject object = new JSONObject();
			object.put("stockSymbol", stockSymbol);
			object.put("noOfShares", numberOfShares);
			object.put("transactions", transactions);
			compShares.add(object);
		}

		JSONObject object2 = new JSONObject();
		object2.put("companyShares", compShares);

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(object2.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Prints the stock report
	 */
	@Override
	public void printReport() {
		System.out.println("Stock Report");
		System.out.println("Shares that you currently hold are\n");
		for (CompanyShares companyShare : companyShares) {
			System.out.println("Share Symbol : " + companyShare.getStockSymbol());
			System.out.println("Number of Holding Shares : " + companyShare.getNoOfShares());
			System.out.println("Value of each share : " + valueof(companyShare) / companyShare.getNoOfShares());
			System.out.println("Total Share Value : " + valueof(companyShare));
			System.out.println();
		}
		System.out.println("Total Value : " + valueOf());
	}

}
