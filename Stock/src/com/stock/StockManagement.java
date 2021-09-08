package com.stock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StockManagement {
	public static Scanner sc = new Scanner(System.in);
	public static JSONArray stockList = new JSONArray();
	
	
	public static void main(String args[]) {
		System.out.println("Welcome to stock management");
		final int EXIT=3;
		int choice=0;
		while(choice!=EXIT) {
			System.out.println("Enter your choice\n1 : Add stock\n2 : Get stock report\n"+EXIT+" : exit");
			choice=sc.nextInt();
			switch(choice) {
			case 1:
				addStock();
				break;
			case 2:printReport();
				break;
			default:break;
			}
		}
	}


	private static void printReport() {
		
		System.out.println("<- Print stock details ->");
		JSONParser jsonParser = new JSONParser();
		try {
			Reader reader = new FileReader("/Users/jayeshkumar/learning_path/BATCH51/oop/Stock/data/stock.json");
			JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
			
			for(int i=0;i<jsonArray.size();i++) {
				System.out.println("###### Stock "+(i+1)+"######");
				JSONObject obj = (JSONObject) jsonArray.get(i);
				String name = (String) obj.get("name");
				long noOfShares = (long) obj.get("no_of_shares");
				Double price = (Double) obj.get("price");
				System.out.println("Stock Name : " +name);
				System.out.println("Number of Shares : " +noOfShares);
				System.out.println("Stock price : " +price);
				System.out.println("Value of stocks : "+ (noOfShares*price));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private static void addStock() {
		System.out.println("<- Add new stock detail ->");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Stock Name:");
		String stockName = sc.nextLine();
		System.out.println("Enter number of shares:");
		int noOfShares = sc.nextInt();
		System.out.println("Enter share price:");
		double sharePrice = sc.nextDouble();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", stockName); 
		jsonObject.put("no_of_shares", noOfShares);
		jsonObject.put("price", sharePrice);
		stockList.add(jsonObject); 
		try {
		FileWriter file = new FileWriter("/Users/jayeshkumar/learning_path/BATCH51/oop/Stock/data/stock.json");
		file.write(stockList.toJSONString());
		file.close();
		} catch (IOException e) {
	
		e.printStackTrace();
		}
		System.out.println("Added: "+jsonObject);
		
	}

}
