package com.hrkj.scalp.util;

import org.springframework.stereotype.Component;

/**
 * @ClassName: ErrorCodeContents
 * @Description: 错误编码静态类
 * @author: lenovo
 * @date: 2018-10-4 上午11:36:22
 */
@Component
public class ErrorCodeContents {
    /**
     * @fieldName: SUCCESS_CODE
     * @fieldType: String
     * @Description: 操作成功
     */
    public static final String SUCCESS_CODE = "10000";
    /**
     * @fieldName: FAILUE_CODE
     * @fieldType: String
     * @Description: 操作失败
     */
    public static final String FAILUE_CODE = "00000";
    /**
     * @fieldName: USER_IS_EXIST
     * @fieldType: String
     * @Description: 该用户已存在
     */
    public static final String USER_IS_EXIST = "10001";
    /**
     * @fieldName: CODE_ERROR
     * @fieldType: String
     * @Description: 验证码验证错误
     */
    public static final String CODE_ERROR = "10002";
    /**
     * @fieldName: PHPNE_NUM_ERROR
     * @fieldType: String
     * @Description: 电话号码错误（格式）
     */
    public static final String PHONE_NUM_ERROR = "10003";
    /**
     * @fieldName: SEND_CODE_FAIL
     * @fieldType: String
     * @Description: 验证码发送失败
     */
    public static final String SEND_CODE_FAIL = "10004";
    /**
     * @fieldName: UPLOAD_ADDRESS_LIST_FALI
     * @fieldType: String
     * @Description: 上传通讯录失败
     */
    public static final String UPLOAD_ADDRESS_LIST_FALI = "10005";
    /**
     * @fieldName: CHECK_PWD_FAIL
     * @fieldType: String
     * @Description: 密码校验失败
     */
    public static final String CHECK_PWD_FAIL = "10006";
    /**
     * @fieldName: USER_IS_NOT_LOGIN
     * @fieldType: String
     * @Description: 用户未登陆
     */
    public static final String USER_IS_NOT_LOGIN = "10007";
    /**
     * @fieldName: USER_IS_LOGGED_IN_ELSEWHERE
     * @fieldType: String
     * @Description: 用户已在其他地方登录
     */
    public static final String USER_IS_LOGGED_IN_ELSEWHERE = "10008";
    /**
     * @fieldName: USER_IS_NOT_FOUND
     * @fieldType: String
     * @Description: 用户不存在
     */
    public static final String USER_IS_NOT_FOUND = "10009";
    /**
     * @fieldName: USER_CODE_IS_NULL
     * @fieldType: String
     * @Description: 验证码为空
     */
    public static final String USER_CODE_IS_NULL = "10010";
    /**
     * 美币余额不足
     */
    public static final String USER_BANLANCE_NOENOUGH = "10011";
	/**
	 * 该商户未绑定该app
	 */
	public static final String COMMERCIAL_NO_APP="10021";

	/**
	 * 该App不存在
	 */
	public static final String APP_NONENTITY="10022";
    /**
     * 支付签名错误
     */
    public static final String SIGN_ERROR = "-1001";
    /**
     * 权限不足
     */
    public static final String PERMISSION_DENIED = "-1002";

    /**
     * @fieldName: LOGIN_TOKEN_TIMEOUT
     * @fieldType: String
     * @Description: 登录TOKEN已过期
     */
    public static final String LOGIN_TOKEN_TIMEOUT = "-1003";


    /**
     * @fieldName: CHECK_LOGINSIGN_FAILUE
     * @fieldType: String
     * @Description: 登录签名校验失败
     */
    public static final String CHECK_LOGINSIGN_FAILUE = "-1004";

    /**
     * @fieldName: FAILUE_ACCOUNT_BALANCE
     * @fieldType: String
     * @Description: 账户余额不足
     */
    public static final String FAILUE_ACCOUNT_BALANCE = "-1005";
    /**
     * 订单号重复
     */
    public static final String REPEAT_ORDERINFO = "-1006";

	/**
	 * @fieldName: NOT_FOUND_ORDERINFO
	 * @fieldType: String
	 * @Description: 未找到订单信息
	 */
	public static final String NOT_FOUND_ORDERINFO = "1006";

    /**
     * @fieldName: NO_FOUND_COMMERCIALNUMBER
     * @fieldType: String
     * @Description: 未携带商户号
     */
    public static final String NO_FOUND_COMMERCIALNUMBER = "1007";

    /**
     * @fieldName: NO_FOUND_TOKEN
     * @fieldType: String
     * @Description: 未携带LOGINTOKEN
     */
    public static final String NO_FOUND_LOGINTOKEN = "1008";

    /**
     * @fieldName: NOT_IN_WHITELIST
     * @fieldType: String
     * @Description: 当前请求IP不在白名单
     */
    public static final String NOT_IN_WHITELIST = "1009";

    /**
     * @fieldName: CHECK_TOKEN_FAILUE
     * @fieldType: String
     * @Description: LOGINTOKEN校验失败
     */
    public static final String CHECK_LOGINTOKEN_FAILUE = "1010";

    /**
     * @fieldName: SC_UNAUTHORIZED
     * @fieldType: String
     * @Description: 访问资源未经授权  请登录认证
     */
    public static final String SC_FORBIDDEN = "4001";

    /**
     * @fieldName: PARAMS_INCOMPLETE
     * @fieldType: String
     * @Description: 参数不完整
     */
    public static final String PARAMS_INCOMPLETE = "4002";

    /**
     * @fieldName: CONNECT_TIMED_OUT
     * @fieldType: String
     * @Description: 连接超时
     */
    public static final String CONNECT_TIMED_OUT = "4003";


    public static final String TIMEOUT="9001";

    /**
     * MD5校验失败
     */
    public static final String MD5_DEFEATED="9002";

    /**
     * 时间戳为空
     */
    public static final String TIME_IS_NULL="9003";


}
