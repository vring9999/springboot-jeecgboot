package com.hrkj.scalp.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class InputInjectFilter {

	/**
	 * 过滤html标签
	 * 
	 * @param jsObject
	 * @return
	 */
	public static JSONObject encodeInputString(JSONObject jsObject) {
		if(jsObject!=null){
			Iterator iterator = jsObject.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				if(jsObject.get(key)!=null && jsObject.get(key) instanceof String){
					jsObject.put(key, InputInjectFilter.encodeInputString((String) jsObject.get(key)));
				}else if(jsObject.get(key)!=null && jsObject.get(key) instanceof JSONArray){
					jsObject.put(key, InputInjectFilter.encodeInputString(jsObject.getJSONArray(key)));
				}else if(jsObject.get(key)!=null && jsObject.get(key) instanceof JSONObject){
					jsObject.put(key, InputInjectFilter.encodeInputString(jsObject.getJSONObject(key)));
				}
				
			}
		}
		return jsObject;
		
	}
	/**
	 * 过滤html标签
	 * 
	 * @param jsObject
	 * @return
	 */
	public static JSONObject decodeInputString(JSONObject jsObject) {
		if(jsObject!=null){
			Iterator iterator = jsObject.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				if(jsObject.get(key)!=null && !StringUtils.isEmpty((String) jsObject.get(key))){
					jsObject.put(key, InputInjectFilter.decodeInputString((String) jsObject.get(key)));
				}else if(jsObject.get(key)!=null && jsObject.get(key) instanceof JSONArray){
					jsObject.put(key, InputInjectFilter.decodeInputString((JSONArray) jsObject.get(key)));
				}else if(jsObject.get(key)!=null && jsObject.get(key) instanceof JSONObject){
					jsObject.put(key, InputInjectFilter.decodeInputString(jsObject.getJSONObject(key)));
				}
				
			}
		}
		
		return jsObject;
		
	}
	
	/**
	 * 过滤html标签
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static JSONArray encodeInputString(JSONArray jsonArray) {
		for(int i=0 ; i<jsonArray.size();i++){
			InputInjectFilter.encodeInputString(jsonArray.getJSONObject(i));
		}
		return jsonArray;
		
	}
	/**
	 * 过滤html标签
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static JSONArray decodeInputString(JSONArray jsonArray) {
		for(int i=0 ; i<jsonArray.size();i++){
			InputInjectFilter.decodeInputString((jsonArray.getJSONObject(i)));
		}
		return jsonArray;}
	
	
	
	
	/**
	 * 过滤html标签
	 * 
	 * @param input
	 * @return
	 */
	public static String encodeInputString(String input) {
		// resut = ESAPI.encoder().canonicalize(input);
			String resut = HtmlUtils.htmlEscape(input);
			return resut;
		
	}
	
	/**
	 * 过滤html标签
	 * 
	 * @param inputList
	 * @return
	 */
	public static List<String> encodeInputStringList(List<String> inputList) {
		// resut = ESAPI.encoder().canonicalize(input);
		List<String> resultList = new ArrayList<String>();
		if(inputList!=null && inputList.size()>0){
			for(String input : inputList){
				String result = HtmlUtils.htmlEscape(input);
				resultList.add(result);
			}
		}
		return resultList;
	}
	
	
	/**
	 * html反转义
	 * 
	 * @param input
	 * @return
	 */
	public static String decodeInputString(String input) {
		String resut = HtmlUtils.htmlUnescape(input);
		return resut;
	}
	
}
