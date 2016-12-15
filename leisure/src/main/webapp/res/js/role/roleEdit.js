$(function() {
	$("#select-res").button().click(function(){
		$("#detail").load("showAllRes.htm");
		$("#detail").dialog({
		      modal: true,
		      height: 500,
		      width: 800,
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
	$("#save-role").button().click(function() {
		if ($("#roleId").val()) {
			$("#roleEditForm").attr("action", "update.htm");
		} else {
			$("#roleEditForm").attr("action", "create.htm");
		}
		$("#roleEditForm").submit();
		return false;
	});
	$("#cancle-role").button().click(function() {
		location.href = "list.htm";
		return false;
	});
});