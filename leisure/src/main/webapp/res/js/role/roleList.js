$(function() {
	$("#createRole").click(function() {
		location.href = "new.htm";
	});
	$("#editRole").click(function() {
		var id = this.attr("name");
		location.href = "edit.htm?role.id=" + id;
	});
	$("#showUsers").click(function() {
		var id = $(this).attr("name");
		var roleName = $("#" + id).text();
		$("#detail").load("showUsers.htm?role.id=" + id);
		showDetail(roleName + "所属用户");
	});
	$("#showResources").click(function() {
		var id = $(this).attr("name");
		var roleName = $("#" + id).text();
		$("#detail").load("showResources.htm?role.id=" + id);
		showDetail(roleName + "所属权限");
	});
	$("#removeRole").click(function() {
		var id = this.attr("name");
		var roleName = $("#" + id).text();
		if (confirm("角色[" + roleName + "]将被删除，确认吗？")) {
			location.href = "delete.htm?role.id=" + id;
		}
	});
});
function showDetail(title){
	$("#detail").attr("title", title);
	$("#detail").dialog({
	      modal: true,
	      height: 400,
	      width: 600,
	      buttons: {
	        Ok: function() {
	          $(this).dialog("close");
	        }
	      },
	      autoOpen: false,
	      position : "top",
	      show: {
	        effect: "blind",
	        duration: 1000
	      },
	      hide: {
	        effect: "explode",
	        duration: 1000
	      }
	});
	$("#detail").dialog("open");	
}