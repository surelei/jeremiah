$(function() {
	$("#save-org").button().click(function() {
		if ($("#orgId").val()) {
			$("#orgEditForm").attr("action", "update.htm");
		} else {
			$("#orgEditForm").attr("action", "create.htm");
		}
		$("#orgEditForm").submit();
		return false;
	});
	$("#cancle-org").button().click(function() {
		location.href = "list.htm";
		return false;
	});
});