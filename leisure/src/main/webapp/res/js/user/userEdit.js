$(function() {
	$("#list-org").click(function() {
		$("#detail").load("/org/listMin.htm");
	});
	$("#list-role").click(function() {

	});
	$("#save-user").click(function() {

	});
	$("#cancle-user").click(function() {

	});
});
function open(title, close) {
	$("#detail").attr("title", title);
	$("#detail").dialog({
		modal : true,
		height : 400,
		width : 500,
		buttons : {
			Ok : function() {
				close();
				$(this).dialog("close");
			}
		},
		autoOpen : false,
		position : "top",
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("#detail").dialog("open");
}