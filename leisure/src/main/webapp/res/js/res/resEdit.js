$(function() {
	$("#save-res").click(function() {
		$("#resEditForm").attr("action", "update.htm");
		$("#resEditForm").submit();
		return false;
	});
	$("#cancle-res").click(function() {
		location.href = "list.htm";
		return false;
	});
});