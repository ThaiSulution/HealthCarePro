function numericScroll(obj, step, min, max){
	var oldVal = parseInt(obj.value)
	var newVal = oldVal + step;
	if(newVal>=min && newVal<=max){
		obj.value = newVal;
	}
}

function changeTimelines(membertype, targetid, timelinename){
	$.ajax({
		url: "Ajax/Default.aspx?Mod=ChangeTimelines&Type=" + timelinename,
		cache: false,
		success: function(res){
			loadTimelines(membertype, targetid);
		}
	});
}

function loadTimelines(membertype, targetid){
	var strDate = getUrlParameter("Date");
	if(strDate!=""){
		strDate = "&Date=" + strDate;
	}
	
	$.ajax({
		url: "Ajax/Default.aspx?Mod=" + membertype + "Timelines" + strDate,
		cache: false,
		success: function(html){
			$('#'+targetid).html(html);
		}
	});
}
			
function toInt(value){
	if(value!=''){
		if(!isNaN(value))
			return parseInt(value, 10);
		else{
			return 0;
		}
	}
}
function addCommas(nStr)
{
	nStr += '';
	x = nStr.split(',');
	x1 = x[0];
	x2 = x.length > 1 ? ',' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + '.' + '$2');
	}
	return x1 + x2;
}

function toggle(objTrigger, tagertid){
	var src = objTrigger.src.toLowerCase().replace("\\", "/");
	var path = src.substring(0, src.lastIndexOf("/") + 1);
	var tgl = src.substring(src.lastIndexOf("/") + 1);
	
	var objTagert = document.getElementById(tagertid);
	
	if(tgl=="toggle_minus.gif"){
		objTrigger.src = path + "toggle_plus.gif";
		objTrigger.alt = "Hiển thị các lô";
		
		objTagert.style.display = "none";
	}else{
		objTrigger.src = path + "toggle_minus.gif";
		objTrigger.alt = "Ẩn các lô";
		
		objTagert.style.display = "block";
	}
}

function showHelp(name){
	var url = 'Help.aspx?Code=' + name;
	layer=dhtmlwindow.open('layer', 'ajax', url, 'Hướng dẫn', 'width=660px,height=400px,center=1,resize=1,scrolling=1');
	return false;
}
function showAtlas(type, id){
	var url = 'ViewFile.aspx?Mod=ViewImage&Type=' + type + '&ImageID=' + id;
	layer=dhtmlwindow.open('layer', 'ajax', url, 'Hình ảnh giải phẫu - Atlas', 'width=950px,height=500px,center=1,resize=1,scrolling=1');
	return false;
}

function showFormModal(popupid, divid, title, width, height){
	var option = 'width='+width+'px,height=105px,center=1,resize=0,scrolling=0';
	if(height>0){
		option += ',height='+height+'px';
	}
	
	frm=dhtmlmodal.open(popupid, 'div', divid, title, option);
	return false;
}

function hideFormModal(){
	frm.hide();
	return false;
}

function checkReasonForDelete(txtReasonID){
	var txt = document.getElementById(txtReasonID);
	if(trim(txt.value)==''){
		txt.style.backgroundColor='#FDD6C9';
		txt.focus();
		return false;
	}else{
		txt.style.backgroundColor='#ffffff';
	}
	return true;
}

function TotalCost(pattern, targetid){
	var str = ":" + pattern;
	var reg = new RegExp(str);
	var max = document.forms[0].elements.length;
	var i = 0;
	var total = 0;
	while(i<max){
		var obj = document.forms[0].elements[i];
		if(obj.type == "text"){
			if(reg.test(obj.name)){
				if(obj.value!=0){
					obj.value = parseInt(obj.value, 10);
					total = total + parseInt(obj.value, 10);
				}
			}
		}
		i++;
	}
	var lblTarget = document.getElementById(targetid);
	lblTarget.innerHTML = 'Tổng cộng: ' + ConvertToVNNumber(total);
}

function TotalCost2(pattern, tongcongid, giamgiaid, thanhtienid){
	var str = ":" + pattern;
	var reg = new RegExp(str);
	var max = document.forms[0].elements.length;
	var i = 0;
	var total = 0;
	while(i<max){
		var obj = document.forms[0].elements[i];
		if(obj.type == "text"){
			if(reg.test(obj.name)){
				if(obj.value!=0){
					obj.value = parseInt(obj.value, 10);
					total = total + parseInt(obj.value, 10);
				}
			}
		}
		i++;
	}
	var txtTongCong = document.getElementById(tongcongid);
	txtTongCong.value = parseInt(total);
	
	var txtGiamGia = document.getElementById(giamgiaid);
	var intGiamGia = 0;
	if(txtGiamGia.value!=''){
		txtGiamGia.value = parseInt(txtGiamGia.value, 10);
		intGiamGia = parseInt(txtGiamGia.value, 10);
	}
	
	var txtThanhTien = document.getElementById(thanhtienid);
	txtThanhTien.value = ConvertToVNNumber(total * (100 - intGiamGia) / 100);
}
function showPayServiceCost(TongPhiID, GiamGiaID, ThanhTienID){
	var txtTongPhi = document.getElementById(TongPhiID);
	var txtGiamGia = document.getElementById(GiamGiaID);
	var txtThanhTien = document.getElementById(ThanhTienID);
	
	var intTongPhi = 0;
	if(txtTongPhi.value!=''){
		txtTongPhi.value = parseInt(txtTongPhi.value, 10);
		intTongPhi = parseInt(txtTongPhi.value, 10);
	}
	
	var intGiamGia = 0;
	if(txtGiamGia.value!=''){
		txtGiamGia.value = parseInt(txtGiamGia.value, 10);
		intGiamGia = parseInt(txtGiamGia.value, 10);
	}
	
	txtThanhTien.value = ConvertToVNNumber(intTongPhi * (100 - intGiamGia) / 100);
}
function showPatientRegisterCost(PhiKhamBenhID, GiamGiaID, ThanhTienID){
	var txtPhiKhamBenh = document.getElementById(PhiKhamBenhID);
	var txtGiamGia = document.getElementById(GiamGiaID);
	var txtThanhTien = document.getElementById(ThanhTienID);
	
	var intPhiKhamBenh = 0;
	if(txtPhiKhamBenh.value!=''){
		txtPhiKhamBenh.value = parseInt(txtPhiKhamBenh.value, 10);
		intPhiKhamBenh = parseInt(txtPhiKhamBenh.value, 10);
	}
	
	var intGiamGia = 0;
	if(txtGiamGia.value!=''){
		txtGiamGia.value = parseInt(txtGiamGia.value, 10);
		intGiamGia = parseInt(txtGiamGia.value, 10);
	}
	
	txtThanhTien.value = intPhiKhamBenh * (100 - intGiamGia) / 100;
}
function ConvertBoolToBit(obj){
	if(obj==true){
		return 1
	}else{
		return 0
	}
}
function validateVNDate( strValue ) {
/************************************************
DESCRIPTION: Validates that a string contains only
    valid dates with 2 digit month, 2 digit day,
    4 digit year. Date separator can be ., -, or /.
    Uses combination of regular expressions and
    string parsing to validate date.
    Ex. dd/mm/yyyy or dd-mm-yyyy or dd.mm.yyyy

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.

REMARKS:
   Avoids some of the limitations of the Date.parse()
   method such as the date separator character.
*************************************************/
  if(strValue==''){
	return true;
  }
  
  var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
 
  //check to see if in correct format
  if(!objRegExp.test(strValue))
    return false; //doesn't match pattern, bad date
  else{
    var strSeparator = strValue.substring(strValue.length-5,strValue.length-4);
    var arrayDate = strValue.split(strSeparator); 
    //create a lookup for months not equal to Feb.
    var arrayLookup = { '01' : 31,'03' : 31,
						'1' : 31,'3' : 31, 
                        '04' : 30,'05' : 31,
                        '4' : 30,'5' : 31,
                        '06' : 30,'07' : 31,
                        '6' : 30,'7' : 31,
                        '08' : 31,'09' : 30,
                        '8' : 31,'9' : 30,
                        '10' : 31,'11' : 30,'12' : 31}
    var intDay = parseInt(arrayDate[0],10); 

    //check if month value and day value agree
    if(arrayLookup[arrayDate[1]] != null) {
      if(intDay <= arrayLookup[arrayDate[1]] && intDay != 0)
        return true; //found in lookup table, good date
    }
    
    //check for February (bugfix 20050322)
    //bugfix  for parseInt kevin
    //bugfix  biss year  O.Jp Voutat
    var intMonth = parseInt(arrayDate[1],10);
    if (intMonth == 2) { 
       var intYear = parseInt(arrayDate[2]);
       if (intDay > 0 && intDay < 29) {
           return true;
       }
       else if (intDay == 29) {
         if ((intYear % 4 == 0) && (intYear % 100 != 0) || 
             (intYear % 400 == 0)) {
              // year div by 4 and ((not div by 100) or div by 400) -&gt;ok
             return true;
         }   
       }
    }
  }  
  return false; //any other values, bad date
}
function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
		return false;

    return true;
}

function checkItem(item, itemName){
	var str = ":" + itemName;
	var reg = new RegExp(str);
	var max = document.forms[0].elements.length;
	var i = 0;
	var val = item.checked;
	while(i<max){
		var obj = document.forms[0].elements[i];
		if(obj.type == "checkbox"){
			if(reg.test(obj.name)){
				obj.checked = false;
			}
		}
		i++;
	}
	item.checked = val;
}

function validateSelectedCheckBox(itemName, messenger){
	var str = ":" + itemName;
	var reg = new RegExp(str);
	var max = document.forms[0].elements.length;
	var i = 0;
	var val = false;
	while(i<max && !val){
		var obj = document.forms[0].elements[i];
		if(obj.type == "checkbox"){
			if(reg.test(obj.name)){
				if(obj.checked == true){
					val = true;
				}
			}
		}
		i++;
	}
	if(!val){
		alert(messenger);
	}
	return val;
}

// Check if string is a whole number(digits only). 
function isWhole (s) { 
	var isWhole_re = /^\s*\d+\s*$/; 
    return String(s).search (isWhole_re) != -1 
}
// Check if string is non-blank  
function isNonblank (s) { 
	var isNonblank_re = /\S/;
    return String (s).search (isNonblank_re) != -1 
}

function confirmEdit(mes){
	if(confirm(mes)){
		return true;
	}else{
		return false;
	}
}

function confirmDelete(){
	if(confirm("Bạn có chắc chắn muốn xóa không?")){
		return true;
	}else{
		return false;
	}
}

function confirmClear(){
	if(confirm("Tất cả thông tin bạn đang nhập sẽ bị xóa.\nBạn có chắc chắn muốn xóa không?")){
		return true;
	}else{
		return false;
	}
}

function round_currency(a)
{
  
  var b = Math.abs(a);
  
  b = Math.round(b * 10000000.0) / 100000.0;
  
  b = Math.round(b) / 100.0;
  
  b = b * (a >= 0.0 ? 1.0 : -1.0);
  if( b == 0.0 )
	return 0.0;
  return b;
}

function pad_to_atleast_two_decimal_places(a)
{
  var s;
    if(a == null)
    {
       s = "";
    }
  else
  {
    s = a.toString();
    var n = s.indexOf(".");
    if(n == -1)
    {
      //s = s + ".00";
    }
    else if(n == s.length-1)
    {
      //s = s + "00";
      s = s;
    }
    else if(n == s.length-2)
    {
      s = s + "0";
    }
    if (n == 0)
    {
      s = "0" + s;
    }
  }
  return s;
}

function format_currency(a, bDoNotRound)
{
  
  var returnMe;
  if(isNaN(a))
  {
    return "";
  }
  else if( !(bDoNotRound == true))  
  {
        returnMe = round_currency(a);
  }
  else
    returnMe = a;
  returnMe = pad_to_atleast_two_decimal_places(returnMe);
  return returnMe;
}

function onlydigits(str){ 
	var re = new RegExp("([0-9]+)"); 
	return (re.exec(str)!=null && RegExp.$1==str); 
}

function FomatNumber(txtName){
	var sumstr = document.all.item(txtName).value;
	sumstr = format_currency(parseFloat(sumstr));
	sumstr = sumstr.substring(0,10);
	document.all.item(txtName).value = sumstr;
}


// Convert Number to format "###.###"
function ConvertToVNNumber(num){
	num = num.toString().replace(/\$|\./g,'');
	num = String(num).substring(0,10);	
	num = parseFloat(num);
	if(isNaN(num))num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	//cents = num%100;
	//if(cents<10)
	//cents = "0" + cents;
	
	num = Math.floor(num/100).toString();
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
		num = num.substring(0,num.length-(4*i+3))+'.'+ num.substring(num.length-(4*i+3));
	}	
	return ((sign)?'':'-') + num;
}

// Convert Number to format "###,###"
function ConvertToSysNumber(num){
	num = num.toString().replace(/\$|\./g,'');
	return parseFloat(num);
}

// Convert Number to format "###.###,###"
function ConvertToVNCurrency(num){
	var numInteger;
	var numDecimal;	
	num = num.toString().replace(/\$|\,/g,'');
	num = num.toString().replace(/\$|\./g,',');
	return FormatVNCurrency(num)
}

function FormatVNCurrency(num){
	var numInteger;
	var numDecimal;
	num = num.toString().replace(/\$|\./g,'');
	num = num.toString().replace(/\$|\,/g,'.');
	//num = String(num).substring(0,10);	
	num = parseFloat(num);
	if(isNaN(num))num = "0";
	sign = (num == (num = Math.abs(num)));
	
	if (num.toString().indexOf('.')>0){
		var array = num.toString().split(".");
        numInteger = array[0];
        numDecimal = array[1];	
	}else{
        numInteger = num;
        numDecimal = '';	
	}      
	numInteger = parseInt(numInteger);
	
	numInteger = Math.floor(numInteger*100+0.50000000001);
	numInteger = Math.floor(numInteger/100).toString();
	for (var i = 0; i < Math.floor((numInteger.length-(1+i))/3); i++){
		numInteger = numInteger.substring(0,numInteger.length-(4*i+3))+'.'+ numInteger.substring(numInteger.length-(4*i+3));
	}
	if (numDecimal != ''){
		return (numInteger + ',' + numDecimal);
	
	}else{
	
		return (numInteger);
	}

}

// Convert Number to format "###,###"
function ConvertToSysCurrency(num){
	num = num.toString().replace(/\$|\./g,'');
	num = num.toString().replace(/\$|\,/g,'.');
	return parseFloat(num);
}

function FormatNumber(txtName){
	var num = document.all.item(txtName).value;	
	document.all.item(txtName).value = ConvertToVNNumber(num);	
}

function FormatCurrency(txtName){
	var num = document.all.item(txtName).value;	
	document.all.item(txtName).value = FormatVNCurrency(num);	
}

function CheckTypeOfByte(txtFieldName){
	var fValue = 1;
	var strValue = document.all.item(txtFieldName).value;
	strValue = format_currency(parseFloat(strValue));
	strValue = strValue.substring(0,10);
	if ((strValue.length > 0)&&(parseFloat(strValue) > 0))
		document.all.item(txtFieldName).value = strValue
	else{
		document.all.item(txtFieldName).value = "";
		alert("Giá trị phải là số dương lớn hơn 0");
	}
}

function NotEmpty(txtName){
	var sumstr = document.all.item(txtName).value;
	if (sumstr == ''){
		alert('Chưa nhập dữ liệu');
		document.all.item(txtName).focus();
		return false;
	}else{
		document.all.item(txtName).value = sumstr;	
		return true;
	}	
}

function validate_field(field,type,doalert,mandatory)
{
	type = type.toLowerCase();
    if (field.value == null || field.value.length == 0){
		if (mandatory == true){
		    if (doalert) alert("Chưa nhập dữ liệu ");
		    field.focus();
		    //field.select();
			//window.isvalid = false;
			return false;
		}
		else{
		    //window.isvalid = true;
		    return true;
		}
    }
    var validflag = true;
    if (type == "date")
    {
        validflag = true;
        var m=0,d=0,y=0, val=field.value;
        var fmterr;
        var year="";

        if (val.indexOf("/") != -1) {
                var c = val.split("/");
                if(onlydigits(c[0])) d = parseInt(c[0],10);
                if(onlydigits(c[1])) m = parseInt(c[1],10);
                if(onlydigits(c[2])) y = parseInt(c[2],10);
                year=c[2];
        }
        else{
                //var l = val.length, str;
                //str = val.substr(0,2-l%2); if(onlydigits(str)) m = parseInt(str,10);
                //str = val.substr(2-l%2,2); if(onlydigits(str)) d = parseInt(str,10);
                //str = val.substr(4-l%2);   if(onlydigits(str)) y = parseInt(str,10);
                //year=str;
        }
        fmterr = "Ngày/Tháng/Năm";
        if(m==0 || d==0)
        {
            if (doalert) alert("Ngày không hợp lệ, phải là : "+fmterr+"");
            field.value = "";
            field.focus();
		    //field.select();

            validflag = false;

        }
        else
        {
			if (y==0 && !onlydigits(year)) y = (new Date()).getYear();  
            if(m<1) m=1; else if(m>12) m=12;
            
            if(d<1) d=1; else if(d>31) d=31;
            
            switch (m){
				case 1 : 
				case 3 :
				case 5 :
				case 7 :
				case 8 :
				case 10 :
				case 12 :
				{
					if(d>31) d=31;
					break;
				}
				case 6 :
				case 9 :
				case 11 : 
				case 4 : {
					if(d>30) d=30;
					break;
				}
				case 2 : {
					if(d>28) d=28;
					break;
				}
			}
			
            if(y<100) y+=((y>=70)?1900:2000);
            if(y<1000) y*=10;
            if (y > 9999) y = (new Date()).getYear();
            if(d <= 9){ d = '0'+ d;}
            if(m <= 9){m = '0'+ m;}        
            field.value = d +'/'+ m + '/'+ y ;//getdatestring(new Date(y,m-1,d));
        }
    }
    else if (type =="number"){
			var sumstr = field.value;
			//alert(parseFloat(sumstr));
			//sumstr = format_currency(parseFloat(sumstr));
			sumstr = format_currency(sumstr);
			field.value = sumstr;
			if (field.value == null || field.value.length == 0){
				if (mandatory == true){
					if (doalert) alert("Chưa nhập dữ liệu ");
					field.focus();
					return false;
				}
				else{
					return true;
				}
			}
			
    }    
    else if (type =="decimal"){
			var sumstr = field.value;
			//alert(parseFloat(sumstr));
			num = sumstr.toString().replace(/\$|\,/g,'');
			num = sumstr.toString().replace(/\$|\,/g,'');
			//sumstr = format_currency(parseFloat(sumstr));
			sumstr = format_currency(sumstr,true);
			field.value = sumstr;
			if (field.value == null || field.value.length == 0){
				if (mandatory == true){
					if (doalert) alert("Chưa nhập dữ liệu ");
					field.focus();
					return false;
				}
				else{
					return true;
				}
			}
			
    }    
        
    else if (type =="currency"){
        var val = field.value.replace(/\$/g,"");
        val = field.value.replace(/\ /g,"");
				val = val.replace(/,/g,"");
        val = val.toLowerCase();
        if(val.charAt(0) == '=') val = val.substr(1);
        if (val.substr(1).search(/[\+\-\*\/]/g) != -1){
            var c = val.charAt(0);
            if(val.charAt(0) >='a' && val.charAt(0) <='z')
            {
                //value = "error";
            }
            else
            {
		    //    try {
            //      val = eval(val);
			//	} catch (e) { val = "error"; }
                //autoplace = false;
            }
        }
        numval = parseFloat(val);
        if (isNaN(numval) || Math.abs(numval)>=1.0e+12){
            if (doalert) alert("Giá trị không hợp lệ, phải nhỏ hơn 999.999.999.999,99");
            validflag = false;

        }
        else {
			//if(autoplace && val.indexOf(".") == -1) numval/=100;
			field.value = format_currency(numval);
			validflag = true;
        }
    }
    else if (type == "time" || type == "timetrack"){
        var hours;
        var minutes;

        var re = /([0-9][0-9]?)?(:[0-5][0-9])?/
        var result = re.exec(field.value)
        if (result==null || result.index > 0 || result[0].length != field.value.length){
            timeval = parseFloat(field.value);
            if (isNaN(timeval))
                hours = -1;
            else{
                hours = Math.floor(timeval);
                minutes = Math.floor((timeval-hours)*60+0.5);
            }
        }
        else{
            if (RegExp.$1.length > 0)
                hours = parseInt(RegExp.$1,10);
            else
                hours = 0;
            if (typeof(RegExp.$2) != "undefined" && RegExp.$2.length > 0)
                minutes = parseInt(RegExp.$2.substr(1),10);
            else
                minutes = 0;
        }
        if (hours >= 0 && minutes >= 0 && minutes < 60){
            field.value = hours + ":" + (minutes < 10 ? "0" : "") + minutes;
            validflag = true;
        }
        else{
            if (doalert) alert("Thời gian không hợp lệ, phải là (giờ:phút)");
            validflag = false;
        }
    }
    return validflag;
}
function openImageNews(vLink, vHeight, vWidth)
{
	var sLink = (typeof(vLink.href) == 'undefined') ? vLink : vLink.href;

	if (sLink == '')
	{
		return false;
	}

	winDef = ""
	winDef = winDef.concat('height=').concat(vHeight).concat(',width=').concat(vWidth)
	winDef = winDef.concat(',resizable=yes,scrollbars=no,menubar=no,toolbar=no,status=no,location=no,directories=no,alwaysRaised=yes')
	winDef = winDef.concat(',top=').concat((screen.height - vHeight)/2).concat(',');
	winDef = winDef.concat('left=').concat((screen.width - vWidth)/2);
	newwin = open('', '_blank', winDef);

	newwin.document.writeln('<title>Anh minh hoa</title><body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">');
	newwin.document.writeln('<a href="" onClick="window.close(); return false;"><img src="', sLink, '" alt="', 'Dong lai', '" border=0></a>');
	newwin.document.writeln('</body>');

	if (typeof(vLink.href) != 'undefined')
	{
		return false;
	}
}
function submitenter(myButton,e)
{
	var keycode;
	if (window.event) 
		keycode = window.event.keyCode;
	else if (e) 
		keycode = e.which;
	else 
		return true;

	if (keycode == 13)
	{
		document.getElementById(myButton).click();
		return false;
	}else
		return true;
}
function changeClass(tab, myClass) {
	tab.className = myClass;
}
function trim(str){
	var trimmed = str.replace(/^\s+|\s+$/g, '') ;
	return trimmed;
}
function checkSearch(inputText){
	var strKeyword = trim(document.getElementById(inputText).value);
	if(strKeyword.length>=2){
		return true;
	}else{
		alert('Bạn phải nhập tối thiểu 2 ký tự để tìm kiếm');
		return false;
	}		
}
function addBookmark(title, url) { 
    if (window.sidebar) {
        // mozilla/firefox     
        window.sidebar.addPanel(title, url,"");    
    } else if( window.external ) {
        // IE        
        window.external.AddFavorite( url, title); 
    } else if(window.opera && window.print) {
        // Opera        
        var bookmark = document.createElement('a');
        bookmark.setAttribute('href',url);
        bookmark.setAttribute('title',title);
        bookmark.setAttribute('rel','sidebar');
        bookmark.click(); 
    } 
}

function getBrowserSize() {
	var myWidth = 0, myHeight = 0;
	if( typeof( window.innerWidth ) == 'number' ) {
		//Non-IE
		myWidth = window.innerWidth;
		myHeight = window.innerHeight;
	} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		//IE 6+ in 'standards compliant mode'
		myWidth = document.documentElement.clientWidth;
		myHeight = document.documentElement.clientHeight;
	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		//IE 4 compatible
		myWidth = document.body.clientWidth;
		myHeight = document.body.clientHeight;
	}
	return new Array(myWidth, myHeight);
}

function isDigitCode(code)
{
    if (code > 31 && (code < 48 || code > 57)){
		return false;
    }else{
	    return true;
	}
}

function isVNDate(obj) {
	if(!validateVNDate(obj.value)){
		alert('Ngày không hợp lệ!'); 
		obj.value='';
	}
}

function isEmpty(s) { 
	var isNonblank_re = /\S/;
    return !(String (s).search (isNonblank_re) != -1);
}

function isNumber(s) { 
	var isDigits_re = /^\s*\d+\s*$/; 
    return String(s).search (isDigits_re) != -1 
}
