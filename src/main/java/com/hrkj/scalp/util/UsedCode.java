package com.hrkj.scalp.util;

import com.hrkj.scalp.util.rsa.MD5Util;

public class UsedCode {

    public static final Integer BOOLEAN_TRUE = 1;

    public static final Integer BOOLEAN_FALSE = 0;

    public static final Integer LOG_TYPE_3 = 3;

    public static final String SYSTEM_DEFAULT_QRCODE = "http://qny.hrcx.top//20200409111826242";
    /**
     * 秘钥配置
     */
    public static final Integer SECRET_KEY = 2;
    /**
     * 秘钥显示
     */
    public static final String GET_SECRET="**********";
    /**
     * 管理员登录
     */
    public static final String LOGIN_TYPE_ADMIN = "admin";
    /**
     * 商户登录
     */
    public static final String LOGIN_TYPE_MERCHANT = "merchant";
    /**
     * 码商登录
     */
    public static final String LOGIN_TYPE_USER = "user";
    /**
     * 消息推送--全平台
     */
    public static final String LOGIN_TYPE_FULL = "full";
    /**
     * 余额操作类型  增加
     */
    public static final String BALANCE_ADD = "+";
    /**
     * 余额操作类型  减少
     */
    public static final String BALANCE_REDUCE = "-";
    /**
     * 初始密码
     */
    public static String Initial_PWD = "wpsk123";
    /**
     * 初始盐值密码
     */
    public static String SALT_MD5_PWD = MD5Util.getSaltMD5(Initial_PWD);
    /**
     * 归属用户组 --- 铂金用户组
     */
    public static  final String GREOUP_NAME_PLATINUM = "铂金用户组";


    /**
     * 归属用户组 --- 黄金用户组
     */
    public static  final String GREOUP_NAME_GOLD = "黄金用户组";
    /**
     * 归属用户组 --- 白金用户组
     */
    public static  final String GREOUP_NAME_WHITE = "白金用户组";
    /**
     * 归属用户组 --- 至尊用户组
     */
    public static  final String GREOUP_NAME_SUPER = "至尊用户组";

    /**
     * 归属用户组 --- 普通用户组
     */
    public static  final String GREOUP_NAME_GENERAL = "普通用户组";

    /**
     * @fieldName: ACCOUNT_STATUS_WAIT
     * @fieldType: String
     * @Description: 账目状态--待确认
     */
    public static final int ACCOUNT_STATUS_WAIT = 1;
    /**
     * @fieldName: ACCOUNT_STATUS_CANCEL
     * @fieldType: String
     * @Description: 账目状态--已取消
     */
    public static final int ACCOUNT_STATUS_CANCEL = 2;
    /**
     * @fieldName: ACCOUNT_STATUS_CONFIRM
     * @fieldType: String
     * @Description: 账目状态--已确认
     */
    public static final int ACCOUNT_STATUS_CONFIRM = 3;
    /**
     * @fieldName: PRACTICE_STATUS_OPEN
     * @fieldType: String
     * @Description: 码商开业状态--开业  码商账号正常
     */
    public static final int PRACTICE_STATUS_OPEN = 1;
    /**
     * @fieldName: PRACTICE_STATUS_OPEN
     * @fieldType: String
     * @Description: 码商开业状态--打烊  码商账号异常
     */
    public static final int PRACTICE_STATUS_CLOSE = 0;
    /**
     * @fieldName: PRACTICE_STATUS_OPEN
     * @fieldType: String
     * @Description: 码商开业状态--管理员打烊
     */
    public static final int PRACTICE_STATUS_ADMIN_CLOSE = 2;


    /**
     * 管理员
     */
    public static final int GET_SYSTEM=2;

    /**
     * 码商
     */
    public static final int GET_USER=1;
    /**
     * 商户
     */
    public static final int GET_MERCHANT=0;

    /**
     * 付款确认---未支付
     */
    public static final int ORDER_NO_PAYMENT=0;
    /**
     * 付款确认---已支付
     */
    public static final int ORDER_YES_PAYMENT=1;
    /**
     * 已过期  付款确认---取消支付
     */
    public static final int ORDER_PAST_DUE=2;
    /**
     * 取消
     */
    public static final int ORDER_CANCEL=3;
    /**`
     * 未回调
     */
    public static final int ORDER_NO_BACK=0;
    /**
     * 已回调
     */
    public static final int ORDER_YES_BACK=1;

    /**
     * 出账/提现
     */
    public static final int RECORD_TYPE_OUT=0;
    /**
     * 入账/充值
     */
    public static final int RECORD_TYPE_IN=1;
    /**
     * 订单
     */
    public static final int RECORD_TYPE_ORD =2;
    /**
     * 系统公告
     */
    public static final int RECORD_TYPE_SYSTEM =3;

    /**
     * 订单确认--手动确认
     */
    public static final int ORDER_USER_PASS=0;
    /**
     * 订单确认--管理员确认
     */
    public static final int ORDER_SYS_PASS=1;
    /**
     * 订单确认--未确认
     */
    public static final int ORDER_NOT_PASS=2;

    /**
     * 二维码类型--支付宝
     */
    public static final int QR_TYPE_ALIPAY=1;

    /**
     * 二维码类型--微信
     */
    public static final int QR_TYPE_WECHAT=2;

    /**
     * 二维码状态--启用
     */
    public static final int QR_STATUS_PASS=1;
    /**
     * 二维码状态--关闭
     */
    public static final int QR_STATUS_CLOSE=0;
    /**
     * 二维码状态--待审核
     */
    public static final int QR_STATUS_WAIT = 2;
    /**
     * 二维码状态--驳回
     */
    public static final int QR_STATUS_CANCEL = 3;

    /**
     * 二维码状态--管理员启动
     */
    public static final int QR_ADMIN_IS_PASS=2;
    /**
     * 二维码状态--管理员下架
     */
    public static final int QR_ADMIN_IS_CLOSE=1;

    /**
     * 固定分润
     */
    public static final int ORDER_FIXED=1;

    /**
     * 个人用户组分润
     */
    public static final int ORDER_PROFIT=2;

    /**
     * 码商开店
     */
    public static final int SHOP_OPEN=1;

    /**
     * 可开业
     */
    public static final int MAY_OPEN=1;

    /**
     * 未达成条件
     */
    public static final int NOT_OPEN=0;

    /**
     * 消息发送状态---未发送
     */
    public static final int SEND_STATUS_WAIT = 0;
    /**
     * 消息发送状态---成功
     */
    public static final int SEND_STATUS_SUCCESS = 1;
    /**
     * 消息发送状态---失败
     */
    public static final int SEND_STATUS_FAILED = 2;

    /**
     * 码商关店
     */
    public static final int SHOP_CLOSE=0;

    public static final int SYS_SHOP_CLOSE=2;

    public static final String ORDER_PREFIX = "order";

    /**
     * 扣除保证金  模板
     * @param num
     * @return
     */
    public static String getEnsTxt(int num){
        return "扣款可用库存："+num+"个";
    }

    /**
     * 添加利润   模板
     * @param money
     * @return
     */
    public static String getProTxt(Integer money){
        return "订单利润(添加至抵用金额)："+(Double.parseDouble(money.toString())/100)+"元";
    }

    /**
     * 添加客户支付
     * @param money
     * @return
     */
    public static String getPayTxt(int money){
        return "客户支付金额："+(money/100)+"元";
    }

    /**
     * 添加商户提现 模板
     * @param money
     * @return
     */
    public static String getWithTxt(int money) { return "商户提现操作："+(money/100)+"元"; }

    public static Integer getSUPER(){return 1;}

    public static String getGroupSonName(String parentName){
        String sonName = "" ;
        switch (parentName){
            case GREOUP_NAME_SUPER:
                sonName = GREOUP_NAME_PLATINUM;
                break;
            case GREOUP_NAME_PLATINUM:
                sonName = GREOUP_NAME_GOLD;
                break;
            case GREOUP_NAME_GOLD:
                sonName = GREOUP_NAME_WHITE;
                break;
            case GREOUP_NAME_WHITE:
                sonName = GREOUP_NAME_GENERAL;
                break;
            default:
                break;
        }
        return sonName;
    }

    public static String getGroupLevel(String parentGroupName){
        String level = "" ;
        switch (parentGroupName){
            case GREOUP_NAME_SUPER:
                level = "oneUserId";
                break;
            case GREOUP_NAME_PLATINUM:
                level = "twoUserId";
                break;
            case GREOUP_NAME_GOLD:
                level = "threeUserId";
                break;
            case GREOUP_NAME_WHITE:
                level = "fourUserId";
                break;
            default:
                break;
        }
        return level;
    }


}
