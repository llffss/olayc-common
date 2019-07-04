package com.olayc.common.controller;

import com.olayc.common.constant.CommonResultEnum;
import com.olayc.common.exception.BaseException;
import com.olayc.common.utils.JsonUtils;
import com.olayc.common.vo.EmployeeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * 修改并发问题 by stone
 *
 * @author yangshuo
 * @date 2018-09-29
 * @since 1.0
 */
public class WebController extends BaseController {

    private static final String USER_DATA_SESSION_KEY = "userdata";

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    public EmployeeInfo getEmployeeInfo() {
        Object userData = null;
        HttpSession session = getSession();
        if (session != null) {
            userData = session.getAttribute(USER_DATA_SESSION_KEY);
        }
        if (userData != null) {
            try {
                return JsonUtils.parseObject(String.valueOf(userData), EmployeeInfo.class);
            } catch (Exception ex) {
                LOGGER.warn("login data parse error:{}", userData, ex);
            }
        }
        throw new BaseException(CommonResultEnum.NOT_LOGIN_RESULT_CODE.getCode());
    }

}
