package com.olayc.common.digest;

import com.olayc.common.annotation.DingClient;
import com.olayc.common.utils.DateUtil;
import com.olayc.common.utils.JsonUtils;
import com.olayc.common.utils.UUIDUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * 使用钉钉----服务降级通知 切面
 *
 * @author liuyan
 * @date 2018-09-12
 */
@Aspect
@RefreshScope
@ConditionalOnProperty(value = "ding.fallback.enabled", havingValue = "true")
@Component
public class DingFallBackAspect {

    @Autowired
    private PushMessage pushMessage;

    private final Logger logger = LoggerFactory.getLogger(DingFallBackAspect.class);

    @Pointcut("execution(* *(..)) && @annotation(com.olayc.common.annotation.DingClient)")
    private void fallBackAnnotated() {
    }


    private long interval = 60000;
    /**上一次有效触发时间*/
    private static long lastTime = 0;
    private static long errorIndex = 0;


    @After("fallBackAnnotated()&&@annotation(dingClient)")
    public void invoke(JoinPoint joinPoint,DingClient dingClient) throws Throwable {
        Throwable fallbackError = AbstractFallbackFactory.getFallbackError();
        if(fallbackError == null){
            return;
        }
        try {
            String dingClientDesc = dingClient.desc();
            String errorMsg = fallbackError == null ? "" : fallbackError.getClass() + ":" + fallbackError.getMessage();
            Object[] args = joinPoint.getArgs();
            String argJson = JsonUtils.toJsonString(args);
            String uuid = UUIDUtil.getUuid();
            String content = new StringBuffer("feign hystrix fallback error :")
                    .append(" ,uuid :").append(uuid)
                    .append(" ,接口信息 :").append(dingClientDesc)
                    .append(" ,异常信息 :").append(errorMsg)
                    .append(" ,参数信息 :").append(argJson).toString();
            errorIndex ++;
            logger.error(content + ",异常序号 :" + errorIndex);
            long l = System.currentTimeMillis();
            //判断大于5分钟
            if(l-lastTime > interval){
                long _lastTime = lastTime;
                long _errorIndex = errorIndex;
                lastTime=l;
                errorIndex = 0;
                PushMessage.DingModel dingmodel =new PushMessage.DingModel();
                dingmodel.setMsgtype("text");
                PushMessage.DingModel.Text text =new PushMessage.DingModel.Text();
                text.setContent("聚合平台 hystrix 熔断异常," + content + ",上次报警时间:" + DateUtil.formatDate(_lastTime) + ",累计异常数:" + _errorIndex);
                dingmodel.setText(text);
                pushMessage.dingAdapter.sendMessage(dingmodel);
            }
        }catch (Exception e){
            logger.error("record hystrix fallback inner error" + e.getMessage(),e);
        }finally {
            AbstractFallbackFactory.removeFallbackError();
        }
    }



}
