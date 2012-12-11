/**
 * 
 */
package com.suziwen.weibo.base;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuthOrganization;

/********************************************************
 * <p>
 * Description: 用户与第三方用户绑定关系DAO
 * </p>
 * <p>
 * Create Time: 2012-3-15 下午03:41:18
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IUserAuthOrganizationDAO extends GenericDao<UserAuthOrganization,Long> {
	
	
	/**
	 * 根据用户删除该用户绑定下的所有第三方用户
	 * @param userId
	 */
	public void deleteByUser(String userId) ;
	
	
	/**
	 * 根据网站标识和该用户在该网站的唯一标识获得绑定列表
	 * @param authSiteId
	 * @param bindUid
	 * @return
	 * @throws BaseException
	 */
	public List<UserAuthOrganization> getListByAuthSiteAndBindUid(String authSiteId,String bindUid) throws BaseException;

	/***
	 * 根据用户取得用户与第三方用户关联信息集
	 * @param userId
	 * @return
	 */
	public List<UserAuthOrganization> getListByUser(String userId) throws BaseException;
	
	/**
	 * 根据用户和authSiteId取得用户与第三方用户关联信息集
	 * @param userId
	 * @param authSiteId
	 * @return
	 * @throws BaseException
	 */
	public List<UserAuthOrganization> getListByUserAndAuthSite(String userId,String authSiteId) throws BaseException ;

}
