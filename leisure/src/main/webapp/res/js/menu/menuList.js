$(function() {
	var currentId = 0;
	$("#menutree").jstree({
		"plugins" : [ "themes", "html_data", "ui", "crrm" ]
	}).bind("select_node.jstree", function(event, data) {
		currentId = data.rslt.obj.children("a").attr("id");
	});
	$("#createTopMenu").click(function() {
		location.href = "new.htm?menu.parent.code=root_menu";
	});
	$("#createMenu").click(
			function() {
				if (currentId == 0) {
					alert("请选择一个菜单项作为父菜单！");
					return;
				}
				var menuName = $("#" + currentId).text();
				if (confirm("子菜单将被创建在菜单[" + menuName + "]下，确认吗？")) {
					location.href = "new.htm?menu.parent.id="	+ currentId + "&menu.parent.name=" + menuName;
				}
			});
	$("#editMenu").click(function() {
		if (currentId == 0) {
			alert("请选择要编辑的菜单！");
			return;
		}
		location.href = "edit.htm?menu.id=" + currentId;
	});
	$("#showMenu").click(function() {
		if (currentId == 0) {
			alert("请选择要查看的菜单！");
			return;
		}
		$("#detail").load("show.htm?menu.id=" + currentId);
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
	});
	$("#removeMenu").click(function() {
		if (currentId == 0) {
			alert("请选择要删除的菜单！");
			return;
		}
		var menuName = $("#" + currentId).text();
		if (confirm("菜单[" + menuName + "]及其子菜单将被删除，确认吗？")) {
			location.href = "delete.htm?menu.id=" + currentId;
		}
	});
});