// Little hack to dock an element to the top of the screen
$.fn.dock = function() {
	// wrap the element
	if (!this.length) {
		return;
	}
	var $el = $(this[0]),
		el_ypos = $el.offset().top,
		el_height = $el.height(),
		el_width = $el.width(),
		el_xpos = $el.offset().left,
		is_docked = false
	
	$(window).scroll(function() {
		var ypos = $(document).scrollTop();
		if (is_docked && ypos < el_ypos) {
			is_docked = false;
			if($el.hasClass('toolbar-fixed')) {
				$el.removeClass('toolbar-fixed');
				$el.css({padding: '0px 0px 0px 0px'});
			} 
		} else if (!is_docked && ypos > el_ypos) {
			is_docked = true;
			$el.addClass('toolbar-fixed');
			$el.css({padding: '0px 0px 0px ' + el_xpos + 'px'});
		}
	});
}

jQuery(document).ready(function($) {
		// Keep the seciton tabs docked to the top of the screen
	$('.toolbar').dock();
});