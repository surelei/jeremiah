$(function() {
	var height = $(window).height();
	$("#header").css({
		"position" : "absolute",
		"top" : 10,
		"left" : 10,
		"right" : 10
	});
	var header_height = $("#header").height();
	$("#topmenu").css({
		"position" : "absolute",
		"z-index" : 999,
		"top" : header_height + 10,
		"left" : -30
	});
	var menu_height = 24;
	$("#center").css({
		"position" : "absolute",
		"top" : header_height + menu_height + 10,
		"left" : 10,
		"right"	: 10,
		"height" : height - header_height - menu_height - 50
	});
	var center_top = $("#center").offset().top;
	var center_height = $("#center").height();
	$("#footer").css({
		"position" : "absolute",
		"top" : center_top + center_height + 10,
		"left" : 10,
		"right" : 10
	});
	$("#tabs").css({
		"top" : center_top - 90
	});
	$(window).bind("beforeunload", function() {
		return "您将离开本页面或者页面将被重置，所有打开的页面都将被关闭，确认离开或者刷新页面吗？";
	});
});
