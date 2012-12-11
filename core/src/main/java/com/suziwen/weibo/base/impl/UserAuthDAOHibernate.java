/**
 * 
 */
package com.suziwen.weibo.base.impl;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;
import com.suziwen.weibo.base.IUserAuthDAO;

/********************************************************
 * <p>
 * Description: 用户与第三方用户绑定关系DAO实现类
 * </p>
 * <p>
 * Create Time: 2012-3-15 下午03:43:07
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
@Repository
public class UserAuthDAOHibernate extends GenericDaoHibernate<UserAuth, Long> implements IUserAuthDAO {

	public UserAuthDAOHibernate() {
		super(UserAuth.class);
	}

	public static final String ENTITYNAME  = UserAuth.class.getSimpleName();
	
	public Class<UserAuth> getType(){
		return UserAuth.class;
	}
	
	public void deleteByUser(final String userId) {
		getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {						 
							session.createQuery("delete from " + ENTITYNAME + "  where userId='" + userId + "'").executeUpdate();
						  return 1;
					}
				});
	}

	@Override
	public List<UserAuth> getListByAuthSiteAndBindUid(String authSiteId,
			String bindUid) throws BaseException {
		List<UserAuth> userAuths = this.getHibernateTemplate().find(" from UserAuth where siteId = ? and bindUid = ? ", authSiteId,bindUid);
		return userAuths;
	}

	@Override
	public List<UserAuth> getListByUser(String userId) throws BaseException {
		
		return this.getHibernateTemplate().find(" from UserAuth where userId = ? ", userId);
	}

	@Override
	public List<UserAuth> getListByUserAndAuthSite(String userId,
			String authSiteId) throws BaseException {
		return this.getHibernateTemplate().find(" from UserAuth where siteId = ? and userId = ? ", authSiteId ,userId);
	}
}
