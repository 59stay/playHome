package com.jyb.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jyb.lucene.GameIndex;
import com.jyb.lucene.SoftwareIndex;
import com.jyb.service.GameInformationService;
import com.jyb.service.InvalidLinkService;
import com.jyb.service.SoftwareService;
/**
 * 非Controller中注入service工具类
 * @author jyb
 *
 */
@Component
public class BeanUtil {

	@Autowired
	public SoftwareService softwareService;
	
	@Autowired
	public GameInformationService gameInformationService;
	
	@Autowired
	public InvalidLinkService invalidLinkService;
	
	@Autowired
	public GameIndex gameIndex;
	
	@Autowired
	public SoftwareIndex softwareIndex;
	
	public static BeanUtil beanUtil;
	
	@PostConstruct
	public void init() {
		beanUtil = this;
		beanUtil.softwareService = this.softwareService;
		beanUtil.gameInformationService = this.gameInformationService;
		beanUtil.invalidLinkService =  this.invalidLinkService;
		beanUtil.gameIndex = this.gameIndex;
		beanUtil.softwareIndex = this.softwareIndex;
	}
	
	
	
}
