package com.jyb.realm;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;


/**
 * 自定义Realm
 *
 *
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserInformationService userInformationService;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName=(String) SecurityUtils.getSubject().getPrincipal();
		UserInformation user=userInformationService.findByUserName(userName);
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		Set<String> roles=new HashSet<String>();
		if(user.getUserRole()==0){//管理员
			roles.add("0");
			info.addStringPermission("后台-首页");
			info.addStringPermission("后台-游戏资源");
			info.addStringPermission("后台-用户信息");
			info.addStringPermission("后台-类别信息");
			info.addStringPermission("后台-友情链接");
			info.addStringPermission("后台-留言板");
			info.addStringPermission("后台-失效链接");
			info.addStringPermission("后台-日志信息");
			info.addStringPermission("后台-评论信息");

			/********AdminDataDictionaryController**********/
			info.addStringPermission("后台-分页查询所有的类别信息");
			info.addStringPermission("后台-根据id查找类别信息");
			info.addStringPermission("后台-保存或修改类别信息");
			info.addStringPermission("后台-删除类别信息");
			
			/********AdminFriendshipLinkController**********/
			info.addStringPermission("后台-查询所有的友情链接信息");
			info.addStringPermission("后台-保存友情链接信息");
			info.addStringPermission("后台-删除友情链接信息");
			
			/********AdminGameInformationController**********/
			info.addStringPermission("后台-分页查询所有的游戏资源");
			info.addStringPermission("后台-修改游戏信息");
			info.addStringPermission("后台-审核通过");
			info.addStringPermission("后台-审核被驳回");
			info.addStringPermission("后台-批量删除游戏资源");
			
			/********AdminSoftwareController**********/
			info.addStringPermission("后台-分页查询所有的软件资源");
			info.addStringPermission("后台-批量删除软件资源");
			
			
			/********AdminInvalidLinkController**********/
			info.addStringPermission("后台-分页查询所有的失效链接资源");
			
			/********AdminLogController**********/
			info.addStringPermission("后台-分页查询所有的日志信息");
			info.addStringPermission("后台-保存日志信息");
			
			/********AdminUserInformationController**********/
			info.addStringPermission("后台-分页查询所有用户信息");
			info.addStringPermission("后台-设置账号是否禁用");
			info.addStringPermission("后台-充值积分");
			info.addStringPermission("后台-重置用户密码"); 
			
			/********AdminUserMessageController**********/
			info.addStringPermission("后台-分页查询所有留言信息"); 
			info.addStringPermission("后台-删除留言信息"); 
			
			/********AdminUserReviewsController**********/
			info.addStringPermission("后台-分页查询所有评论信息"); 
			info.addStringPermission("后台-删除评论信息"); 
			info.addStringPermission("后台-安全退出");
		}
		info.setRoles(roles);
		return info;
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		UserInformation user=userInformationService.findByUserName(userName);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getUserPassword(),"xxx");
			return authcInfo;
		}else{
			return null;			
		}
	}

}
