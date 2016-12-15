$(function(){
	$("#check-all").click(function(){
		if ($(this).attr("checked")=="true"){
			$("input[name='checkbox']").attr("checked","true");
		} else {
			$("input[name='checkbox']").attr("checked","false");
		}
	});
	
});