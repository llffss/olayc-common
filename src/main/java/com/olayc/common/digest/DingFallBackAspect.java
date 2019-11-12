package com.olayc.common.digest;

import com.olayc.common.annotation.DingClient;
import com.olayc.common.utils.JsonUtils;
import com.olayc.common.utils.UUIDUtil;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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

    private final Logger logger = LoggerFactory.getLogger(DingFallBackAspect.class);

    @Pointcut("execution(* *(..)) && @annotation(com.olayc.common.annotation.DingClient)")
    private void fallBackAnnotated() {
    }
//
//    @Value("${ding.fallback.formate: %s %s %s fallback}")
//    private String dingtalkformate;

    /*间隔时间  默认300秒 */
    @Value("${ding.fallback.interval:300000}")
    private long interval;
    /*上一次有效触发时间*/
    private static long lastTime;


    @After("fallBackAnnotated()")
    public void invoke(JoinPoint joinPoint) throws Throwable {
        try {
            String dingClientDesc = "";
            Throwable fallbackError = AbstractFallbackFactory.getFallbackError();
            String errorMsg = fallbackError == null ? "" : fallbackError.getMessage();
            Object[] args = joinPoint.getArgs();
            String argJson = JsonUtils.toJsonString(args);
            String uuid = UUIDUtil.getUuid();
            String content = new StringBuffer("feign hystrix fallback error :")
                    .append(" uuid ").append(uuid)
                    .append(" dingClientDesc ").append(dingClientDesc)
                    .append(" errorMsg ").append(errorMsg)
                    .append(" argJson ").append(argJson).toString();
            logger.error(content);
            long l = System.currentTimeMillis();
            //判断大于5分钟
            if(l-lastTime > interval){
                lastTime=l;
                DingModel dingmodel =new DingModel();
                dingmodel.setMsgtype("text");
                DingModel.Text text =new DingModel.Text();
                text.setContent(content);
                dingmodel.setText(text);
                dingAdapter.sendMessage(dingmodel);
            }
        }catch (Exception e){
            logger.error("record hystrix fallback inner error" + e.getMessage(),e);
        }finally {
            AbstractFallbackFactory.removeFallbackError();
        }
    }

    /**
     * 获取当前请求链接
     *
     * @return
     */
//    private String getCurrentUrl() {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if(requestAttributes!=null){
//            HttpServletRequest request = requestAttributes.getRequest();
//            return request.getRequestURI();
//        }else{
//            return "sys initiative request   /";
//        }
//    }

    @Autowired
    private DingAdapter dingAdapter;

    @FeignClient(value = "ding", url = "${ding.fallback.domain:https://oapi.dingtalk.com}")
    public interface DingAdapter {

        /**
         * @Description 钉钉
         * @author suxxin
         * @date 2019/4/23 0023
         * @param
         * @return java.lang.String
         */
        @PostMapping("${ding.fallback.sendurl:/send}")
        String sendMessage(DingModel dingModel);

    }

    @Data
    static public class DingModel {

        /*t  ext \ link \markdown  */
        private String msgtype;
        private Text text;
        private At at;
        private Link link;
        private Markdown markdown;

        @Data
        static public class Text{
            private String content;

            public static String getStringDate() {
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                return dateString;
            }

            public void setContent(String content) {
                this.content = getStringDate()+"  "+content;
            }
        }
        @Data
        static public class At{
            private List mobiles;
        }
        @Data
        static public class Link {
            private String messageUrl;
            private String picUrl;
            private String title;
        }
        @Data
        static public class Markdown {
            private String title;
            private String text;
        }

    }
}
