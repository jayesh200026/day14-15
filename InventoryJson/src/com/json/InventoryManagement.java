package com.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

public class InventoryManagement {
	static Map<String, Double> map = new HashMap<>();

	public static void inventoryManager() {
		List<JSONObject> list = inventoryFactory();

		for (JSONObject obj : list) {
			String type = (String) obj.get("type");
			String name = (String) obj.get("name");
			double weight = (double) obj.get("weight");
			double pricePerWeight = (double) obj.get("pricePerWeight");
			double totalPrice = pricePerWeight * weight;
			map.put(name, totalPrice);
		}

		displayJSON();

	}

	private static void displayJSON() {
		JSONArray array = new JSONArray();
		for (String s : map.keySet()) {
			// object1 = new JSONObject();
			JSONObject object1 = new JSONObject();
			object1.put("name", s);
			object1.put("Totalprice", map.get(s));
			array.add(object1);
		}
		// System.out.println(array);
		JSONObject mainObject = new JSONObject();
		mainObject.put("Result", array);
		System.out.println(mainObject.toJSONString());

	}

	private static List<JSONObject> inventoryFactory() {

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONParser parser = new JSONParser();
		try {
			Reader reader = new FileReader(
					"/Users/jayeshkumar/learning_path/BATCH51/oop/InventoryJson/data/inventory.json");
			JSONObject inventory = (JSONObject) parser.parse(reader);
			JSONArray array = (JSONArray) inventory.get("inventory");
			Iterator<JSONObject> iterator = array.iterator();
			while (iterator.hasNext()) {
				JSONObject object2 = iterator.next();
				list.add(object2);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static void main(String args[]) {
		InventoryManagement.inventoryManager();
	}

}
