/**
 * 
 */
package com.suziwen.weibo.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;
import com.suziwen.weibo.base.IUserAuth;
import com.suziwen.weibo.base.IUserAuthDAO;

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
@Service("userAuth")
public class UserAuthImpl extends GenericManagerImpl<UserAuth, Long> implements IUserAuth {
	
	/**
	 * 用户与第三方用户DAO
	 */
	@Autowired
	private IUserAuthDAO userAuthDAO;

	/***
	 * 增加用户与第三方用户关联
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#add(com.cblink.persistent.model.UserAuth
	 * )
	 */
	@Override
	public boolean add(UserAuth obj) throws BaseException {
		this.userAuthDAO.save(obj);
		return true;
	}

	/***
	 * 修改用户与第三方用户关联
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#modify(com.cblink.persistent.model.UserAuth
	 * )
	 */
	@Override
	public boolean modify(UserAuth obj) throws BaseException {
		this.userAuthDAO.save(obj);
		return true;
	}

	/***
	 * 删除用户与第三方用户关联
	 * 
	 * @see com.cblink.account.biz.IUserAuth#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String id) throws BaseException {

		this.userAuthDAO.remove(Long.parseLong(id));
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
		if(StringUtils.isBlank(userId))	{
			throw new BaseException("");
		}
		this.userAuthDAO.deleteByUser(userId);
		return true;
	}

	/***
	 * 获取用户与第三方用户关联
	 * 
	 * @see com.cblink.account.biz.IUserAuth#get(java.lang.String)
	 */
	@Override
	public UserAuth get(String id) throws BaseException {
		if(StringUtils.isBlank(id)){
			throw new BaseException("");
		}
		return this.userAuthDAO.get(Long.parseLong(id));
	}

	/***
	 * 获取用户与第三方用户关联列表
	 * 
	 * @see
	 * com.cblink.account.biz.IUserAuth#getList(com.cblink.common.pagination
	 * .Pagination)
	 */
	@Override
	public List<UserAuth> getList() throws BaseException {
		return this.userAuthDAO.getAll();
	}
	
	
	public List<UserAuth> getListByAuthSiteAndBindUid(String authSiteId,String bindUid) throws BaseException{
		List<UserAuth> result = this.getList();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cblink.account.biz.IUserAuth#getListByUser(java.lang.String)
	 */
	@Override
	public List<UserAuth> getListByUser(String userId) throws BaseException {
		return this.getListByUser(userId);
	}
	
	public List<UserAuth> getListByUserAndAuthSite(String userId,String authSiteId) throws BaseException {
		return this.getListByUserAndAuthSite(userId, authSiteId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cblink.account.biz.IUserAuth#getCount(java.lang.String)
	 */
	@Override
	public long getCount(String queryStr) throws BaseException {
		//TODO
		return this.userAuthDAO.getAll().size();
	}

	public IUserAuthDAO getUserAuthDAO() {
		return userAuthDAO;
	}

	public void setUserAuthDAO(IUserAuthDAO userAuthDAO) {
		this.userAuthDAO = userAuthDAO;
	}

}
