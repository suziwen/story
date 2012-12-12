

//地区checkbox check or uncheck时触发的函数
function selectJobhunterRegion(t){	
	if(t.checked){
		var text = t.parentNode.lastChild.innerHTML;
		var li = document.createElement("li");
		li.innerHTML = text;
		li.setAttribute('name',t.value);
		var em = document.createElement("em");
		li.appendChild(em) ;
		document.getElementById("showselected").appendChild(li);
		em.onclick=function(){
			if(t) t.checked = false ;
			jobhunterAddressEmOnclick($(this));
		}
		if($(":checkbox[checked=true]").length==1){
			$(":checkbox[checked=false]").each(function(t,m){
				m.disabled=true;
			});
		}
	}else{
		var id = t.value;
		$("#showselected li[name="+id+"]").each(function(t,m){
			$(this).remove();
		});
		$(":checkbox").each(function(t,m){
			m.disabled=false;
		});
	}
	
}
//地区选择完毕后确定时触发的函数
function jobhunterInsure(t){
	if($("#showselected").children().size()==0){
		return;
	}
	var items = document.getElementById("resume.jobhunter.address") ;
	var text;
	$("#showselected").children().each(function(t,m){
		if(t==0){
			text = $(this).attr("name") ;
		}else{
			text = text + " " + $(this).attr("name");
		}
	});
	items.setAttribute("value",text);
	jobhunterShowul();
	$( "#jobhunterregionDialog" ).dialog( "close");
}

//取消触发的函数
function jobhunterCancel(t){
	$( "#jobhunterregionDialog" ).dialog( "close");
}

function showJobhunterdialog(){
	 $("#jobhunterregionDialog").dialog( "open");
}

function jobhunterShowspan(){
	$("span.pop_selection").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 $(this).attr({style:" width:440px;"});
		 }
	 });
	$("ul.pop_selection_result").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 $(this).attr({style:"display: none;"});
		 }
	 }); 
	 $("a[href=#]").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 $(this).attr({style:"display:none ;"});
		 }
	 });
}
function jobhunterShowul(){
	$("span.pop_selection").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 $(this).attr({style:"display: none; width:440px;"});
		 }
	 }); 
	 $("ul.pop_selection_result").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 var child = $("#showselected").children()
			 $(this).attr({style:"width: 440px;"});
			 if($(this).children().size()>0){
				 $(this).children().each(function(t,m){
					 $(this).remove();
				 });
			 }
			 $(this).append(child);
		 }
	 }); 
	 $("a[href=#]").each(function(t,m){
		 var pre = $(this).parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 $(this).attr({style:"display:inline ;"});
		 }
	 });
}
function jobhunterAddressEmOnclick(me){
	var parent = me.parent();
	var grantParent = parent.parent();
	if(parent){
		parent.remove();
	}
	var text='';
	$("ul.pop_selection_result li").each(function(t){
		var pre = $(this).parent().parent().children().first();
		 if(pre.attr("name") == "resume.jobhunter.address"){
			 if(t==0){
					text = $(this).attr("name") ;
			 }else{
				text = text + " " +  $(this).attr("name");
			 }
		 }
		
	});
	var items = document.getElementById("resume.jobhunter.address") ;
	items.setAttribute("value",text);
	if(grantParent.children().size()==0){
		jobhunterShowspan();
	};
}