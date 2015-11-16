function collapsePanel(){
	if($.cookie('CollapsePanel')=='hide'){
		changeClass($("#rightPanel"), 'rightPanel-show', 'rightPanel-hide');
		changeClass($("#leftPanel"), 'leftPanel-show', 'leftPanel-hide');
	}else{
		changeClass($("#rightPanel"), 'rightPanel-hide', 'rightPanel-show');
		changeClass($("#leftPanel"), 'leftPanel-hide', 'leftPanel-show');
	}
}

function changeClass(obj, oldClass, newClass){
	obj.removeClass(oldClass);
	obj.addClass(newClass);
}

$(document).ready(function() {
	$('.togglemenu').click(function(){
		if($.cookie('CollapsePanel')=='hide'){
			$.cookie('CollapsePanel', 'show');
		}else{
			$.cookie('CollapsePanel', 'hide');
		}
		
		collapsePanel();
		
		var mod = getUrlParameter('Mod');
		if(mod.toUpperCase()=='CALENDAR'){
			$('#calendar').fullCalendar('windowResize');
		}
	});
	
	collapsePanel();
});