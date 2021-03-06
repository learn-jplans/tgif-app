package com.tgif.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.FoodItem;
import model.FoodMenu;
import model.Order;
import model.Sauce;
import model.Serving;
import model.ServingPrice;
import model.SideDish;

public class FoodMenuDAO {
	public List<FoodMenu> getFoodMenus(JsonObject json) {
		List<FoodMenu> list = new ArrayList<>();

		JsonArray menuItems = json.get("items").getAsJsonArray();
		for(int i = 0; i < menuItems.size(); i++) {
			JsonObject jsonObject = menuItems.get(i).getAsJsonObject();
			String label = jsonObject.get("label").getAsString();
			int id = jsonObject.get("menu_id").getAsInt();
			int qty = jsonObject.get("quantity").getAsInt();

			FoodMenu fm = new FoodMenu();
			fm.setMenuId(id);
			fm.setLabel(label);
			fm.setQty(qty);
			list.add(fm);
		}
		return list;
	}
	
	public List<FoodItem> getFoodMenuItems(JsonObject json) {
		List<FoodItem> list = new ArrayList<>();

		JsonArray menuItems = json.get("items").getAsJsonArray();
		for(int i = 0; i < menuItems.size(); i++) {
			JsonObject jsonObject = menuItems.get(i).getAsJsonObject();
			
			int itemId = jsonObject.get("item_id").getAsInt();
			int menuId = jsonObject.get("menu_id").getAsInt();
			String menuName = jsonObject.get("menu_name").getAsString();
			String image = jsonObject.get("image").getAsString();
			int orderCtr = jsonObject.get("order_ctr").getAsInt();
			
			FoodItem fmi = new FoodItem();
			fmi.setMenuId(menuId);
			fmi.setItemId(itemId);
			fmi.setMenuName(menuName);
			fmi.setImage(image);
			fmi.setOrderCtr(orderCtr);
			list.add(fmi);
		}
		return list;
	}
	
	public List<FoodItem> getAllTimeFavorites(JsonObject json) {
		List<FoodItem> list = new ArrayList<>();
		
		JsonArray menuItems = json.get("items").getAsJsonArray();

		for(int i = 0; i < menuItems.size(); i++) {
			JsonObject jsonObject = menuItems.get(i).getAsJsonObject();
			
			int itemId = jsonObject.get("item_id").getAsInt();
			int menuId = jsonObject.get("menu_id").getAsInt();
			String menuName = jsonObject.get("menu_name").getAsString();
			String image = jsonObject.get("image").getAsString();
			int orderCtr = jsonObject.get("order_ctr").getAsInt();
			
			FoodItem fmi = new FoodItem();
			fmi.setMenuId(menuId);
			fmi.setItemId(itemId);
			fmi.setMenuName(menuName);
			fmi.setImage(image);
			fmi.setOrderCtr(orderCtr);
			list.add(fmi);
		}
		return list;
	}
	
	public FoodItem getOrderDetails(JsonObject json) {
		FoodItem fmi = new FoodItem();

		JsonObject jsonItem = json.get("item").getAsJsonObject();
		
		int itemId = jsonItem.get("id").getAsInt();
		fmi.setItemId(itemId);
		
		JsonArray jsonServings = jsonItem.get("servings").getAsJsonArray();			
		List<Serving> servings = new ArrayList<>();
		for (int i = 0; i < jsonServings.size(); i++) {
			JsonObject jsonObject = jsonServings.get(i).getAsJsonObject();
			String servingName = jsonObject.get("serving_name").getAsString();
			int servingId = jsonObject.get("serving_id").getAsInt();
			Double servingPrice = jsonObject.get("price").getAsDouble();
			
			ServingPrice price = new ServingPrice();
			price.setPrice(servingPrice);
			Serving serving = new Serving();
			serving.setServingId(servingId);
			serving.setServingName(servingName);
			serving.setServingPrice(price);
			
			servings.add(serving);
		}
		
		JsonArray jsonSauce = jsonItem.get("sauces").getAsJsonArray();
		List<Sauce> sauces = new ArrayList<>();
		for (int i = 0; i < jsonSauce.size(); i++) {
			JsonObject jsonObject = jsonSauce.get(i).getAsJsonObject();
			String sauceName = jsonObject.get("sauce_name").getAsString();
			int sauceId = jsonObject.get("sauce_id").getAsInt();
			Sauce sauce = new Sauce();
			sauce.setSauceId(sauceId);
			sauce.setSauceName(sauceName);
			sauces.add(sauce);
		}
		
		JsonArray jsonSideDish = jsonItem.get("side_dishes").getAsJsonArray();
		List<SideDish> sideDishes = new ArrayList<>();
		for (int i = 0; i < jsonSideDish.size(); i++) {
			JsonObject jsonObject = jsonSideDish.get(i).getAsJsonObject();
			String sideDishName = jsonObject.get("side_dish_name").getAsString();
			
			int sdId = jsonObject.get("side_dish_id").getAsInt();
			SideDish sideDish = new SideDish();
			sideDish.setSideDishId(sdId);
			sideDish.setSideDishName(sideDishName);
			sideDishes.add(sideDish);
		}
		fmi.setServings(servings);
		fmi.setSauces(sauces);
		fmi.setSideDishes(sideDishes);
		return fmi;
	}
	
	public List<Order> getMyOrders(JsonObject json) {
		List<Order> orders = new ArrayList<>();
		
		String status = json.get("statuss").getAsString();
		System.out.println("status: "+status);
		JsonArray items = json.get("items").getAsJsonArray();
		for (int i = 0; i < items.size(); i++) {
			JsonObject jsonItem = items.get(i).getAsJsonObject();
			Order order = new Order();
			order.setQty(jsonItem.get("qty").getAsInt());
			order.setStatus(jsonItem.get("status").getAsString());
			order.setId(jsonItem.get("id").getAsInt());
			
			List<Serving> servings = new ArrayList<>();
			if (!jsonItem.get("serving").toString().equalsIgnoreCase("null")) {
				Serving serving = new Serving();
				serving.setServingName(jsonItem.get("serving").getAsString());
				servings.add(serving);
			}
			JsonArray arrSauces = jsonItem.get("sauces").getAsJsonArray();
			List<Sauce> sauces = new ArrayList<>();
			if (arrSauces.size() > 0) {
				for (int j = 0; j < arrSauces.size(); j++) {
					JsonObject jsonSauce = arrSauces.get(j).getAsJsonObject();
					Sauce sauce = new Sauce();
					sauce.setSauceName(jsonSauce.get("sauce_name").getAsString());
					sauces.add(sauce);
				}
			}
			List<SideDish> sideDishes = new ArrayList<>();
			if (!jsonItem.get("side_dish").toString().equalsIgnoreCase("null")) {
				SideDish sideDish = new SideDish();
				sideDish.setSideDishName(jsonItem.get("side_dish").getAsString());
				sideDishes.add(sideDish);
			}
			
			FoodItem foodItem = new FoodItem();
			foodItem.setMenuName(jsonItem.get("item_name").getAsString());
			foodItem.setServings(servings);
			foodItem.setSauces(sauces);
			foodItem.setSideDishes(sideDishes);
			
			order.setFoodItem(foodItem);
			orders.add(order);
			System.out.println("fetch");
			System.out.println("DAO:"+orders.size());
		}
		return orders;
	}	
}

