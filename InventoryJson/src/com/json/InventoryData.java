package com.json;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InventoryData {

	public static void main(String[] args) {
		
		JSONObject object1 = new JSONObject();
		JSONObject object2 = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String,Double> map = new HashMap<>();
		try {
			Reader reader = new FileReader("/Users/jayeshkumar/learning_path/BATCH51/oop/InventoryJson/data/inventory.json");
			
			
			JSONArray array =  new JSONArray();
			
			object1= (JSONObject) parser.parse(reader);
			array =(JSONArray) object1.get("inventory");
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext()) {
				//System.out.println(iterator.next());
				object2=(JSONObject)iterator.next();
				String name=(String) object2.get("name");
				String type=(String) object2.get("type");
				double weight=(double) object2.get("weight");
				Double pricePerWeight = (Double) object2.get("pricePerWeight");
				map.put(name, weight*pricePerWeight);
				
			}
			
			System.out.println(map);
			//System.out.println(object1);
			//System.out.println(array);
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
			
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
		
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//System.out.println("hello");
		
		writeToJson(map);

	}

	private static void writeToJson(Map<String, Double> map) {
		
		
		JSONArray array =  new JSONArray();
		for (String s: map.keySet()) {
			//object1 = new JSONObject();
			JSONObject object1=new JSONObject();
			object1.put("name", s);
			object1.put("Totalprice",map.get(s));
			array.add(object1);
		}
		System.out.println(array);
		JSONObject mainObject =  new JSONObject();
		mainObject.put("Result", array);
		System.out.println(mainObject);
		
		try {
			File file = new File("/Users/jayeshkumar/learning_path/BATCH51/oop/InventoryJson/data/result.json");
			
			if(file.createNewFile()) {
				
			}
			else {
				System.out.println("already file exits");
			}
			
			FileWriter writer = new FileWriter("/Users/jayeshkumar/learning_path/BATCH51/oop/InventoryJson/data/result.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		

}

