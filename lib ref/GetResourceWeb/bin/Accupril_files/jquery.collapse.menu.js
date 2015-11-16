
		var showImage = 'images/ui/collapsed.png';
		var showText = 'Hiển thị';
		//var hideImage = 'images/ui/up.gif';
		var hideImage = 'images/ui/collapse.png';
		var hideText = 'Ẩn';
		
		$(document).ready(function() {
			//show or hide link when hover
			$('.toggle').mouseover(function() {
				$(this).find('div.link').show();
				$(this).addClass('current');
			}).mouseout(function() {
				$(this).find('div.link').hide();
				$(this).removeClass('current');
			});
			
			//trigger click on link
			$('.link img').click(function() {
				if($.cookie(this.id)=='hide'){
					$.cookie(this.id, 'show');
					$('#SubMenu_' + this.id).css("display","block");
					$(this).attr('src', hideImage);
					$(this).attr('alt', hideText);
				}else{
					$.cookie(this.id, 'hide');
					$('#SubMenu_' + this.id).css("display","none");
					$(this).attr('src', showImage);
					$(this).attr('alt', showText);
				}
			});
			
			//init menu
			$('.link img').each(function(index) {
				if($.cookie(this.id)=='hide'){
					$('#SubMenu_' + this.id).css("display","none");
					$(this).attr('src', showImage);
					$(this).attr('alt', showText);
				}else{
					$('#SubMenu_' + this.id).css("display","block");
					$(this).attr('src', hideImage);
					$(this).attr('alt', hideText);
				}
			});
			
		});
