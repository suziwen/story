$(function() {
	//打开地区选择Dialog
	$(function() {
		$( "#competencyDialog" ).dialog({
			autoOpen: false,
			modal : true,
			height:500,
			width:700,
			show : 'slide'
		});

		$( "#competencybutton" ).click(function() {
			$( "#competencyDialog" ).dialog( "open" );
			return false;
		});
		
		$( "#competencyDialog" ).bind( "dialogopen", function(event, ui) {
			$('#competencyItem').load('/ajax/region');
		});
	});
	
	//地区初始化jsp选择性显示数据
	$(document).ready(function(){
		$("[id=河北]").show();
		$("li.parentli").click(function(l){
			var me = $(this)
			var text = me.text();
			var id = me.context.id;
			$("div.childliststyle").hide();
			$("#"+text).show();
		});
		
	});

});

//地区checkbox check or uncheck时触发的函数
function selectRegion(t){	
	if(t.checked){
		var text = t.parentNode.lastChild.innerHTML;
		var li = document.createElement("li");
		li.innerHTML = text;
		li.name = t.value;
		var em = document.createElement("em");
		li.appendChild(em) ;
		document.getElementById("showselected").appendChild(li);
		em.onclick=function(){
			if($(this).parent()) $(this).parent().remove();
			if(t) t.checked = false ;
		}
		if($(":checkbox[checked=true]").length==3){
			$(":checkbox[checked=false]").each(function(t,m){
				m.disabled=true;
			});
		}
	}else{
		var id = t.value;
		$("li[name="+id+"]").each(function(t,m){
			$(this).remove();
		});
		$(":checkbox").each(function(t,m){
			m.disabled=false;
		});
	}
	
}
//地区选择完毕后确定时触发的函数
function selectedInsure(t){
	if($("#showselected").children().size()==0){
		return;
	}
	var items = document.getElementById("resume.jobhunter.competency") ;
	var text;
	$("#showselected").each(function(t,m){
		if(t==0){
			text = m.id ;
		}else{
			text = text + " " + m.id
		}
	});
	items.value = text;
	 $("span.pop_selection").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.competency"){
			 $(this).attr({style:"display: none; width:440px;"});
		 }
	 }); 
	 $("ul.pop_selection_result").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.competency"){
			 var child = $("#showselected").children()
			 $(this).attr({style:"width: 440px;"});
			 $(this).append(child);
		 }
	 }); 
	 $("a[href=#]").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.competency"){
			 $(this).attr({style:"display: inline;"});
			 $(this).click(function(){
				 alert()
			 });
		 }
	 });
	 $( "#competencyDialog" ).dialog( "close");
}

//取消触发的函数
function selectedCancel(t){
	$( "#competencyDialog" ).dialog( "close");
}