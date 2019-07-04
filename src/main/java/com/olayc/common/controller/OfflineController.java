package com.olayc.common.controller;


import com.olayc.common.constant.CommonConstants;
import com.olayc.common.msg.BaseResponse;
import com.olayc.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author jianbiao11
 * @date 2018-08-08
 */
@RestController
@Slf4j
public class OfflineController extends BaseController {


	@Value("${spring.application.name:unknown}")
	private String serviceName;

    @Value("${eureka.instance.instance-id:unknown}")
    private String instanceId;

	@Value("${ola.isAllowSetOffline:false}")
	private boolean isAllowSetOffline;

	@Value("${eureka.client.service-url.defaultZone: }")
	private String eurekaUrls;

	/**
	 * 优雅下线
	 * pod preStop webhook触发
	 * @return
	 */
	@GetMapping(CommonConstants.OUT_OF_SERVICE_URL)
	public BaseResponse outOfService() {
		log.info(">>>>>>>OUT_OF_SERVICE--begin....");
		BaseResponse baseResponse=new BaseResponse();
		if(isAllowSetOffline){
			RestTemplate restTemplate =new RestTemplate();
			List<String> eurekaUrlList=StringUtil.splitToList(",",eurekaUrls);
			String result = "no instance available";
			if (eurekaUrlList != null && eurekaUrlList.size() > 0) {
				for (String eurekaUrl : eurekaUrlList) {
					URI productUri = URI.create(eurekaUrl+String.format(CommonConstants.OUT_OF_SERVICE_EUREKA_URL,this.serviceName,this.instanceId));
					HttpEntity<String> entity = new HttpEntity<>(null,null);
					log.info(">>>>>>> call 'OUT_OF_SERVICE' url:"+productUri.toString());
					ResponseEntity<String> resultEntity = restTemplate.exchange(productUri, HttpMethod.PUT, entity, String.class);
					//处理成功 200
					if(HttpStatus.OK.value()==resultEntity.getStatusCode().value()){
						this.getResponse().setStatus(HttpStatus.OK.value());
						log.info(">>>>>>> set 'OUT_OF_SERVICE' ok....");
						baseResponse.setMsg("set out_of_service success");
						return baseResponse;
					}
				}
				log.warn(">>>>>>> set 'OUT_OF_SERVICE' fail....");
				baseResponse.setCode(0);
				baseResponse.setMsg("set out_of_service fail");
				return baseResponse;
			}else{
				//注册中心无效
				this.getResponse().setStatus(0);
				log.warn(">>>>>>> set 'OUT_OF_SERVICE' fail : "+result);
				baseResponse.setCode(0);
				baseResponse.setMsg("set out_of_service fail:"+result);
				return baseResponse;
			}
		}else{
			log.warn(">>>>>>> set 'OUT_OF_SERVICE' fail : 403 Forbidden");
			this.getResponse().setStatus(403);
			baseResponse.setCode(0);
			baseResponse.setMsg("set out_of_service fail: 403 Forbidden");
			return baseResponse;
		}
	}
}
