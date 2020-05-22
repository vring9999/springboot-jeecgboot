package com.hrkj.scalp.util.gsonadapter;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hrkj.scalp.util.DateUtil;
import com.hrkj.scalp.util.StringUtil;


public class GsonDateAdapter implements JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement ele, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		String date = ele.getAsString();
		Date d;
		try {
			if(!StringUtil.isEmpty(date)){
				if (date.indexOf(":")!=-1) {
					d = DateUtil.parseDateTime(date, "yyyy-MM-dd HH:mm:ss");
				}else {
					long dateLong = ele.getAsLong();
					d = new Date(dateLong);
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			 throw new RuntimeException(e); 
		}
		return d;
	}
}
