var BindType_SINA="sina";
var BindType_QQ="qq";
var BindType_SOHU="sohu";
/**
 * 按钮绑定操作
 */
var bindBtn = function(){
	/**
	 * 新浪微博登录
	 */
	$('sinabtn').observe('click',function(){
		bindFunction(BindType_SINA);
	});
	/**
	 * 腾讯微博登录
	 */
	$('qqblogbtn').observe('click',function(){
		bindFunction(BindType_QQ);
	});
	/**
	 * 搜狐微博登录
	 */
	$('qqbtn').observe('click',function(){
		bindFunction(BindType_SOHU);
	});
}

/**
 * 绑定操作
 * @param {Object} bindType
 */
var bindFunction = function(bindType){
	var backUrl=QueryPara.getPara("backUrl");
	if(!!backUrl){
		//使用主页
		var JsonCookie=new JsonCookies();
		JsonCookie.set(Cookie_Back_Url,backUrl,10); 
	}
	
	var paras = {
		bindType : bindType,
		callBack : document.location.protocol + '//' +document.location.host +guest_action + "?method=guest.bind.defaultauthbind&bindType="+bindType+"&isCallBack="+SysConst_True,
		sendRedirectUrl : document.location.protocol + '//' +document.location.host +BaseActionPath + "/pcb/bindcallback.html?bindType="+bindType
	};
	
	getServerData(user_defaultauthbind_action,$H(paras).toQueryString(),function(rs){
		if(!!rs && rs.value == SysConst_True){
			var result = rs.dataList;
			if(result.type =='0'){
				//也可以弹出窗口
				document.location.href = result.href;
			}
		} else {
			msgDlg(!!rs ? rs.message : "");
		}
	});
}