var tab_counter = 1;
var tab_close_counter = 0;
var tabTitle = "tabs-";
var tabTemplate = "<li><a href='#{href}'>#{label}</a><span class='ui-icon ui-icon-close'>Remove Tab</span></li>";
var tabContent = "<div id='#{id}'><iframe src='#{src}' marginheight='0' marginwidth='0' frameborder='0' scrolling='no'></iframe></div>";
var tabs;
function addTab(title, url) {
	var id = tabTitle + tab_counter;
	var li = $(tabTemplate.replace(/#\{href\}/g, "#" + id).replace(/#\{label\}/g, title));
	tabs.find(".ui-tabs-nav").append(li);
	var div = $(tabContent.replace(/#\{id\}/g, id).replace(/#\{src\}/g, url));
	tabs.append(div);
    tabs.tabs("refresh");
	$(div).find("iframe").height($("#tabs").height()-40).width($("#tabs").width()-10);
	tabs.tabs("option", "active", tab_counter - tab_close_counter);
	tab_counter++;
}
$(function() {
	tabs = $("#tabs").tabs();
	tabs.delegate( "span.ui-icon-close", "click", function() {
		var id = $(this).closest("li").remove().attr("aria-controls");
	    $("#" + id).remove();
	    tabs.tabs("refresh");
	    tab_close_counter++;
	});
});
