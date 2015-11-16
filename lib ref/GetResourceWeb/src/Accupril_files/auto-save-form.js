
function autoSaveRequireServicesGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequireServicesDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRequireServices(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequireServices&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveTransferRoom(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToTransferRoom&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRequireHealthServiceGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequireHealthServiceDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRequirePrescription(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequirePrescription&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRequirePrescriptionGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequirePrescriptionDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHospitalizationP(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToHospitalizationP&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHospitalization(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToHospitalization&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRequireHospitalization(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRequireHospitalization&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSavePrintBarcodeGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPrintBarcodeDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

function ShowExamination(examinationid){
	var strPhiKhamBenh = "";
	var strSoTheBHYT = $('input[name*="txtSoTheBHYT"]').val();
	
	$.ajaxSetup({cache:false});
	$.getJSON('Suggestion.aspx?Mod=SelectExamination&ID=' + examinationid + '&BHYT=' + strSoTheBHYT, function(data) {
		$('input[name*="txtPhiKhamBenh"]').val(data.PhiKhamBenh);
		
		var strGiamGia = $('input[name*="txtGiamGia"]').val();
		
		var intPhiKhamBenh = 0;
		if(data.PhiKhamBenh!=''){
			intPhiKhamBenh = parseInt(data.PhiKhamBenh, 10);
		}
		var intGiamGia = 0;
		if(strGiamGia!=''){
			intGiamGia = parseInt(strGiamGia, 10);
		}
		
		$('[id*="txtThanhTien"]').val(intPhiKhamBenh * (100 - intGiamGia) / 100);
	
	});
}

function calAge(bornDate, mod){
	if(bornDate!=''){
		var bd = bornDate.split('/');
		var y = parseInt(bd[2], 10);
		var m = parseInt(bd[1], 10);
		
		var today = new Date();
		var cy = today.getFullYear();
		var cm = today.getMonth()+1;
		
		var month = (cm-m) + 12*(cy-y);
		
		$('input[name*="txtTuoi"]').val(month);
		
		var age = 0;
		var ageType = 'NAM';
		
		if(month<72){
			age = month;
			ageType = 'THANG';
		}else{
			age = Math.floor(month/12);
			if(month%12>0){
				age++;
			}
		}
		
		$('input[name*="txtTuoi"]').val(age);
		$('select[name*="ddlDonViTinh"]').val(ageType);
		
		AjaxRequest.get(
		{
			'url':'Ajax/Default.aspx?Mod=SaveTo' + mod + '&Name=Tuoi&Value=' + age
			,'onLoading':function(){}
			,'onSuccess':function(req){}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
		
		AjaxRequest.get(
		{
			'url':'Ajax/Default.aspx?Mod=SaveTo' + mod + '&Name=DonViTinh&Value=' + ageType
			,'onLoading':function(){}
			,'onSuccess':function(req){}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}
}

function ShowInsuranceCard(bhytid, type){	
	$.ajaxSetup({cache:false});
	$.getJSON('Suggestion.aspx?Mod=SelectInsuranceCard&Type=' + type + '&ID=' + bhytid, function(data) {
	
		$('input[name*="txtHoVaTen"]').val(data.HoVaTen);
		$('input[name*="txtHoVaTenPhuHuynh"]').val(data.HoVaTenPhuHuynh);
		$('input[name*="txtTuNgay"]').val(data.TuNgay);
		$('input[name*="txtDenNgay"]').val(data.DenNgay);
		$('input[name*="txtNgaySinh"]').val(data.NgaySinh);
		$('input[name*="txtTenCSYT"]').val(data.TenCSYT);
		$('input[name*="txtMaCSYT"]').val(data.MaCSYT);
		$('input[name*="txtMaTinh"]').val(data.MaTinh);
		$('input[name*="txtDiaChi"]').val(data.DiaChi);
		$('select[name*="ddlGioiTinh"]').val(data.GioiTinh);
		$('input[name*="txtTuoi"]').val(data.Tuoi);
		$('select[name*="ddlDonViTinh"]').val(data.DonViTinh);
		
	});
}

function ShowFamily(familyid){
	$.ajaxSetup({cache:false});
	$.getJSON('Suggestion.aspx?Mod=SelectFamily&ID=' + familyid, function(data) {
		$('input[name*="txtTuoi"]').val(data.Tuoi);
		$('select[name*="ddlDonViTinh"]').val(data.DonViTinh);
		$('select[name*="ddlGioiTinh"]').val(data.GioiTinh);
		$('input[name*="txtDiaChi"]').val(data.DiaChi);
		$('input[name*="txtDienThoai"]').val(data.DienThoai);
		$('input[name*="txtEmail"]').val(data.Email);
		$('input[name*="txtSoTheBHYT"]').val(data.SoBHYT);
	});
}
function autoSaveInventory(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToInventory&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveInventoryGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToInventoryDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveIECustomer(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIECustomer&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveIECustomerGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIECustomerDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function ShowCustomer(customerid){
	var strDiaChi = "";
	$.ajaxSetup({cache:false});
	$.getJSON('Suggestion.aspx?Mod=SelectCustomer&ID=' + customerid, function(data) {
		$('input[name*="txtDiaChi"]').val(data.DiaChi);
		$('input[name*="txtDienThoai"]').val(data.DienThoai);
		$('input[name*="txtEmail"]').val(data.Email);
		$('input[name*="txtTuoi"]').val(data.Tuoi);
		$('select[name*="ddlGioiTinh"]').val(data.GioiTinh);
	});
	//alert(strDiaChi);
}
function autoSaveIESupplier(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIESupplier&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveIESupplierGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIESupplierDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveIEStore(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIEStore&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveIEStoreGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToIEStoreDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveRegistryForm(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToRegistryForm&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function SelectServiceAtHome(chkid){
	var act = (chkid.checked==true)?'ADD':'REMOVE';
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddOrRemoveServiceAtHome&Action=' + act + '&ServiceID=' + chkid.value
		,'onLoading':function(){}
		,'onSuccess':function(req){var mycart = document.getElementById('mycart'); mycart.innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

function updateWaitingWork(sophieu, loaiphieu, img){
	if(confirmEdit('Bạn chắc chắn muốn thay đổi trạng thái thực hiện?')){ 
		AjaxRequest.get(
		{
			'url':'Ajax/Default.aspx?Mod=UpdateWaitingWork&SoPhieu=' + sophieu + '&LoaiPhieu=' + loaiphieu
			,'onLoading':function(){ img.src='Images/loading.gif';}
			,'onSuccess':function(req){img.src=req.responseText;}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}
}
function updateWaitingResult(sophieu, loaiphieu, img){
	if(confirmEdit('Bạn chắc chắn muốn thay đổi trạng thái trả kết quả?')){ 
		AjaxRequest.get(
		{
			'url':'Ajax/Default.aspx?Mod=UpdateWaitingResult&SoPhieu=' + sophieu + '&LoaiPhieu=' + loaiphieu
			,'onLoading':function(){ img.src='Images/loading.gif';}
			,'onSuccess':function(req){img.src=req.responseText;}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}
}

function updateDoctorWorkStatus(doctorid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=UpdateDoctorWorkStatus&DoctorID=' + doctorid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){img.src=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

function updatePayServiceStatus(phieuthuid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=UpdatePayServiceStatus&PhieuThuID=' + phieuthuid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){img.src=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function markPatientFilter(phieuthuid, nhomxetnghiemid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=MarkPatientFilter&PhieuThuID=' + phieuthuid + '&NhomXetNghiemID=' + nhomxetnghiemid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){img.src=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function updatePatientRegisterStatus(registrationid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=UpdatePatientRegisterStatus&RegistrationID=' + registrationid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){img.src=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSavePatientRegister(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPatientRegister&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function syncContactAppointment(hovatenid, diachiid, dienthoaiid, tenlienheid, diachilienheid, dienthoailienheid){
	var txtHoVaTen = document.getElementById(hovatenid);
	var txtDiaChi = document.getElementById(diachiid);
	var txtDienThoai = document.getElementById(dienthoaiid);
	var txtTenLienHe = document.getElementById(tenlienheid);
	var txtDiaChiLienHe = document.getElementById(diachilienheid);
	var txtDienThoaiLienHe = document.getElementById(dienthoailienheid);
	
	autoSaveAppointment('TenLienHe', Url.encode(txtHoVaTen.value));
	autoSaveAppointment('DiaChiLienHe', Url.encode(txtDiaChi.value));
	autoSaveAppointment('DienThoaiLienHe', Url.encode(txtDienThoai.value));
	
	txtTenLienHe.value = txtHoVaTen.value;
	txtDiaChiLienHe.value = txtDiaChi.value;
	txtDienThoaiLienHe.value = txtDienThoai.value;
}
function autoSaveAppointment(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToAppointment&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function selectMember(memberid){
	var url = 'Ajax/Default.aspx?Mod=SaveToAppointment&Name=ThanhVienID&Value=' + memberid;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){window.location=window.location;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveTest(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToTest&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveTestGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToTestDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveMedicine(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToMedicine&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveMedicineGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToMedicineDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveInvoice(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToInvoice&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveInvoiceGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToInvoiceDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSave(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPrescriptionHeader&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveTempPrescription(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToTempPrescription&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHealthService(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToAssignHealthService&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHealthServiceResults(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToHealthServiceResults&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHealthServiceGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToAssignHealthServiceDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveHealthServiceResultsGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToHealthServiceResultsDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSavePayService(name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPayService&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSavePayServiceGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPayServiceDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToPrescriptionDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function autoSaveToTempPrescriptionGrid(rowid, name, value){
	var url = 'Ajax/Default.aspx?Mod=SaveToTempPrescriptionDetail&Index=' + rowid + '&Name=' + name + '&Value=' + value;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){}
		,'onSuccess':function(req){}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToMedicalInvoice(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToMedicalInvoice&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/drug_basket.jpg';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToAssignHealthService(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToAssignHealthService&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/cd.gif';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
//khong dung?
function addToHealthServiceResults(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToHealthServiceResults&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/kq.gif';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToInvoice(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToInvoice&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/drug_basket.jpg';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToMedicine(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToMedicine&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp';}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToBag(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToBag&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Thuốc đã được thêm vào Danh mục thuốc.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToManagerCataloge(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToManagerCataloge&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Thuốc đã được thêm vào Danh mục thuốc.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToMyHealthService(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToHealthService&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Dịch vụ y tế đã được thêm vào Danh mục dịch vụ y tế của bác sỹ khám.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToMyHealthServices(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToHealthServices&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add_green.bmp'; alert('Dịch vụ y tế đã được thêm vào Danh mục dịch vụ y tế của bác sỹ cận lâm sàng.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToReceptionHealthService(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToReceptionHealthService&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Dịch vụ y tế đã được thêm vào Danh mục dịch vụ y tế.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToManagerHealthService(phanloaiid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToManagerHealthService&PhanLoaiID=' + phanloaiid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Dịch vụ y tế đã được thêm vào Danh mục dịch vụ y tế.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
function addToMarketingCataloge(drugsid, img){
	AjaxRequest.get(
	{
		'url':'Ajax/Default.aspx?Mod=AddToMarketingCataloge&DrugsID=' + drugsid
		,'onLoading':function(){ img.src='Images/loading.gif';}
		,'onSuccess':function(req){ img.src='Images/icon_add.bmp'; alert('Thuốc đã được thêm vào Danh mục thuốc của tôi.'); }
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}
