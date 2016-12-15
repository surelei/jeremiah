$(function() {
	$("#createRes").click(function() {
		location.href = "new.htm";
	});
	$(".editRes").click(function() {
		location.href = "edit.htm?res.id=" + $(this).attr("id");
	});
	$(".removeRes").click(function() {
		if (confirm("确定删除资源信息吗？")){
			location.href = "delete.htm?res.id=" + $(this).attr("id");
		}
	});
});