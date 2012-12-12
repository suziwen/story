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
	$('#sinabtn').bind('click',function(){
		bindFunction(BindType_SINA);
	});
	/**
	 * 腾讯微博登录
	 */
	$('#qqblogbtn').bind('click',function(){
		bindFunction(BindType_QQ);
	});
	/**
	 * 搜狐微博登录
	 */
	$('#qqbtn').bind('click',function(){
		bindFunction(BindType_SOHU);
	});
}

/**
 * 绑定操作
 * @param {Object} bindType
 */
var bindFunction = function(bindType){
	var backUrl=$.url(document.location.href).param('backUrl');
	if(!!backUrl){
		//使用主页
		//var JsonCookie=new JsonCookies();
		//JsonCookie.set(Cookie_Back_Url,backUrl,10); 
	}
	
	var paras = {
		bindType : bindType,
		callBack : document.location.protocol + '//' +document.location.host + "/bind/defaultauthbind?bindType="+bindType+"&isCallBack=1",
		sendRedirectUrl : document.location.protocol + '//' +document.location.host  + "/pcb/bindcallback.html?bindType="+bindType
	};
	$.ajax('/bind/defaultauthbind',{
        cache : true,
        type : 'POST',
        data : paras,
        dataType : 'json',
        success : function(data){
            if(!!data){
            	var result = data.dataList;
            	document.location.href = result.href;
            }
        }
    });
}

$(function(){
	bindBtn();
});