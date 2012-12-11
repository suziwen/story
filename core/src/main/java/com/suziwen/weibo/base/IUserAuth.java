/**
 * 
 */
package com.suziwen.weibo.base;

import java.util.List;

import org.appfuse.service.GenericManager;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;

/********************************************************
 * <p>
 * Description:用户与第三方用户业务操作接口
 * </p>
 * <p>
 * Create Time: 2012-3-15 下午03:46:39
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IUserAuth extends GenericManager<UserAuth,Long> {

	/***
	 * 增加用户与第三方用户关联
	 * 
	 * @param obj
	 * @return
	 * @throws BaseException
	 */
	public boolean add(UserAuth obj) throws BaseException;

	/***
	 * 修改用户与第三方用户关联
	 * 
	 * @param obj
	 * @return
	 * @throws BaseException
	 */
	public boolean modify(UserAuth obj) throws BaseException;

	/***
	 * 删除用户与第三方用户关联
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public boolean remove(String id) throws BaseException;

	/***
	 * 删除用户与第三方用户关联
	 * 
	 * @param ids
	 * @return
	 * @throws BaseException
	 */
	public boolean remove(List<String> ids) throws BaseException;

	/***
	 * 根据用户ID删除用户与第三方用户关联
	 * 
	 * @param userId
	 * @return
	 * @throws BaseException
	 */
	public boolean removeByUser(String userId) throws BaseException;

	/***
	 * 获取用户与第三方用户关联
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public UserAuth get(String id) throws BaseException;

	/***
	 * 获取用户与第三方用户关联列表
	 * 
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	public List<UserAuth> getList() throws BaseException;
	
	
	/**
	 * 根据网站标识和该用户在该网站的唯一标识获得绑定列表
	 * @param authSiteId
	 * @param bindUid
	 * @return
	 * @throws BaseException
	 */
	public List<UserAuth> getListByAuthSiteAndBindUid(String authSiteId,String bindUid) throws BaseException;

	/***
	 * 根据用户取得用户与第三方用户关联信息集
	 * @param userId
	 * @return
	 */
	public List<UserAuth> getListByUser(String userId) throws BaseException;
	
	/**
	 * 根据用户和authSiteId取得用户与第三方用户关联信息集
	 * @param userId
	 * @param authSiteId
	 * @return
	 * @throws BaseException
	 */
	public List<UserAuth> getListByUserAndAuthSite(String userId,String authSiteId) throws BaseException ;

	/***
	 * 获取用户与第三方用户关联数量
	 * 
	 * @param queryStr
	 * @return
	 * @throws BaseException
	 */
	public long getCount(String queryStr) throws BaseException;
}
