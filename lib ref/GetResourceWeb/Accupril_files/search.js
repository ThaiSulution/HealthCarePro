//////////////////////////////////////////////
//Author: Vu Minh Tuan
//Email : vmt47@yahoo.com
//Website: http://thuoc.vn
//////////////////////////////////////////////
$('document').ready(function(){
	//init
	initSearchBox();
	
	//autocomplete
	initAC();
	
	//tab filter click
	$('#searchbox .filter a').click(function (){
		showFilter($(this).attr("rel"));
		$('#searchbox .control .keyword #q').focus();
		
		//autocomplete change url
		changeACSource();
	});
	
	//set focus when option is checked
	$('#searchbox .option input').click(function (){
		$('#searchbox .control .keyword #q').focus();
		
		//autocomplete change url
		changeACSource();
	});
	
	//search button click
	$('#searchbox .control .search #go').click(function (){
		var keyword = $('#searchbox .control .keyword #q').val();
		keyword = encodeURIComponent(trim(keyword));
		
		if(keyword.length>=2){
			var url = buildSearchUrl(keyword);
			window.location.href = url;
		}else{
			alert(keyword + ': Từ tìm kiếm phải có ít nhất từ 2 ký tự trở lên!');
			$('#searchbox .control .keyword #q').focus();
		}
	});
	
	//enter keypress
	$('#searchbox .control .keyword #q').keypress(function(event) {
		if ( event.which == 13 ) {
			event.preventDefault();
			$('#searchbox .control .search #go').focus().click();
		}
	});
});

//init autocomplete
function initAC(){
	//var filter = getFilterName();
	
	$('#searchbox .control .keyword #q').autocomplete(buildACUrl(), {
		selectFirst: false,
		formatItem: function(row) {
			if(row[1]!=''){
				return "<b>" + row[0] + "</b><br><i>" + row[1] + "</i>";
			}else{
				return row[0];
			}
		},
		formatResult: function(row) {
			return row[0];
		}
	});
}

//change autocomplete source
function changeACSource(){
	$('#searchbox .control .keyword #q').setOptions({
		url: buildACUrl()
	});
}

function buildACUrl(){
	var query = '';
	var filter = getFilterName();
	var option = $('input[name=o' + filter + ']:checked').val();
	
	if(filter=='fd'){
		if(option=='TT'){
			query = '?Mod=CheckInteraction';
		}else{
			query = '?Mod=ViewSearch&Type=' + option;
		}
	}else if(filter=='fa'){
		switch(option){
			case 'SEARCHARTICLES':
				query = '?Mod=SearchArticles';
				break;
			case 'SEARCHVIDEO':
				query = '?Mod=SearchVideo';
				break;
			case 'SEARCHICD':
				query = '?Mod=SearchICD';
				break;
			case 'SEARCHSERVICE':
				query = '?Mod=SearchService';
				break;
			case 'SEARCHTESTINDEX':
				query = '?Mod=SearchTestIndex';
				break;
			default:
				query = '?Mod=SearchArticles';
				break;
		}
	}else if(filter=='fi'){
		query = '?Mod=SearchImage&Type=' + option;
	}
	
	return 'Suggestion.aspx' + query;
}

function buildSearchUrl(keyword){
	var query = '';
	var filter = getFilterName();
	var option = $('input[name=o' + filter + ']:checked').val();
	
	if(filter=='fh'){
		query = '?Mod=ViewHealthProfiles&Code=' + keyword;
	}else if(filter=='fd'){
		if(option=='TT'){
			query = '?Mod=CheckInteraction&Keyword=' + keyword;
		}else{
			query = '?Mod=ViewSearch&Type=' + option + '&Keyword=' + keyword;
		}
	}else if(filter=='fa'){
		switch(option){
			case 'SEARCHARTICLES':
				query = '?Mod=SearchArticles&Keyword=' + keyword;
				break;
			case 'SEARCHVIDEO':
				query = '?Mod=SearchVideo&Keyword=' + keyword;
				break;
			case 'SEARCHICD':
				query = '?Mod=SearchICD&Keyword=' + keyword;
				break;
			case 'SEARCHSERVICE':
				query = '?Mod=SearchService&Keyword=' + keyword;
				break;
			case 'SEARCHTESTINDEX':
				query = '?Mod=SearchTestIndex&Keyword=' + keyword;
				break;
			default:
				query = '?Mod=SearchArticles&Keyword=' + keyword;
				break;
		}
	}else if(filter=='fi'){
		query = '?Mod=SearchImage&Type=' + option + '&Keyword=' + keyword;
	}
	
	return 'Default.aspx' + query;
}

function initSearchBox(){
	var strMod = getUrlParameter('Mod').toUpperCase();
	var strType = getUrlParameter('Type').toUpperCase();
	
	var strKeyword = getUrlParameter('Keyword');
	strKeyword = decodeURIComponent(trim(strKeyword));
	
	$('#searchbox .control .keyword #q').val(strKeyword);
	
	$('#searchbox .dbinfo').html(getDataCounter);
	
	var filter = '';
	switch(strMod){
		case 'SEARCHARTICLES':
		case 'SEARCHVIDEO':
		case 'VIEWVIDEO':
		case 'SEARCHICD':
		case 'SEARCHSERVICE':
		case 'SEARCHTESTINDEX':
		case 'ICD':
		case 'HEALTHSERVICE':
		case 'VIEWVIDEO':
		case 'VIEWARTICLES':
			filter = 'fa';
			break;
		case 'VIEWHEALTHPROFILES':
			filter = 'fh';
			break;	
		case 'SEARCHIMAGE':
		case 'VIEWIMAGE':
			filter = 'fi';
			break;
		default:
			filter = 'fd';
			break;
	}
	
	//set option
	var option = '';
	if(filter=='fd'){
		if(strMod=='CHECKINTERACTION' || strMod=='VIEWINTERACTION'){
			option = 'TT';
		}else{
			if(strType.length>0){
				option = strType;
			}else{
				option = getOptionDefault(filter);
			}
		}
	}
	
	if(filter=='fa'){
		if(strMod.length>0){
			if(strMod=='HEALTHSERVICE'){
				option = 'SEARCHSERVICE';
			}else if(strMod=='ICD'){
				option = 'SEARCHICD';
			}else if(strMod=='VIEWVIDEO'){
				option = 'SEARCHVIDEO';
			}else if(strMod=='SEARCHTESTINDEX'){
				option = 'SEARCHTESTINDEX';
			}else{
				option = strMod;
			}
		}else{
			option = getOptionDefault(filter);
		}
	}
	
	if(filter=='fi'){
		if(strType.length>0){
			option = strType;
		}else{
			option = getOptionDefault(filter);
		}
	}
    
	$("#searchbox .option input:radio").filter('[value=' + option + ']').attr('checked', true);
	
	showFilter(filter);
}

function getOptionDefault(filter){
	var option = 'TN';
	switch(filter){
		case 'fd':
			option = 'TN';
			break;
		case 'fa':
			option = 'SEARCHARTICLES';
			break;
		case 'fi':
			option = 'ATLAS';
			break;
	}
	
	return option;
}

function showFilter(filter){
	$('#searchbox .filter a').each(function(i){
		$(this).removeClass("active");
		
		var rel = $(this).attr("rel");
		if(rel==filter){
			$(this).addClass("active");
			
			$('#searchbox .option div').each(function(i){
				$(this).removeClass("show");
				
				if(rel==this.id){
					$(this).addClass("show");
				}
			});
			
			//set option
			if($('input[name=o' + filter + ']').is(':checked') === false) {
				$('input[name=o' + filter + ']').filter('[value=' + getOptionDefault(filter) + ']').attr('checked', true);
			}
		}
	});
}

function getFilterName(){
	var filter = '';
	$('#searchbox .filter a').each(function(i){
		if($(this).hasClass("active")){
			filter = $(this).attr("rel");
		}
	});
	
	return filter;
}

function getUrlParameter( name ){  
	var regexS = "[\\?&]"+name+"=([^&#]*)";  
	var regex = new RegExp( regexS );  
	var tmpURL = window.location.href;  
	var results = regex.exec( tmpURL );  
	if( results == null )    
		return "";  
	else    
		return results[1];
}
