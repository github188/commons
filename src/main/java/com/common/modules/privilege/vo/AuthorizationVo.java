/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.common.modules.privilege.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.common.modules.privilege.entity.Resource;
import com.common.modules.privilege.entity.Role;
import com.common.utils.SpringContextHolder;

/**
 * @Package: com.yxw.platform.privilege.vo
 * @ClassName: SimpleAuthorizationVo
 * @Statement: <p>鉴权vo</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AuthorizationVo {
	private String userName;
	/**是否通过remeberMe自动登录*/
	private boolean isRemeberMe;
	private Map<String, Role> roleMap;

	public AuthorizationVo(String userName, boolean isRemeberMe) {
		super();
		this.userName = userName;
		this.isRemeberMe = isRemeberMe;
		this.roleMap = new HashMap<String, Role>();
	}

	/**
	 * @param userName
	 * @param isRemeberMe
	 * @param roles
	 * @param stringPermissions
	 */
	public AuthorizationVo(String userName, boolean isRemeberMe, Map<String, Role> roleMap) {
		super();
		this.userName = userName;
		this.isRemeberMe = isRemeberMe;
		this.roleMap = roleMap;
	}

	public Map<String, Resource> getResourceMap() {
		Map<String, Resource> resourceMap = new HashMap<String, Resource>();

		//从redis 取数据
		RoleResourceCache roleResourceCache = SpringContextHolder.getBean(RoleResourceCache.class);
		for (Role role : roleMap.values()) {
			List<Resource> tmpResourceList = roleResourceCache.queryResourcesByRoleId(role.getId());
			if (CollectionUtils.isNotEmpty(tmpResourceList)) {
				for (Resource resource : tmpResourceList) {
					resourceMap.put(resource.getAbstr(), resource);
				}
			}
		}

		return resourceMap;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the isRemeberMe
	 */
	public boolean isRemeberMe() {
		return isRemeberMe;
	}

	/**
	 * @param isRemeberMe the isRemeberMe to set
	 */
	public void setRemeberMe(boolean isRemeberMe) {
		this.isRemeberMe = isRemeberMe;
	}

	/**
	 * @return the roleMap
	 */
	public Map<String, Role> getRoleMap() {
		return roleMap;
	}

	/**
	 * @param roleMap the roleMap to set
	 */
	public void setRoleMap(Map<String, Role> roleMap) {
		this.roleMap = roleMap;
	}

}
