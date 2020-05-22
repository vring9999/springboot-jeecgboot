package com.hrkj.scalp.util.gsonadapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


/** 
 * @ClassName: GsonListAdapter 
 * @Description: 避免不规范数组
 * @author: cai_zhiqiang
 * @date: 2018年11月4日 下午3:21:21  
 */
@SuppressWarnings("rawtypes")
public class GsonListAdapter implements JsonDeserializer<List> {

	@SuppressWarnings("unchecked")
	@Override
	public List deserialize(JsonElement ele, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		if(ele.isJsonArray()){
			JsonArray array = ele.getAsJsonArray();
			Type itemType = ((ParameterizedType)type).getActualTypeArguments()[0];
			List list = new ArrayList();
			for(int i = 0,l = array.size();i < l;i++){
				JsonElement element = array.get(i);
				Object item = context.deserialize(element, itemType);
				list.add(item);
			}
			return list;
		}else{
			return Collections.EMPTY_LIST;
		}
	}
}
