package com.hrkj.scalp.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @ClassName: StringUtil
 * @Description: 字符串处理类
 * @date 2015-12-24 下午2:15:03
 */
@Component
@Slf4j
public class StringUtil {


    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private static char[] forty = new char[]{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'l', 'k', 'j', 'h', 'g',
            'f', 'd', 's', 'a', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
			'L', 'K', 'J', 'H', 'G', 'F','D', 'S', 'A', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};


    /**
     * @param @param string
     * @return boolean
     * @throws
     * @Title: isEmpty
     * @Description: 判断字符串是否为空
     */
    public static boolean isEmpty(String string) {
        boolean result = false;
        if (string == null || "".equals(string.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 验证Object是否为空,object instanceof String
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        boolean result = false;
        if (object == null || "".equals(object.toString().trim())) {
            result = true;
        }
        return result;
    }


    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * @return String 返回类型
     * @throws
     * @Title: getUuid
     * @Description: 获取UUID 带-标识
     */
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str;
    }

    /**
     * @return
     * @Title: getUUID
     * @Description: 获取UUID 去掉-标识
     */
    public static String getUUID() {
        String str = getUuid();
        str = str.replace("-", "");
        return str;
    }

    /**
     * @return String 短UUID
     * @Title: getShortUuid
     * @Description: 获取短UUID
     */
    public static String getShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = getUUID();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 获取随机数
     *
     * @param len 随机数长度
     * @return 返回len长度的随机数
     */
    public static StringBuffer getRandomCode(int len) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer;
    }


    /**
     * @param @param  val
     * @param @param  length
     * @param @return 设定文件
     * @return boolean 返回类型
     * @Title: isCheckFiledLen
     * @Description: 校验字段长度
     */
    public static boolean isCheckFiledLen(String val, int length) {
        boolean result = false;
        int valLen = val.length();
        if (valLen > length) {
            result = true;
        }
        return result;
    }

    /**
     * 将字符串为"null"或空对象转化为字符串""
     *
     * @param obj
     */
    public static String doNullStr(Object obj) {
        String str = "";
        if (obj != null) {
            str = String.valueOf(obj);
            if (str.equals("null")) {
                str = "";
            }
        }
        return str.trim();
    }

    public static void main(String[] args) {
        String str = "";
        System.out.println(str.trim());
    }


    /**
     * 格式化查询参数
     *
     * @param filter
     * @return
     */
    public static Map<String, Object> formatParam(String filter) {
        filter = InputInjectFilter.decodeInputString(filter);// HTML 反转义
        Map<String, Object> map = new HashMap<String, Object>();
        if (filter != null) {
            Pattern p = Pattern.compile("(\\w*)=([^&]*)");
            Matcher m = p.matcher(filter);
            while (m.find()) {
                if (!isEmpty(m.group(1)) && !isEmpty(m.group(2))) {
                    map.put(m.group(1), m.group(2));
                }
            }
        }
        return map;
    }

    public static Map<String, String> formatParamString(String filter) {
        filter = InputInjectFilter.decodeInputString(filter);// HTML 反转义
        Map<String, String> map = new HashMap<String, String>();
        if (filter != null) {
            Pattern p = Pattern.compile("(\\w*)=([^&]*)");
            Matcher m = p.matcher(filter);
            while (m.find()) {
                if (!isEmpty(m.group(1)) && !isEmpty(m.group(2))) {
                    map.put(m.group(1), m.group(2));
                }
            }
        }
        return map;
    }

    /**
     * 2016-11-30
     *
     * @param @param  str
     * @param @return 参数说明
     * @return boolean 返回类型
     * @Title: isNumeric
     * @Description: 判断字符串是否是数字组成
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    /**
     * @param str
     * @return
     * @Title: underlineToCamel
     * @Description: 下划线格式字符转为驼峰式字符规则
     */
    public static String underlineToCamel(String str) {
        if (isEmpty(str)) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if ('_' == c) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @param str
     * @return
     * @Title: camelToUnderline
     * @Description: 驼峰式字符转为下划线格式字符规则
     */
    public static String camelToUnderline(String str) {
        if (isEmpty(str)) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @param str
     * @return
     * @Title: camel2under
     * @Description: 驼峰式字符转为下划线格式字符规则
     */
    public static String camel2under(String str) {
        String separator = "_";
        str = str.replaceAll("([a-z])([A-Z])", "$1" + separator + "$2").toLowerCase();
        return str;
    }

    /**
     * @param @param  str
     * @param @param  code
     * @param @throws UnsupportedEncodingException 参数说明
     * @return String 返回类型
     * @Title: encode
     * @Description: 根据指定编码对字符串进行转码
     */
    public static String encode(String str, String code) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return java.net.URLEncoder.encode(str, code);
    }

    /**
     * @param @param  str
     * @param @return
     * @param @throws UnsupportedEncodingException 参数说明
     * @return String 返回类型
     * @Title: encode
     * @Description: 对字符转进行UTF-8转码
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return encode(str, "UTF-8");
    }

    /**
     * @param @param  str
     * @param @param  code
     * @param @return
     * @param @throws UnsupportedEncodingException 参数说明
     * @return String 返回类型
     * @Title: decode
     * @Description: 根据指定编码对字符串进行解码
     */
    public static String decode(String str, String code) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
//		if (str.contains("+"))
//			str = str.replace("+", "%2B");
        return java.net.URLDecoder.decode(str, code);
    }

    /**
     * @param @param  str
     * @param @return
     * @param @throws UnsupportedEncodingException 参数说明
     * @return String 返回类型
     * @Title: decode
     * @Description: 对字符转进行UTF-8解码
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return decode(str, "UTF-8");
    }

    /**
     * 将磁盘的单位为byte转为便于阅读的单位
     * 1kb = 1024(b)
     * 1M = 1,048,576(b)
     * 1G = 1,073,741,824(b)
     * 1Tb = 1,099,511,627,776(b)
     * 1Pb = 1125899906842624(b)
     *
     * @param size
     * @return
     */
    public static String changeFileSizeToRead(BigDecimal size) {
        String readSize = "";
        if (size.longValue() < 1024) {
            readSize = size + " b";
        } else if (size.longValue() >= 1024 && size.longValue() < 1048576) {
            readSize = size.divide(new BigDecimal(1024)).setScale(1, RoundingMode.HALF_UP) + " Kb";
        } else if (size.longValue() >= 1048576 && size.longValue() < 1073741824) {
            readSize = size.divide(new BigDecimal(1024 * 1024)).setScale(1, RoundingMode.HALF_UP) + " Mb";
        } else if (size.longValue() >= 1073741824 && size.longValue() < 1099511627776l) {
            readSize = size.divide(new BigDecimal(1024 * 1024 * 1024)).setScale(1, RoundingMode.HALF_UP) + " Gb";
        } else if (size.longValue() >= 1099511627776l && size.longValue() < 1125899906842624l) {
            readSize = size.divide(new BigDecimal(1024 * 1024 * 1024 * 1024l)).setScale(1, RoundingMode.HALF_UP) + " Tb";
        }
        return readSize;
    }

    /**
     * 转义"_"
     *
     * @param params
     * @param filterKeys
     */
    public static void filterFormater(Map<String, Object> params, String[] filterKeys) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (ArrayUtils.contains(filterKeys, entry.getKey())) {
                String value = (String) entry.getValue();
                if (!StringUtil.isEmpty(value)) {
                    value = value.replaceAll("_", "\\\\_");
                }
                params.put(entry.getKey(), value);
            }
        }
    }

    /**
     * 转义"_"
     *
     * @param obj
     * @param filterKeys
     */
    public static Object filterFormater(Object obj, String[] filterKeys) throws Exception {
        Map<String, Object> params = objectToMap(obj);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (ArrayUtils.contains(filterKeys, entry.getKey())) {
                String value = (String) entry.getValue();
                if (!StringUtil.isEmpty(value)) {
                    value = value.replaceAll("_", "\\\\_");
                }
                params.put(entry.getKey(), value);
            }
        }
        obj = mapToObject(params, obj.getClass());
        return obj;
    }

    /**
     * Object转Map
     *
     * @param obj
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //法一：使用reflect进行转换
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;

        //法二：使用Introspector进行转换
		/*
		if(obj == null) {
			return null; 
		}
        Map<String, Object> map = new HashMap<String, Object>();   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
        return map;  
        */
    }

    /**
     * Map转Object
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        //法一：使用reflect进行转换
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;

        //法二：使用Introspector进行转换 
		/*
		if(map == null) {
			return null;    
		}
        Object obj = beanClass.newInstance();  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
        return obj;
        */
    }


    /**
     * 获取盐值
     *
     * @param length
     * @return
     */
    public static String getSalt(int length) {
        String randomStr = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int random = (int) (Math.round(Math.random() * (62 - 1)) + 1);
            strBuf.append(randomStr.charAt(random));
        }
        return strBuf.toString();
    }

    /**
     * 获取提成金额
     *
     * @param orderMoney 订单金额  分为单位
     * @param radio      提成比例
     * @return
     */
    public static int handMoney(int orderMoney, double radio) { //248900   0.10
        int handMoney;
        double moneyY = (double) orderMoney / 100;  //转换成元为单位
        double moneyH = moneyY * radio;   //计算提成
        BigDecimal bd = new BigDecimal(moneyH);
        moneyH = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  //保留两位小数
        double moneyA = moneyH * 100;   //转换成分为单位
        handMoney = (int) moneyA;
        return handMoney;
    }

    public static String getOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String day = sdf.format(new Date());
        String math = getRandomMath(8);
        String id = day+math;
        return id;
    }

//    public static void main(String[] args) {
//        System.out.println(getOrderId());
//    }


    /**
     * @return
     * @Title: getRandomNum
     * @Description: 获取随机数
     */
    public static String getRandomNum() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        String num = result + "_" + date;
        return num;
    }

    /**
     * @return
     * @Title: getFloat
     * @Description: 获取浮动金额
     */
    public static int getFloat(int money) {
        float Max = 0.5f, Min = -0.5f;
        BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
        String af = db.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00");
        double num = Double.parseDouble(af);//装换为double类型
        double temp = money / 100;
        money = (int) ((num + temp) * 100);
        return money;
    }


    /**
     * 生成指定length的随机字符串（A-Z，a-z，0-9）
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成指定length的随机字符串（A-Z，a-z，0-9）
     * @param length
     * @return
     */
    public static String getRandomMath(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static Map<String,String> request2Map(HttpServletRequest request){
        Map<String, String> map=null;
        try {
            if(request.getParameterMap()!=null&&request.getParameterMap().size()!=0){
                map = req2Map(request);
            }else {
                map = getRequestPostStr(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> req2Map(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> map.put(key, value[0]));
        return map;
    }
    /**
     * json转换成map
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String,String> getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        Map<String, String> map = (Map) JSON.parse(new String(buffer, charEncoding));
        return map;
    }
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readLen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }
        return buffer;
    }
}
