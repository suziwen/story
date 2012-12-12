$(function() {
	$(function() {
		//期望工作地点
		$( "#regionDialog" ).dialog({
			hide:true, 
			autoOpen: false,
			modal : true,
			height:450,
			width:680,
			show : 'slide'
		});
		
		$( "#regionbutton" ).click(function() {
			$( "#regionDialog" ).dialog( "open" );
			return false;
		});
		
		$( "#regionDialog" ).bind( "dialogopen", function(event, ui) {
			var id = $("#regionshowspan").parent().children(":first").attr("id");
			$('#regionItem').load(ctx + '/ajax/region',{'region_id': id},function(){
				
			});
		});
		
		//当前工作地点
		$( "#addressDialog" ).dialog({
			hide:true, 
			autoOpen: false,
			modal : true,
			height:450,
			width:680,
			show : 'slide'
		});

		$( "#addressbutton" ).click(function() {
			$( "#addressDialog" ).dialog( "open" );
			return false;
		});
		
		$( "#addressDialog" ).bind( "dialogopen", function(event, ui) {
			$('#addressItem').load(ctx + '/ajax/jobhunterAddress','',function(){
				
			});
		});
		
		//职能
		$( "#competencyDialog" ).dialog({
			hide:true, 
			autoOpen: false,
			modal : true,
			height:450,
			width:680,
			show : 'slide'
		});

		$( "#competencybutton" ).click(function() {
			$( "#competencyDialog" ).dialog( "open" );
			return false;
		});
		
		$( "#competencyDialog" ).bind( "dialogopen", function(event, ui) {
			var id = $("#competencyshowspan").parent().children(":first").attr("id");
			$('#competencyItem').load(ctx + '/ajax/competency',{'competency_id': id},function(){
			});
		});
		
		//行业
		$( "#industryDialog" ).dialog({
			hide:true, 
			autoOpen: false,
			modal : true,
			height:450,
			width:650,
			show : 'slide'
		});

		$( "#industrybutton" ).click(function() {
			$( "#industryDialog" ).dialog( "open" );
			return false;
		});
		
		$( "#industryDialog" ).bind( "dialogopen", function(event, ui) {
			var id = $("#industryshowspan").parent().children(":first").attr("id");
			$('#industryItem').load(ctx + '/ajax/industry',{'industry_id': id},function(){
			});
		});
	});
	
});