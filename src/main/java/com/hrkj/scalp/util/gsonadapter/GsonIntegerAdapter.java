package com.hrkj.scalp.util.gsonadapter;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hrkj.scalp.util.StringUtil;


public class GsonIntegerAdapter implements JsonDeserializer<Integer> {

	@Override
	public Integer deserialize(JsonElement ele, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		String str = ele.getAsString();
		if(!StringUtil.isEmpty(str)){
			return 0;
		}else{
			return ele.getAsInt();
		}
	}
}
