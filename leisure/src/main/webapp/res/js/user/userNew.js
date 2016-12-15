$(function() {
	$("#list-org").click(function() {
		$("#detail").load("../org/listMin.htm");
		open("选择组织结构");
		return false;
	});
	$("#list-role").click(function() {
		var ids = "|";
		$("#roles option").each(function(){
			ids = ids + $(this).val() + "|";
		});
		$("#detail").load("../role/listMin.htm?ids=" + ids);
		open("选择角色");
		return false;
	});
	$("#save-user").click(function() {

	});
	$("#cancle-user").click(function() {

	});
});
function open(title) {
	$("#detail").attr("title", title);
	$("#detail").dialog({
		modal : true,
		height : 400,
		width : 500,
		buttons : {
			OK : function() {
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

function selectOrg(currentId, currentName) {
	$("#orgId").val(currentId);
	$("#orgName").val(currentName);
}