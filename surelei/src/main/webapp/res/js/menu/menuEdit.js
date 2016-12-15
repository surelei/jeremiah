$(function() {
	$("#save-menu").button().click(function() {
		if ($("#menuId").val()) {
			$("#menuEditForm").attr("action", "update.htm");
		} else {
			$("#menuEditForm").attr("action", "create.htm");
		}
		$("#menuEditForm").submit();
		return false;
	});
	$("#cancle-menu").button().click(function() {
		location.href = "list.htm";
		return false;
	});
});