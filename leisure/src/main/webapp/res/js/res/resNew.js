$(function() {
	$("#save-res").click(function() {
		$("#resNewForm").attr("action", "create.htm");
		$("#resNewForm").submit();
		return false;
	});
	$("#cancle-res").click(function() {
		location.href = "list.htm";
		return false;
	});
});