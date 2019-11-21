package com.olayc.common.digest;

import com.olayc.common.annotation.DingClient;
import com.olayc.common.utils.DateUtil;
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


    /*间隔时间  默认300秒 */
//    @Value("${ding.fallback.interval:300000}")
    // 1分钟只提示一次报警
    private long interval = 60000;
    /*上一次有效触发时间*/
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
            String errorMsg = fallbackError == null ? "" : fallbackError.getClass() + fallbackError.getMessage();
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
                DingModel dingmodel =new DingModel();
                dingmodel.setMsgtype("text");
                DingModel.Text text =new DingModel.Text();
                text.setContent("聚合平台 hystrix 熔断异常," + content + ",上次报警时间:" + DateUtil.formatDate(_lastTime) + ",累计异常数:" + _errorIndex);
                dingmodel.setText(text);
                dingAdapter.sendMessage(dingmodel);
            }
        }catch (Exception e){
            logger.error("record hystrix fallback inner error" + e.getMessage(),e);
        }finally {
            AbstractFallbackFactory.removeFallbackError();
        }
    }



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
