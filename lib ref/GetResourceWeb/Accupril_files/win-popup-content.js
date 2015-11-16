//REQUIRESERVICES
function searchBrowserInService(hospitalizationcodeid, keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var txtHospitalizationCode = document.getElementById(hospitalizationcodeid);
	
	searchBrowserInServiceByFirstChar(txtHospitalizationCode.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchBrowserInServiceByFirstChar(hospitalizationcode, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=BrowserInServiceByChar&HospitalizationCode=' + hospitalizationcode + '&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectBrowserInService(chk){
	var url = 'Ajax/Default.aspx?Mod=SelectBrowserInService&ServiceID=' + chk.value + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditBrowserInService(serviceid, name){
	var chk = document.getElementById('chk_' + serviceid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + serviceid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectBrowserInService&ServiceID=' + serviceid + '&Function=';
	if(trim(txtSoLuong.value)!=''){
		chk.checked = true;
		url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearBrowserInService(){
	var url = 'Ajax/Default.aspx?Mod=SelectBrowserInService&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptBrowserInService(hospitalizationcode){
	var url = 'Ajax/Default.aspx?Mod=SelectBrowserInService&Function=ACCEPT';
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=RequireServices&HospitalizationCode=' + hospitalizationcode;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//REQUIREHEALTHSERVICE
function searchDoctorServiceBrowserForRequireHealthService(hospitalizationcodeid, keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var txtHospitalizationCode = document.getElementById(hospitalizationcodeid);
	
	searchDoctorServiceBrowserForRequireHealthServiceByChar(txtHospitalizationCode.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchDoctorServiceBrowserForRequireHealthServiceByChar(hospitalizationcode, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=DoctorServiceBrowserForRequireHealthServiceByChar&HospitalizationCode=' + hospitalizationcode + '&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function loadServicesForRequireHealthService(objService, targetid, func, hospitalizationcode){
	var parentid = objService.value;
	if(parentid>0){
		var url = 'Ajax/Default.aspx?Mod=ServicesInGroup&HospitalizationCode=' + hospitalizationcode + '&GroupID=' + parentid + '&Function=' + func;
		AjaxRequest.get(
		{
			'url':url
			,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
			,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}else{
		document.getElementById(targetid).innerHTML='';
	}
}
function SelectDoctorServiceBrowserForRequireHealthService(chk, serviceid){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorServiceBrowserForRequireHealthService&ServiceID=' + serviceid + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearDoctorServiceBrowserForRequireHealthService(){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorServiceBrowserForRequireHealthService&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptDoctorServiceBrowserForRequireHealthService(hospitalizationcode){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorServiceBrowserForRequireHealthService&Function=ACCEPT';
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=RequireHealthService&HospitalizationCode=' + hospitalizationcode;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//END REQUIREHEALTHSERVICE

//REQUIRE PRESCRIPTION
function searchDoctorDrugBrowserForRequirePrescription(hospitalizationcodeid, propertyid, optionid, keywordid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	var txtHospitalizationCode = document.getElementById(hospitalizationcodeid);
	searchDoctorDrugBrowserForRequirePrescriptionByChar(txtHospitalizationCode.value, ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchDoctorDrugBrowserForRequirePrescriptionByChar(hospitalizationcode, property, option, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=DoctorDrugBrowserForRequirePrescriptionByChar&HospitalizationCode=' + hospitalizationcode + '&FChar=' + character;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditDoctorDrugBrowserForRequirePrescription(drugid, name){
	var chk = document.getElementById('chk_' + drugid);
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorDrugBrowserForRequirePrescription&DrugID=' + drugid + '&Function=';
	if(trim(txtCachDung.value)!='' || trim(txtSoLuong.value)!=''){
		chk.checked = true;
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}else{
			url = url + 'EDIT&Name=CachDung&Value=' + Url.encode(trim(txtCachDung.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectDoctorDrugBrowserForRequirePrescription(drugid, defaultCachDung){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorDrugBrowserForRequirePrescription&DrugID=' + drugid + '&Function=';
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var chk = document.getElementById('chk_' + drugid);
	if(chk.checked==true){
		url = url + 'ADD';
		var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
		if(trim(txtCachDung.value)==''){
			txtCachDung.value = defaultCachDung;
		}
		if(trim(txtCachDung.value)!=''){
			url = url + '&CachDung=' + Url.encode(trim(txtCachDung.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtCachDung.value)==trim(defaultCachDung)){
			txtCachDung.value = '';
		}
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearDoctorDrugBrowserForRequirePrescription(){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorDrugBrowserForRequirePrescription&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptDoctorDrugBrowserForRequirePrescription(hospitalizationcode){
	var url = 'Ajax/Default.aspx?Mod=SelectDoctorDrugBrowserForRequirePrescription&Function=ACCEPT';
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=RequirePrescription&HospitalizationCode=' + hospitalizationcode;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//END REQUIRE PRESCRIPTION

//MARKETING
function doSearchMarkDrugs(pageid, propertyid, optionid, keywordid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	searchMarkDrugs(txtPage.value, ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchMarkDrugs(page, property, option, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyMarkDrugsByChar&Page=' + page + '&FChar=' + character;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditMarkDrug(drugid, name, page){
	var chk = document.getElementById('chk_' + drugid);
	var txtGhiChu = document.getElementById('txtGhiChu_' + drugid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectMarkDrug&Page=' + page + '&DrugID=' + drugid + '&Function=';
	if(trim(txtGhiChu.value)!='' || trim(txtSoLuong.value)!=''){
		chk.checked = true;
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}else{
			url = url + 'EDIT&Name=GhiChu&Value=' + Url.encode(trim(txtGhiChu.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectMarkDrug(drugid, defaultGhiChu, page){
	var url = 'Ajax/Default.aspx?Mod=SelectMarkDrug&Page=' + page + '&DrugID=' + drugid + '&Function=';
	var txtGhiChu = document.getElementById('txtGhiChu_' + drugid);
	var chk = document.getElementById('chk_' + drugid);
	if(chk.checked==true){
		url = url + 'ADD';
		var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
		if(trim(txtGhiChu.value)==''){
			txtGhiChu.value = defaultGhiChu;
		}
		if(trim(txtGhiChu.value)!=''){
			url = url + '&GhiChu=' + Url.encode(trim(txtGhiChu.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtGhiChu.value)==trim(defaultGhiChu)){
			txtGhiChu.value = '';
		}
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearMarkDrug(page){
	var url = 'Ajax/Default.aspx?Mod=SelectMarkDrug&Function=CLEAR&Page=' + page;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptMarkDrug(page){
	var url = 'Ajax/Default.aspx?Mod=SelectMarkDrug&Function=ACCEPT&Page=' + page;
	
	var returnUrl = 'Default.aspx?Mod=';
	switch(page){
		case 'INVOICE':
			returnUrl = returnUrl + 'Invoice';
			break;
		default:
			returnUrl = returnUrl + 'NOPAGE';
			break;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=returnUrl;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//END MARKETING
//PHARMACY NEW
//PRINTBARCODE
//PRODUCT
function searchPharmProductBrowserForPrintBarcode(keywordid, groupid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmProductBrowserForPrintBarcodeByFirstChar(Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmProductBrowserForPrintBarcodeByFirstChar(keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmProductBrowserForPrintBarcodeByChar&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//DRUG
function searchPharmDrugBrowserForPrintBarcode(propertyid, keywordid, groupid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtKeyword = document.getElementById(keywordid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmDrugBrowserForPrintBarcodeByFirstChar(ddlProperty.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmDrugBrowserForPrintBarcodeByFirstChar(property, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmDrugBrowserForPrintBarcodeByChar&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditPharmProductBrowserForPrintBarcode(productid, packageid, producttype, name){
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var txtDonGiaBan = document.getElementById('txtDonGiaBan_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForPrintBarcode&ProductID=' + productid + '&ProductType=' + producttype + '&PackageID=' + packageid + '&Function=';
	if(trim(txtSoLuong.value)!='' || trim(txtDonGiaBan.value)!=''){
		chk.checked = true;
		if(name!='SOLUONG' && trim(txtSoLuong.value)==''){
			txtSoLuong.value='1';
		}
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}
		if(name=='DONGIABAN'){
			url = url + 'EDIT&Name=DonGiaBan&Value=' + Url.encode(trim(txtDonGiaBan.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectPharmProductBrowserForPrintBarcode(productid, packageid, producttype, dongiaban){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForPrintBarcode&ProductID=' + productid + '&ProductType=' + producttype + '&PackageID=' + packageid + '&Function=';
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var txtDonGiaBan = document.getElementById('txtDonGiaBan_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	if(chk.checked==true){
		url = url + 'ADD';
		if(trim(txtSoLuong.value)==''){
			txtSoLuong.value = '1';
		}
		if(trim(txtDonGiaBan.value)==''){
			txtDonGiaBan.value = dongiaban;
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
		if(trim(txtDonGiaBan.value)!=''){
			url = url + '&DonGiaBan=' + trim(txtDonGiaBan.value);
		}
	}else{
		url = url + 'REMOVE';
		txtSoLuong.value = '';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearPharmProductBrowserForPrintBarcode(){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForPrintBarcode&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptPharmProductBrowserForPrintBarcode(){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForPrintBarcode&Function=ACCEPT';
	
	var returnUrl = 'Default.aspx?Mod=PrintBarcode';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=returnUrl;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

//INVENTORY
//PRODUCT
function searchPharmProductBrowserForInventory(pageid, keywordid, groupid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmProductBrowserForInventoryByFirstChar(txtPage.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmProductBrowserForInventoryByFirstChar(page, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmProductBrowserForInventoryByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//DRUG
function searchPharmDrugBrowserForInventory(pageid, propertyid, optionid, keywordid, groupid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmDrugBrowserForInventoryByFirstChar(txtPage.value, ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmDrugBrowserForInventoryByFirstChar(page, property, option, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmDrugBrowserForInventoryByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectPharmProductBrowserForInventory(productid, packageid, producttype, page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForInventory&Page=' + page + '&ProductID=' + productid + '&PackageID=' + packageid + '&ProductType=' + producttype + '&Function=';
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	if(chk.checked==true){
		url = url + 'ADD';
		
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditPharmProductBrowserForInventory(productid, packageid, producttype, name, page){
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForInventory&Page=' + page + '&ProductID=' + productid + '&PackageID=' + packageid + '&ProductType=' + producttype + '&Function=';
	if(trim(txtSoLuong.value)!='' && chk.checked == false){
		chk.checked = true;
	}
	if(name=='SOLUONG'){
		url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearPharmProductBrowserForInventory(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForInventory&Function=CLEAR&Page=' + page;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptPharmProductBrowserForInventory(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForInventory&Function=ACCEPT&Page=' + page;
	
	var returnUrl = 'Default.aspx?Mod=';
	switch(page){
		case 'INVENTORY':
			returnUrl = returnUrl + 'Inventory';
			break;
		case 'LINVENTORY':
			returnUrl = returnUrl + 'Inventory&Type=L';
			break;
		case 'FINVENTORY':
			returnUrl = returnUrl + 'Inventory&Type=F';
			break;
		default:
			returnUrl = returnUrl + 'NOPAGE';
			break;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=returnUrl;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//SALE
//PRODUCT
function searchPharmProductBrowserForSale(pageid, keywordid, groupid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmProductBrowserForSaleByFirstChar(txtPage.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmProductBrowserForSaleByFirstChar(page, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmProductBrowserForSaleByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//DRUG
function searchPharmDrugBrowserForSale(pageid, propertyid, keywordid, groupid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmDrugBrowserForSaleByFirstChar(txtPage.value, ddlProperty.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmDrugBrowserForSaleByFirstChar(page, property, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmDrugBrowserForSaleByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditPharmProductBrowserForSale(productid, packageid, producttype, name, page){
	var txtCachDung = document.getElementById('txtCachDung_' + productid + '_' + packageid + '_' + producttype);
	var txtDonGia = document.getElementById('txtDonGia_' + productid + '_' + packageid + '_' + producttype);
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForSale&Page=' + page + '&ProductID=' + productid + '&ProductType=' + producttype + '&PackageID=' + packageid + '&Function=';
	if(trim(txtCachDung.value)!='' || trim(txtSoLuong.value)!='' || trim(txtDonGia.value)!=''){
		chk.checked = true;
		if(name!='SOLUONG' && trim(txtSoLuong.value)==''){
			txtSoLuong.value='1';
		}
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}else if(name=='DONGIA'){
			url = url + 'EDIT&Name=DonGia&Value=' + Url.encode(trim(txtDonGia.value));
		}else{
			url = url + 'EDIT&Name=CachDung&Value=' + Url.encode(trim(txtCachDung.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectPharmProductBrowserForSale(productid, packageid, producttype, defaultDonGia, defaultCachDung, page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForSale&Page=' + page + '&ProductID=' + productid + '&ProductType=' + producttype + '&PackageID=' + packageid + '&Function=';
	var txtCachDung = document.getElementById('txtCachDung_' + productid + '_' + packageid + '_' + producttype);
	var txtDonGia = document.getElementById('txtDonGia_' + productid + '_' + packageid + '_' + producttype);
	var txtSoLuong = document.getElementById('txtSoLuong_' + productid + '_' + packageid + '_' + producttype);
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	if(chk.checked==true){
		url = url + 'ADD';
		if(trim(txtCachDung.value)==''){
			txtCachDung.value = defaultCachDung;
		}
		if(trim(txtDonGia.value)==''){
			txtDonGia.value = defaultDonGia;
		}
		if(trim(txtSoLuong.value)==''){
			txtSoLuong.value = '1';
		}
		if(trim(txtCachDung.value)!=''){
			url = url + '&CachDung=' + Url.encode(trim(txtCachDung.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
		if(trim(txtDonGia.value)!=''){
			url = url + '&DonGia=' + trim(txtDonGia.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtCachDung.value)==trim(defaultCachDung)){
			txtCachDung.value = '';
		}
		if(trim(txtDonGia.value)==trim(defaultDonGia)){
			txtDonGia.value = '';
		}
		txtSoLuong.value = '';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearPharmProductBrowserForSale(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForSale&Function=CLEAR&Page=' + page;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptPharmProductBrowserForSale(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowserForSale&Function=ACCEPT&Page=' + page;
	
	var returnUrl = 'Default.aspx?Mod=';
	switch(page){
		case 'ICUSTOMER':
			returnUrl = returnUrl + 'IECustomer&Type=I';
			break;
		case 'ECUSTOMER':
			returnUrl = returnUrl + 'IECustomer&Type=E';
			break;
		default:
			returnUrl = returnUrl + 'NOPAGE';
			break;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=returnUrl;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//STORES
//PRODUCT
function searchPharmProductBrowser(pageid, keywordid, groupid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	var txtPage = document.getElementById(pageid);
	var ddlGroup = document.getElementById(groupid);
	searchPharmProductBrowserByFirstChar(txtPage.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmProductBrowserByFirstChar(page, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmProductBrowserByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//DRUG
function searchPharmDrugBrowser(pageid, propertyid, optionid, keywordid, groupid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	var ddlGroup = document.getElementById(groupid);
	var txtPage = document.getElementById(pageid);
	searchPharmDrugBrowserByFirstChar(txtPage.value, ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), '', ddlGroup.value, targetid)
}
function searchPharmDrugBrowserByFirstChar(page, property, option, keyword, character, groupid, targetid){
	var url = 'Ajax/Default.aspx?Mod=PharmDrugBrowserByChar&Page=' + page + '&FChar=' + character + '&GroupID=' + groupid;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectPharmProductBrowser(productid, packageid, producttype, page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowser&Page=' + page + '&ProductID=' + productid + '&PackageID=' + packageid + '&ProductType=' + producttype + '&Function=';
	var chk = document.getElementById('chk_' + productid + '_' + packageid + '_' + producttype);
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearPharmProductBrowser(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowser&Function=CLEAR&Page=' + page;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptPharmProductBrowser(page){
	var url = 'Ajax/Default.aspx?Mod=SelectPharmDrugBrowser&Function=ACCEPT&Page=' + page;
	
	var returnUrl = 'Default.aspx?Mod=';
	switch(page){
		case 'ISUPPLIER':
			returnUrl = returnUrl + 'IESupplier&Type=I';
			break;
		case 'ESUPPLIER':
			returnUrl = returnUrl + 'IESupplier&Type=E';
			break;
		case 'ISTORE':
			returnUrl = returnUrl + 'IEStore&Type=I';
			break;
		case 'ESTORE':
			returnUrl = returnUrl + 'IEStore&Type=E';
			break;
		default:
			returnUrl = returnUrl + 'NOPAGE';
			break;
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=returnUrl;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//END PHARMACY NEW
function loadServices(objService, targetid, func){
	var parentid = objService.value;
	if(parentid>0){
		var url = 'Ajax/Default.aspx?Mod=ServicesInGroup&GroupID=' + parentid + '&Function=' + func;
		AjaxRequest.get(
		{
			'url':url
			,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
			,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}else{
		document.getElementById(targetid).innerHTML='';
	}
}
//service Group
function doSearchServiceGroup(keywordid, targetid, groupid){
	var txtKeyword = document.getElementById(keywordid);
	var txtGroupID = document.getElementById(groupid);
	searchServiceGroup(Url.encode(txtKeyword.value), 'ALL', targetid, txtGroupID.value)
}
function searchServiceGroup(keyword, character, targetid, groupid){
	var url = 'Ajax/Default.aspx?Mod=MyServiceGroupByChar&GroupID=' + groupid + '&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectServiceGroup(chk, serviceid){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceGroup&ServiceID=' + serviceid + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearServiceGroup(groupid){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceGroup&Function=CLEAR&GroupID=' + groupid;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptServiceGroup(groupid){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceGroup&Function=ACCEPT&GroupID=' + groupid;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=ServiceGroupDetail&GroupID=' + groupid;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//pay services
function doSearchPayServices(keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	searchPayServices(Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchPayServices(keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyPayServicesByChar&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function getPayServices(character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyPayServicesByChar&FChar=' + character;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectPayService(chk, serviceid){
	var url = 'Ajax/Default.aspx?Mod=SelectPayService&ServiceID=' + serviceid + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearPayService(){
	var url = 'Ajax/Default.aspx?Mod=SelectPayService&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptPayService(){
	var url = 'Ajax/Default.aspx?Mod=SelectPayService&Function=ACCEPT';
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=PayService';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//result services
function doSearchServiceResults(keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	searchServiceResults(Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchServiceResults(keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyServiceResultsByChar&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function getServiceResults(character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyServiceResultsByChar&FChar=' + character;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectServiceResult(chk, serviceid){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceResult&ServiceID=' + serviceid + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearServiceResult(){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceResult&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptServiceResult(){
	var url = 'Ajax/Default.aspx?Mod=SelectServiceResult&Function=ACCEPT';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=AssignHealthServiceResult';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//assign services
function doSearchServices(keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	searchServices(Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchServices(keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyServicesByChar&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function getServices(character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyServicesByChar&FChar=' + character;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectService(chk, serviceid){
	var url = 'Ajax/Default.aspx?Mod=SelectService&ServiceID=' + serviceid + '&Function=';
	if(chk.checked==true){
		url = url + 'ADD';
	}else{
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearService(){
	var url = 'Ajax/Default.aspx?Mod=SelectService&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptService(){
	var url = 'Ajax/Default.aspx?Mod=SelectService&Function=ACCEPT';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=AssignHealthService';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//drugs
function toggleTag(tagid, visible){
	var obj = document.getElementById(tagid);
	if(visible==true){
		obj.style.display = "block";
	}else{
		obj.style.display = "none";
	}
}
function setValue(obj, optionid) {
	var txtOption = document.getElementById(optionid);
	txtOption.value = obj.value;
}
function doSearchDrugs(propertyid, optionid, keywordid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	searchDrugs(ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchDrugs(property, option, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyDrugsByChar&FChar=' + character;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditDrug(drugid, name){
	var chk = document.getElementById('chk_' + drugid);
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectDrug&DrugID=' + drugid + '&Function=';
	if(trim(txtCachDung.value)!='' || trim(txtSoLuong.value)!=''){
		chk.checked = true;
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}else{
			url = url + 'EDIT&Name=CachDung&Value=' + Url.encode(trim(txtCachDung.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectDrug(drugid, defaultCachDung){
	var url = 'Ajax/Default.aspx?Mod=SelectDrug&DrugID=' + drugid + '&Function=';
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var chk = document.getElementById('chk_' + drugid);
	if(chk.checked==true){
		url = url + 'ADD';
		var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
		if(trim(txtCachDung.value)==''){
			txtCachDung.value = defaultCachDung;
		}
		if(trim(txtCachDung.value)!=''){
			url = url + '&CachDung=' + Url.encode(trim(txtCachDung.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtCachDung.value)==trim(defaultCachDung)){
			txtCachDung.value = '';
		}
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectDrug&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectDrug&Function=ACCEPT';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=Prescription';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectTempPrescription(tempid){
	var url = 'Ajax/Default.aspx?Mod=SelectDrug&Function=SELECTTEMP&TempID='+tempid;
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location='Default.aspx?Mod=Prescription';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//temp drugs
function doSearchTempDrugs(propertyid, optionid, keywordid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	searchTempDrugs(ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchTempDrugs(property, option, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyTempDrugsByChar&FChar=' + character;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=ME';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditTempDrug(drugid, name){
	var chk = document.getElementById('chk_' + drugid);
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectTempDrug&DrugID=' + drugid + '&Function=';
	if(trim(txtCachDung.value)!='' || trim(txtSoLuong.value)!=''){
		chk.checked = true;
		if(name=='SOLUONG'){
			url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
		}else{
			url = url + 'EDIT&Name=CachDung&Value=' + Url.encode(trim(txtCachDung.value));
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectTempDrug(drugid, defaultCachDung){
	var url = 'Ajax/Default.aspx?Mod=SelectTempDrug&DrugID=' + drugid + '&Function=';
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var chk = document.getElementById('chk_' + drugid);
	if(chk.checked==true){
		url = url + 'ADD';
		var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
		if(trim(txtCachDung.value)==''){
			txtCachDung.value = defaultCachDung;
		}
		if(trim(txtCachDung.value)!=''){
			url = url + '&CachDung=' + Url.encode(trim(txtCachDung.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtCachDung.value)==trim(defaultCachDung)){
			txtCachDung.value = '';
		}
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearTempDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectTempDrug&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptTempDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectTempDrug&Function=ACCEPT';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=window.location;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//drugs in health profiles
function doSearchHDrugs(propertyid, optionid, keywordid, targetid){
	var ddlProperty = document.getElementById(propertyid);
	var txtOption = document.getElementById(optionid);
	var txtKeyword = document.getElementById(keywordid);
	searchHDrugs(ddlProperty.value, txtOption.value, Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchHDrugs(property, option, keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyHDrugsByChar&FChar=' + character;
	if(option!=''){
		url = url + '&Option=' + option;
	}else{
		url = url + '&Option=HOSPITAL';
	}
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	if(property!=''){
		url = url + '&Property=' + property;
	}else{
		url = url + '&Property=NAME';
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function getHDrugs(character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyHDrugsByChar&FChar=' + character;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function EditHDrug(drugid, name){
	var chk = document.getElementById('chk_' + drugid);
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	//var txtDonVi = document.getElementById('txtDonVi_' + drugid);
	var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
	
	var url = 'Ajax/Default.aspx?Mod=SelectHDrug&DrugID=' + drugid + '&Function=';
	if(trim(txtCachDung.value)!='' || trim(txtSoLuong.value)!='' || txtDonVi!=''){
		chk.checked = true;
		switch(name){
			case 'DONVI':
				url = url + 'EDIT&Name=DonVi&Value=' + Url.encode(trim(txtDonVi.value));
				break;
			case 'SOLUONG':
				url = url + 'EDIT&Name=SoLuong&Value=' + Url.encode(trim(txtSoLuong.value));
				break;
			case 'CACHDUNG':
				url = url + 'EDIT&Name=HuongDanSuDung&Value=' + Url.encode(trim(txtCachDung.value));
				break;
		}
	}else{
		chk.checked = false;
		url = url + 'REMOVE';
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectHDrug(drugid, defaultCachDung){
	var url = 'Ajax/Default.aspx?Mod=SelectHDrug&DrugID=' + drugid + '&Function=';
	var txtCachDung = document.getElementById('txtCachDung_' + drugid);
	var chk = document.getElementById('chk_' + drugid);
	if(chk.checked==true){
		url = url + 'ADD';
		var txtSoLuong = document.getElementById('txtSoLuong_' + drugid);
		if(trim(txtCachDung.value)==''){
			txtCachDung.value = defaultCachDung;
		}
		if(trim(txtCachDung.value)!=''){
			url = url + '&CachDung=' + Url.encode(trim(txtCachDung.value));
		}
		if(trim(txtSoLuong.value)!=''){
			url = url + '&SoLuong=' + trim(txtSoLuong.value);
		}
	}else{
		url = url + 'REMOVE';
		if(trim(txtCachDung.value)==trim(defaultCachDung)){
			txtCachDung.value = '';
		}
	}
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ClearHDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectHDrug&Function=CLEAR';
	
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function AcceptHDrug(){
	var url = 'Ajax/Default.aspx?Mod=SelectHDrug&Function=ACCEPT';
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=document.location.href;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//HEALTH PROFILES
function doSearchHServices(keywordid, targetid){
	var txtKeyword = document.getElementById(keywordid);
	searchHServices(Url.encode(txtKeyword.value), 'ALL', targetid)
}
function searchHServices(keyword, character, targetid){
	var url = 'Ajax/Default.aspx?Mod=MyHServicesByChar&FChar=' + character;
	if(keyword!=''){
		url = url + '&Keyword=' + keyword;
	}
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectHService(chk, serviceid){
	var url = 'Default.aspx?Mod=AddNewTest&TestID=' + serviceid;
	window.location = url;
}