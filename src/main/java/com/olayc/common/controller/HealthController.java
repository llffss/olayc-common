package com.olayc.common.controller;

import com.olayc.common.constant.HealthConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianbiao11
 * @date 2018-08-08
 */
@RestController
public class HealthController {
    private final static String HEALTH_OK = "ok";

    /**
     * K8S心跳检查
     *
     * @return
     */
    @GetMapping(HealthConstants.HEALTH_URL)
    public String health() {
        return HealthConstants.HEALTH_OK_RESULT;
    }


}
