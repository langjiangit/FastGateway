/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.zs.gateway.chains;

import com.zs.gateway.bean.vo.RequestVO;
import com.zs.gateway.support.IPGuavaTokenBucket;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/6/11 16:40
 */
@Log4j2
public class InterfaceFlowLimitHandler extends Handler {
	
	@Resource
	private IPGuavaTokenBucket tokenBucket;
	
	@Override
	public boolean execute(RequestVO requestVO) {
		log.info("step 1 : interface flow limitting...");
		String clientIP = requestVO.getClientIP();
		if (tokenBucket.allow()) {
			log.info("clientIp {} success in flow control", clientIP);
			return false;
		} else {
			// 可以快速失败，做服务降级处理，或者消费mq里的消息也可以
		}
		requestVO.setLimit(true);
		log.info("clientIp {} fail in flow control", clientIP);
		return true;
	}
	
}
