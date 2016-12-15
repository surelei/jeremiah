$(function() {
	$("#createUser").click(function() {
		location.href = "new.htm";
	});
	$(".showRoles").click(function() {
		$("#detail").load("showRoles.htm?user.id=" + $(this).attr("id"));
		$("#detail").dialog({
		      modal: true,
		      height: 300,
		      width: 400,
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
	$(".editUser").click(function() {
		location.href = "edit.htm?user.id=" + $(this).attr("id");
	});
	$(".removeUser").click(function() {
		location.href = "delete.htm?user.id=" + $(this).attr("id");
	});
});