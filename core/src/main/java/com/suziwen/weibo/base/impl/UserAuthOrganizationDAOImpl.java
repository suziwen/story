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
import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.base.IUserAuthOrganizationDAO;

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
public class UserAuthOrganizationDAOImpl extends
		GenericDaoHibernate<UserAuthOrganization, Long> implements
		IUserAuthOrganizationDAO {

	public UserAuthOrganizationDAOImpl() {
		super(UserAuthOrganization.class);
		// TODO Auto-generated constructor stub
	}

	public static final String ENTITYNAME = UserAuthOrganization.class
			.getSimpleName();

	public Class<UserAuthOrganization> getType() {
		return UserAuthOrganization.class;
	}

	public void deleteByUser(final String userId) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				session.createQuery(
						"delete from " + ENTITYNAME + "  where userId='"
								+ userId + "'").executeUpdate();
				return 1;
			}
		});
	}

	@Override
	public List<UserAuthOrganization> getListByAuthSiteAndBindUid(
			String authSiteId, String bindUid) throws BaseException {
		return this
				.getHibernateTemplate()
				.find("from UserAuthOrganization where authSiteId = ? and bindUid = ? ",
						authSiteId, bindUid);
	}

	@Override
	public List<UserAuthOrganization> getListByUser(String userId)
			throws BaseException {
		return this.getHibernateTemplate().find(
				" from UserAuthOrganization where userId = ? ", userId);
	}

	@Override
	public List<UserAuthOrganization> getListByUserAndAuthSite(String userId,
			String authSiteId) throws BaseException {
		return this
				.getHibernateTemplate()
				.find("from UserAuthOrganization where userId = ? and authSiteId = ? ",
						userId, authSiteId);
	}
}
