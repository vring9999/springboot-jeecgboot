package com.hrkj.scalp.util.gsonadapter;


import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
public class JsonDateValueProcessor implements JsonValueProcessor {  
    private String format ="yyyy-MM-dd HH:mm:ss";  
      
    public JsonDateValueProcessor() {  
        super();  
    }  
      
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override  
    public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
    	return process(paramObject);  
    }  
  
    @Override  
    public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
    	return process(paramObject);  
    }  
      
      
	private Object process(Object value) {
		if (value instanceof Date) {
			Date date = (Date) value;
			return dateToStr(date, format);
		}
		return value == null ? "" : value;
	}
	 private String dateToStr(Date date,String format){
		 SimpleDateFormat df = new SimpleDateFormat(format);
		 return df.format(date);
	 }
  
}  
