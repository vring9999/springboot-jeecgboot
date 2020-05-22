package com.hrkj.scalp.util.gsonadapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


public class GsonUtil {
	public static Gson gson = new GsonBuilder().create();

	/**
	 * Gson序列化对象排除属性 调用方法： String[] keys = { "id" }; Gson gson = new GsonBuilder().setExclusionStrategies(new
	 * JsonKit(keys)).create();
	 */
	static class MyExclusionStrategy implements ExclusionStrategy {
		String[] keys;

		public MyExclusionStrategy(String[] keys) {
			this.keys = keys;
		}

		@Override
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes arg0) {
			for (String key : keys) {
				if (key.equals(arg0.getName())) {
					return true;
				}
			}
			return false;
		}

	}

	/**
	 * json字符串转换为业务对象
	 * 
	 * @param jsonStr
	 * @param classes
	 * @return
	 */
	public static <T> T stringToBo(String jsonStr, Class<T> classes) {
		return gson.fromJson(jsonStr, classes);
	}
	
	/** 
	 * @Title: stringToBo 
	 * @Description: json字符串转换为业务对象(泛型)
	 * @param jsonStr
	 * @param type
	 * @param excludeKeys
	 * @return
	 */
	public static  <T> T stringToBo(String jsonStr, Type type, String[] excludeKeys) {
		GsonBuilder gsonBuilder = getBuilder();
		if(excludeKeys != null && excludeKeys.length > 0){
			gsonBuilder.setExclusionStrategies(new MyExclusionStrategy(excludeKeys));
		}
		Gson gson = gsonBuilder.create();
		return gson.fromJson(jsonStr, type);
	}
	
	public static List<String> stringToArray(String jsonArryStr) {
		return gson.fromJson(jsonArryStr, ArrayList.class);
	}

	/**
	 * json字符串转换为Map
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> stringToMap(String jsonStr) {
		return gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
		}.getType());
	}

	public static String boToString(Object bo) {
		return gson.toJson(bo);
	}

	public static <T> List<T> stringToBoArray(String jsonStr, Class<T> clazz) {
		List<T> lst = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
		for (final JsonElement elem : array) {
			lst.add(gson.fromJson(elem, clazz));
		}
		return lst;

	}

	public static String boToString(Object bo, String[] excludeKeys) {
		Gson gson = new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy(excludeKeys)).create();
		return gson.toJson(bo);
	}

	private static GsonBuilder getBuilder(){
		GsonBuilder gsonBuilder = new GsonBuilder()
			.registerTypeAdapter(Date.class, new GsonDateAdapter())
			.registerTypeAdapter(List.class, new GsonListAdapter())
			.registerTypeAdapter(Integer.class, new GsonIntegerAdapter())
			.registerTypeAdapter(Long.class, new GsonLongAdapter());
		return gsonBuilder;
	}
	
}
