// 创建一个闭包    
(function($) {
	// 插件的定义
	$.fn.selectWindow = function(options) {
		// build main options before element iteration
		var opts = $.extend({}, $.fn.selectWindow.defaults, options);
		// iterate and reformat each matched element
		return this.each(function() {
			$this = $(this);
			// build element specific options
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			// call our build function
			var markup = $.fn.selectWindow.build($this, o);
			var id = $this.attr('id');
			$this.attr('id', 'div_' + id);
			$this.html(markup);	
			$("em",$this).click($this,emOnclick);
			
			//em的click事件
			function emOnclick(){
				var parent = $(this).parent();
				var grantParent = parent.parent();
				if(parent){
					parent.remove();
				}
				var value = '';
				var name = '';
				var pre ;
				var second ;
				if($("ul.pop_selection_result li").size()==0){
					$("ul.pop_selection_result").each(function(t){
						pre = $(this).parent().children().first();
						second = $(this).parent().children().first().next();
					});
					industryShowSpan();
				}else{
					$("ul.pop_selection_result li").each(function(t){
						pre = $(this).parent().parent().children().first();
						second = $(this).parent().parent().children().first().next();
						 if(pre.attr("name") == id){
							 if(t==0){
								 value = $(this).attr("name") ;
								 name = $(this).text();
							 }else{
								 value += " " +  $(this).attr("name");
								 name += " "+ $(this).text() ;
							 }
						 }
					});
				}
				pre.attr("value",value);
				second.attr("value",name);
			}
			
			//地区选择数为0的样式
			function industryShowSpan(){
				$("span.pop_selection").each(function(t,m){
					 var pre = $(this).parent().children().first();
					 if(pre.attr("name") == id){
						 $(this).show();
					 }
				 });
				$("ul.pop_selection_result").each(function(t,m){
					 var pre = $(this).parent().children().first();
					 if(pre.attr("name") == id){
						 $(this).hide();
					 }
				 }); 
				 $("a[href=#]").each(function(t,m){
					 var pre = $(this).parent().children().first();
					 if(pre.attr("name") == id){
						 $(this).hide();
					 }
				 });
			}
		});
	};
	
	// 定义暴露build函数
	$.fn.selectWindow.build = function(me, opts) {
		//向后台传递id的输入框
		var id = 'check_' + me.attr('id'), html = '<input class="input none" id="' + me.attr('id');
		html += '" name="' + me.attr('id');
		html += '" value="' + $.trim(me.html()) + '">';
		//向后台传递name的输入框,支持单个选项
		if ($.isArray(opts.items)) {
			$(opts.items).each(function(index, element) {
				html += '<input class="input none" id="' + me.attr('id').replace("id","name");
				html += '" name="' + me.attr('id').replace("id","name");
				html += '" value="' + element.name + '">';
			})
		}
		//如果session里有值，则显示ul-li元素，否则显示span
		if($.trim(me.html())!=''){
			html += '<span id = "'+opts.name+'showspan" class="pop_selection input-text" ';
			html += 'style="width:'+opts.width+'px;display:none;">';
			html += '<em class="icon_selection"><a href="javascript:;" id="'+opts.name+'button" >选择</a></em></span>';
			html += '<ul class="pop_selection_result input-text Clear" style="width:'+opts.width+'px;">';
			$(opts.items).each(function(index, element) {
				html += '<li name="'+element.id+'">'+element.name+'<em></em></li>';
			});
			html += '</ul>';
			html += '<a href="#" onclick="javascript: show'+opts.name+'dialog()">修改</a>';
			html += '<div id="'+opts.name+'Dialog" title="'+opts.title+'"><div id="'+opts.name+'Item"></div></div>';
		}else{
			html += '<span id = "'+opts.name+'showspan" class="pop_selection input-text" ';
			html += 'style="width:'+opts.width+'px">';
			html += '<em class="icon_selection"><a href="javascript:;" id="'+opts.name+'button" >选择</a></em></span>';
			html += '<ul class="pop_selection_result input-text Clear" style="width:'+opts.width+'px; display:none;">';
			html += '</ul>';
			html += '<a href="#" style="display:none;" onclick="javascript: show'+opts.name+'dialog()">修改</a>';
			html += '<div id="'+opts.name+'Dialog" title="'+opts.title+'"><div id="'+opts.name+'Item"></div></div>';
		}
		
		return html;
	};
	
	// 插件的defaults
	$.fn.selectWindow.defaults = {
		width:'',//生成查询框的大小
		name:'',//查询框的类型,industry、competency、region
		title:'',//dailog的title
		items:[], //查询框的值，{id:'-2',name:'高级管理'}/{id:'-2',name:'北京'}
		okHandler : null
	};

	// 闭包结束
})(jQuery);

function showindustrydialog(){
	 $("#industryDialog").dialog( "open");
}

function showcompetencydialog(){
	 $("#competencyDialog").dialog( "open");
}
function showregiondialog(){
	 $("#regionDialog").dialog( "open");
}






