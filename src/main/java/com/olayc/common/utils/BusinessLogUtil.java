package com.olayc.common.utils;

import com.olayc.common.enums.BusinessServiceNamesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务日志记录类
 * @author jianbiao11
 */
public class BusinessLogUtil {

    /**
     * 业务日志
     */
    private static final Logger logger = LoggerFactory.getLogger("business-log");

    private static final String EXPLAIN="BUSINESS_SERVICE-";
    private static final String EXPLAIN2=" ACTION-";
	private static final String EXPLAIN3=" LOGINFO-";

	/**
	 * 业务分钟日志规范日志写入
	 * @param businessServiceNamesEnum
	 * @param action
	 * @param logInfo
	 */
    public static void setLog(BusinessServiceNamesEnum businessServiceNamesEnum,String action ,String logInfo){
        String log=EXPLAIN+businessServiceNamesEnum.getCode()+"["+businessServiceNamesEnum.getMessage()+"]" + EXPLAIN2 + "["+action+"]" + EXPLAIN3+"["+logInfo+"]" ;
        logger.info(log);
    }
}
