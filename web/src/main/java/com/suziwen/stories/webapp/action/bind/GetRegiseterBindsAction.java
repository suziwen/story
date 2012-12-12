/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import java.util.List;

import com.suziwen.persistent.model.UserAuth;

/********************************************************
 * <p>
 * Description: 取得已经获得accesstoken的微博注册列表
 * </p>
 * <p>
 * Create Time: 2012-3-21 下午03:25:55
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class GetRegiseterBindsAction extends BaseBindAction {

	public String cblinkexecute() throws Exception {
		List<UserAuth> userAuths = (List<UserAuth>) request.getSession().getAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		returnResult.setDataList(userAuths);
		return "";
	}

}
