package com.jyb.timer;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jyb.entity.GameInformation;
import com.jyb.entity.InvalidLink;
import com.jyb.entity.Software;
import com.jyb.util.BeanUtil;
import com.jyb.util.CheckUrlUtil;
/**
 * 排查游戏和软件资源中失效的链接
 * @author jyb
 *
 */
public class CheckLinkRunnable implements Runnable {
    
	private Logger log = LoggerFactory.getLogger(CheckLinkRunnable.class);
	
	@Override
	public void run() {
		Double startTime = (double) System.currentTimeMillis();
		List<GameInformation> gameList= BeanUtil.beanUtil.gameInformationService.listAll(null, null,"gameBrowseFrequency");
		
		 for(GameInformation game:gameList){
        	if(game.getDownloadType()==1&&game.getAuditStatus()==2){//验证百度云链接
  				try {
					if(!CheckUrlUtil.checkBDY(game.getGameDownloadAddress())){
						game.setAuditStatus(1);
						game.setIsUseful(2);
						BeanUtil.beanUtil.gameInformationService.save(game);
						InvalidLink link = new InvalidLink();
						link.setInvalidName(game.getGameName());
						link.setDownloadType(game.getDownloadType());
						link.setDownloadAddress(game.getGameDownloadAddress());
						link.setLinkPwd(game.getLinkPwd());
						link.setLargeCategory(game.getLargeCategory());
						link.setResourceId(game.getId());
						link.setUserId(game.getUserInformation().getId());
						link.setUserName(game.getUserInformation().getUserName());
						link.setCreationTime(new Date());
						BeanUtil.beanUtil.invalidLinkService.save(link);
						BeanUtil.beanUtil.gameIndex.updateIndex(game);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					  log.error("验证百度云链接失败:"+e); 
					  continue;   
				}
  			}
  			 if(game.getDownloadType()==2&&game.getAuditStatus()==2){//验证其他链接地址
               if(!CheckUrlUtil.checkUrl(game.getGameDownloadAddress(), 1000)){
            	        game.setAuditStatus(1);
            	        game.setIsUseful(2);
            	        BeanUtil.beanUtil.gameInformationService.save(game);
            	    	InvalidLink link = new InvalidLink();
            	        link.setInvalidName(game.getGameName());
						link.setDownloadType(game.getDownloadType());
						link.setDownloadAddress(game.getGameDownloadAddress());
						link.setLargeCategory(game.getLargeCategory());
						link.setResourceId(game.getId());
						link.setUserId(game.getUserInformation().getId());
						link.setUserName(game.getUserInformation().getUserName());
						link.setCreationTime(new Date());
						BeanUtil.beanUtil.invalidLinkService.save(link);
						BeanUtil.beanUtil.gameIndex.updateIndex(game);
               }
    	   }
        }
        
        List<Software> softwareList= BeanUtil.beanUtil.softwareService.listAll(null, null,"browseFrequency");
        for(Software software:softwareList){
  			if(software.getDownloadType()==1&&software.getAuditStatus()==2){//验证百度云链接
  				try {
					if(!CheckUrlUtil.checkBDY(software.getDownloadAddress())){
						software.setAuditStatus(1);
						software.setIsUseful(2);
						BeanUtil.beanUtil.softwareService.save(software);
						InvalidLink link = new InvalidLink();
            	        link.setInvalidName(software.getName());
						link.setDownloadType(software.getDownloadType());
						link.setDownloadAddress(software.getDownloadAddress());
					     link.setLinkPwd(software.getLinkPwd());
						link.setLargeCategory(software.getLargeCategory());
						link.setResourceId(software.getId());
						link.setUserId(software.getUserInformation().getId());
						link.setUserName(software.getUserInformation().getUserName());
						link.setCreationTime(new Date());
						BeanUtil.beanUtil.invalidLinkService.save(link);
						BeanUtil.beanUtil.softwareIndex.updateIndex(software);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				    log.error("验证百度云链接失败:"+e);
				    continue;  
				}
  			}
  			 if(software.getDownloadType()==2&&software.getAuditStatus()==2){//验证其他链接地址
               if(!CheckUrlUtil.checkUrl(software.getDownloadAddress(), 1000)){
    					software.setAuditStatus(1);
    					software.setIsUseful(2);
    					BeanUtil.beanUtil.softwareService.save(software);
    					InvalidLink link = new InvalidLink();
            	        link.setInvalidName(software.getName());
						link.setDownloadType(software.getDownloadType());
						link.setDownloadAddress(software.getDownloadAddress());
						link.setLargeCategory(software.getLargeCategory());
						link.setResourceId(software.getId());
						link.setUserId(software.getUserInformation().getId());
						link.setUserName(software.getUserInformation().getUserName());
						link.setCreationTime(new Date());
						BeanUtil.beanUtil.invalidLinkService.save(link);
						BeanUtil.beanUtil.softwareIndex.updateIndex(software);
               }
    	   }
  		}
  		Double endTime = (double) System.currentTimeMillis();
  	    log.info("过滤失效链接处理时间为:"+(endTime - startTime)*0.001+"秒");//记录结束时间
   }
}
