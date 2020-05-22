package com.hrkj.scalp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author vring
 * @ClassName HttpUtil.java
 * @Description
 * @createTime 2019/12/5 11:23
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    private HttpSession session;

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpServletRequest request) {
        this.session = request.getSession(true);
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    //获取当前登录操作人的账号
    public  String getOperaterUseName() {
        return (String)this.getSession().getAttribute("user");
    }
}
