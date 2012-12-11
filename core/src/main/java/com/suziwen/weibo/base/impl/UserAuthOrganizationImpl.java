/**
 * 
 */
package com.suziwen.weibo.base.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.base.IUserAuthOrganization;
import com.suziwen.weibo.base.IUserAuthOrganizationDAO;

/********************************************************
 * <p>
 * Description:用户与第三方用户业务接口实现类
 * </p>
 * <p>
 * Create Time: 2012-3-15 下午03:50:56
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
@Transactional
@Service("userAuthOrganization")
public class UserAuthOrganizationImpl extends GenericManagerImpl<UserAuthOrganization, Long>  implements IUserAuthOrganization {
	
	/**
	 * 用户与第三方用户DAO
	 */
	@Autowired
	private IUserAuthOrganizationDAO userAuthOrganizationDAO;

	/***
	 * 增加用户与第三方用户关联
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#add(com.cblink.persistent.model.UserAuthOrganization
	 * )
	 */
	@Override
	public boolean add(UserAuthOrganization obj) throws BaseException {
		this.userAuthOrganizationDAO.save(obj);
		return true;
	}

	/***
	 * 修改用户与第三方用户关联
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#modify(com.cblink.persistent.model.UserAuthOrganization
	 * )
	 */
	@Override
	public boolean modify(UserAuthOrganization obj) throws BaseException {
		this.userAuthOrganizationDAO.save(obj);
		return true;
	}

	/***
	 * 删除用户与第三方用户关联
	 * 
	 * @see com.cblink.account.biz.IUserAuth#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String id) throws BaseException {
		this.userAuthOrganizationDAO.remove(Long.parseLong(id));
		return true;
	}

	/***
	 * 增加用户与第三方用户关联列表
	 * 
	 * @see com.cblink.account.biz.IUserAuth#remove(java.util.List)
	 */
	@Override
	public boolean remove(List<String> ids) throws BaseException {
		for(String id : ids){
			this.remove(id);
		}
		return true;
	}

	/***
	 * 根据用户ID删除用户与第三方用户关联
	 * 
	 * @see com.cblink.account.biz.IUserAuth#removeByUser(java.lang.String)
	 */
	@Override
	public boolean removeByUser(String userId) throws BaseException {
		this.userAuthOrganizationDAO.deleteByUser(userId);
		return true;
	}

	/***
	 * 获取用户与第三方用户关联
	 * 
	 * @see com.cblink.account.biz.IUserAuth#get(java.lang.String)
	 */
	@Override
	public UserAuthOrganization get(String id) throws BaseException {

		return this.userAuthOrganizationDAO.get(Long.parseLong(id));
	}

	/***
	 * 获取用户与第三方用户关联列表
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#getList(com.cblink.common.pagination
	 * .Pagination)
	 */
	@Override
	public List<UserAuthOrganization> getList() throws BaseException {
		return this.userAuthOrganizationDAO.getAll();
	}
	
	
	public List<UserAuthOrganization> getListByAuthSiteAndBindUid(String authSiteId,String bindUid) throws BaseException{
		return this.getUserAuthOrganizationDAO().getListByAuthSiteAndBindUid(authSiteId, bindUid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cblink.account.biz.IUserAuth#getListByUser(java.lang.String)
	 */
	@Override
	public List<UserAuthOrganization> getListByUser(String userId) throws BaseException {
		return this.getListByUser(userId);
	}
	
	public List<UserAuthOrganization> getListByUserAndAuthSite(String userId,String authSiteId) throws BaseException {
		return this.getUserAuthOrganizationDAO().getListByUserAndAuthSite(userId, authSiteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cblink.account.biz.IUserAuth#getCount(java.lang.String)
	 */
	@Override
	public long getCount() throws BaseException {
		return this.userAuthOrganizationDAO.getAll().size();
	}

	public IUserAuthOrganizationDAO getUserAuthOrganizationDAO() {
		return userAuthOrganizationDAO;
	}

	public void setUserAuthOrganizationDAO(IUserAuthOrganizationDAO userAuthOrganizationDAO) {
		this.userAuthOrganizationDAO = userAuthOrganizationDAO;
	}



}
