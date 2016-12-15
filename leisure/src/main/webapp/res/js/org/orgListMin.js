$(function(){
	$("#orgtree").jstree({
		"plugins" : [ "themes", "html_data", "ui", "crrm" ]
	}).bind("select_node.jstree", function(event, data) {
		var currentId = data.rslt.obj.children("a").attr("id");
		var currentName = data.rslt.obj.children("a").text();
		selectOrg(currentId, currentName);
	});
});