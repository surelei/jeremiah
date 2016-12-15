$(function() {
	var currentId = 0;
	$("#orgtree").jstree({
		"plugins" : [ "themes", "html_data", "ui", "crrm" ]
	}).bind("select_node.jstree", function(event, data) {
		currentId = data.rslt.obj.children("a").attr("id");
	});
	$("#createTopOrg").click(function() {
		location.href = "new.htm?org.parent.code=root_org";
	});
	$("#createOrg").click(
			function() {
				if (currentId == 0) {
					alert("请选择一个机构作为父机构！");
					return;
				}
				var orgName = $("#" + currentId).text();
				if (confirm("子机构将被创建在机构[" + orgName + "]下，确认吗？")) {
					location.href = "new.htm?org.parent.id="
							+ currentId + "&org.parent.name=" + orgName;
				}
			});
	$("#editOrg").click(function() {
		if (currentId == 0) {
			alert("请选择要编辑的机构！");
			return;
		}
		location.href = "edit.htm?org.id=" + currentId;
	});
	$("#showOrg").click(function() {
		if (currentId == 0) {
			alert("请选择要查看的机构！");
			return;
		}
		$("#detail").load("show.htm?org.id=" + currentId);
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
	$("#removeOrg").click(function() {
		if (currentId == 0) {
			alert("请选择要删除的机构！");
			return;
		}
		var orgName = $("#" + currentId).text();
		if (confirm("机构[" + orgName + "]及其子机构将被删除，确认吗？")) {
			location.href = "delete.htm?org.id=" + currentId;
		}
	});
});