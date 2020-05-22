package com.hrkj.scalp.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/** 
 * @ClassName: SendMsgUtil 
 * @Description: 发送短信工具类
 * @author: caizhiqiang
 * @date: 2019年2月12日 上午11:26:42  
 */
@Component
@Slf4j
public class SendMsgUtil {

	@Autowired
	private static CacheInfo cacheInfo;

	/**
	 * @Title:SendMsgUtil
	 * @Description:项目初始化时注入cacheUtil
	 * @param cacheInfo
	 */
	@Autowired
	public SendMsgUtil(CacheInfo cacheInfo){
		SendMsgUtil.cacheInfo = cacheInfo;
	}
	
	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	public String ACCESS_KEY_ID;
	public String ACCESS_KEY_SECRET;
	public String SIGN_NAME;

	/**
	 * SendMsgUtil
	 * @Description:构造函数初始化
	 */
	public SendMsgUtil() throws Exception {
	    log.info("SendMsgUtil----init");
        this.ACCESS_KEY_ID = cacheInfo.getCommonToKey("aliyun.properties+sms.access.id");
        this.ACCESS_KEY_SECRET = cacheInfo.getCommonToKey("aliyun.properties+sms.access.secret");
        this.SIGN_NAME = cacheInfo.getCommonToKey("aliyun.properties+sms.sign.name.comp");
	}
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";


    public JSONObject sendSms(Map<String, Object> params) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        String phoneNum = StringUtil.doNullStr(params.get("phoneNum"));
        String templateCode = StringUtil.doNullStr(params.get("templateCode"));
        String templateParam = StringUtil.doNullStr(params.get("templateParam"));
        
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGN_NAME);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        JSONObject restJson = new JSONObject();
        try{
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            restJson.put("code", sendSmsResponse.getCode());
            restJson.put("message", sendSmsResponse.getMessage());
            if(!"OK".equals(sendSmsResponse.getCode())) {
                System.out.println("短信发送失败Message："+sendSmsResponse.getMessage());
                System.out.println("短信发送失败BizId："+sendSmsResponse.getBizId());
                System.out.println("短信发送失败Code："+sendSmsResponse.getCode());
                System.out.println("短信发送失败RequestId："+sendSmsResponse.getRequestId());
                System.out.println("短信发送失败phoneNum："+phoneNum);
                System.out.println("短信发送失败，模板："+templateCode);
            }
        }catch (Exception e){
                   log.error(String.valueOf(e));
        }
        return restJson;

    }

    public QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
	
    
	public static boolean checkPhoneNum(String phoneNum){
		if(phoneNum == null || phoneNum.equals("")){
			return false;
		}
		if(!Pattern.matches("^1[3|5|8|7|9]\\d{9}$", phoneNum)){
			return false;
		}
		return true;
	}
}
