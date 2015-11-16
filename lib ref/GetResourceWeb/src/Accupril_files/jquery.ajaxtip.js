$(function()
{
	var hideDelay = 500;  
	var hideTimer = null;
	var strMod = getUrlParameter('Mod').toUpperCase();
	if(strMod=="") strMod = "NEWS";
  
	// One instance that's reused to show info for the current person
	var container = $('<div id="ajaxTipContainer">'
		+ '<table width="" border="0" cellspacing="0" cellpadding="0" align="center" class="ajaxTipPopup">'
		+ '<tr>'
		+ '   <td class="corner topLeft"></td>'
		+ '   <td class="top"></td>'
		+ '   <td class="corner topRight"></td>'
		+ '</tr>'
		+ '<tr>'
		+ '   <td class="left">&nbsp;</td>'
		+ '   <td><div id="ajaxTipContent"></div></td>'
		+ '   <td class="right">&nbsp;</td>'
		+ '</tr>'
		+ '<tr>'
		+ '   <td class="corner bottomLeft">&nbsp;</td>'
		+ '   <td class="bottom">&nbsp;</td>'
		+ '   <td class="corner bottomRight"></td>'
		+ '</tr>'
		+ '</table>'
		+ '</div>');

	$('body').append(container);

	$('.ajaxTipTrigger').live('mouseover', function()
	{
		var toolTipID = $(this).attr('rel');
		
		// If no id in url rel tag, don't popup blank
		if (toolTipID == '')
			return;

		if (hideTimer)
			clearTimeout(hideTimer);

		var pos = $(this).offset();
		var width = $(this).width();
	    
		var left = pos.left + width;
		var top = pos.top - 5;
		
		if($(document).width()<left+container.width() && pos.left - container.width() > 0){
			left = pos.left - container.width();
		}
		
		/*
		var windowsWidth = $(document).width();
		var containerWidth = container.width();
		var offsetWidth = windowsWidth - (containerWidth + left);
	    
		if(offsetWidth < 0){
			left = left + offsetWidth;
		}
	    
		var windowsHeight = $(document).height();
		var containerHeight = container.height();
		var offsetHeight = windowsHeight - (containerHeight + top);
	    
		if(offsetHeight < 0){
			top = top + offsetHeight;
		}
	    
		if (left<0) left = 0;
		if (top<0) top = 0;
		*/
		
		container.css({
			left: left + 'px',
			top: top + 'px'
		});

		$('#ajaxTipContent').html('<center><img src="images/loading_2.gif" alt="loading.."><br><i>Đang tải dữ liệu</i></center>');

		$.ajax({
			type: 'GET',
			url: 'Ajax/Default.aspx',
			data: 'Mod=' + strMod + '&ToolTipID=' + toolTipID,
			cache: false,
			success: function(data)
			{
				// Verify requested ID is this ID since we could have multiple ajax
				// requests out if the server is taking a while.
				var id = $(data).filter('.ajaxTipID').html();
				if (toolTipID == id)
				{                  
					var text = $(data).filter('.ajaxTipResult').html();
					$('#ajaxTipContent').html(text);
				}
			}
		});

		container.css('display', 'block');
	});
  
	$('.ajaxTipTrigger').live('mouseout', function()
	{
		if (hideTimer)
			clearTimeout(hideTimer);
		hideTimer = setTimeout(function()
		{
			container.css('display', 'none');
		}, hideDelay);
	});
  
	// Allow mouse over of details without hiding details
	$('#ajaxTipContainer').mouseover(function()
	{
		if (hideTimer)
			clearTimeout(hideTimer);
	});

	// Hide after mouseout
	$('#ajaxTipContainer').mouseout(function()
	{
		if (hideTimer)
			clearTimeout(hideTimer);
		hideTimer = setTimeout(function()
		{
			container.css('display', 'none');
		}, hideDelay);
	});
});