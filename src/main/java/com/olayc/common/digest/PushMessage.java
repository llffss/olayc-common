package com.olayc.common.digest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author GW00167781
 * @Date 2020/4/1 17:50
 **/
@Component
public class PushMessage {


    @Autowired
    public DingAdapter dingAdapter;

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

        /*text \ link \markdown  */
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
