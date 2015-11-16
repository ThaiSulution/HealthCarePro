function HospitalChargeValidate(formType){
	var validate = true;
	
	if($('input[name*=\"txtNgayTamUng\"]').val().trim()==''){
		if(formType=='I')
			alert("Bạn phải nhập Ngày thu!");
		else
			alert("Bạn phải nhập Ngày trả lại!");
		
		return false;
	}
	
	if($('input[name*=\"txtTiemTamUng\"]').val().trim()==''){
		alert("Bạn phải nhập Số tiền!");
		return false;
	}
	
	if($('input[name*=\"txtNguoiTamUng\"]').val().trim()==''){
		if(formType=='I')
			alert("Bạn phải nhập Người nộp!");
		else
			alert("Bạn phải nhập Người nhận!");
		
		return false;
	}
	
	if($('input[name*=\"txtLyDo\"]').val().trim()==''){
		if(formType=='I')
			alert("Bạn phải nhập Lý do thu!");
		else
			alert("Bạn phải nhập Lý do trả lại!");
		
		return false;
	}
	
	return validate;
}
function HospitalizationUpdateValidate(){
	var validate = true;
	
	if($('input[name*=\"txtHoVaTen\"]').val().trim()==''){
		alert("Bạn phải nhập Tên bệnh nhân!");
		return false;
	}
	
	var strTuoi = $('input[name*=\"txtTuoi\"]').val().trim();
	var strNgaySinh = $('input[name*=\"txtNgaySinh\"]').val().trim();
	if(strTuoi=='' && strNgaySinh==''){
		alert("Bạn phải nhập Tuổi hoặc Ngày sinh!");
		return false;
	}
	
	if($('select[name*=\"ddlGioiTinh\"]').val().trim()==''){
		alert("Bạn phải chọn Giới tính!");
		return false;
	}
	
	if($('input[name*=\"txtDiaChi\"]').val().trim()==''){
		alert("Bạn phải nhập Địa chỉ!");
		return false;
	}
	
	return validate;
}
function HospitalizationValidate(){
	var validate = true;
	
	if($('input[name*=\"txtHoVaTen\"]').val().trim()==''){
		alert("Bạn phải nhập Tên bệnh nhân!");
		return false;
	}
	
	var strTuoi = $('input[name*=\"txtTuoi\"]').val().trim();
	var strNgaySinh = $('input[name*=\"txtNgaySinh\"]').val().trim();
	if(strTuoi=='' && strNgaySinh==''){
		alert("Bạn phải nhập Tuổi hoặc Ngày sinh!");
		return false;
	}
	
	if($('select[name*=\"ddlGioiTinh\"]').val().trim()==''){
		alert("Bạn phải chọn Giới tính!");
		return false;
	}
	
	if($('input[name*=\"txtDiaChi\"]').val().trim()==''){
		alert("Bạn phải nhập Địa chỉ!");
		return false;
	}
	
	if($('input[name*=\"txtNgayNhapVien\"]').val().trim()==''){
		alert("Bạn phải nhập Ngày nhập viện!");
		return false;
	}
	
	var strGioNhapVien = $('input[name*=\"txtGioNhapVien\"]').val().trim();
	if(strGioNhapVien!='' && strGioNhapVien >=24){
		alert("Giờ không hợp lệ!\nGiờ hợp lệ là một số từ 00 đến 23");
		return false;
	}
	
	var strPhutNhapVien = $('input[name*=\"txtPhutNhapVien\"]').val().trim();
	if(strPhutNhapVien!='' && strPhutNhapVien >=60){
		alert("Phút không hợp lệ!\nPhút hợp lệ là một số từ 00 đến 59");
		return false;
	}
	
	if($('select[name*=\"ddlKhoa\"]').val().trim()=='0'){
		alert("Bạn phải chọn một Khoa!");
		return false;
	}
	return validate;
}
function RequireHospitalizationValidate(){
	var validate = true;
	
	if($('input[name*=\"txtHoVaTen\"]').val().trim()==''){
		alert("Bạn phải nhập Tên bệnh nhân!");
		return false;
	}
	
	if($('input[name*=\"txtTuoi\"]').val().trim()==''){
		alert("Bạn phải nhập Tuổi!");
		return false;
	}
	
	if($('select[name*=\"ddlGioiTinh\"]').val().trim()==''){
		alert("Bạn phải chọn Giới tính!");
		return false;
	}
	
	if($('input[name*=\"txtDiaChi\"]').val().trim()==''){
		alert("Bạn phải nhập Địa chỉ!");
		return false;
	}
	
	if($('input[name*=\"txtLyDoNhapVien\"]').val().trim()==''){
		alert("Bạn phải nhập Lý do nhập viện!");
		return false;
	}
	return validate;
}
function checkMemberCard(MemberCardID){
	var isChecked = false;
	var txtMemberCard = document.getElementById(MemberCardID);
	
	if(isNonblank(txtMemberCard.value)){
		isChecked = true;
	}else{
		txtMemberCard.focus();
		alert('Bạn phải nhập mã thẻ thành viên!');
	}
	return isChecked;
}
function WaitingHealthServiceResultDetail(targetid, sophieu, loaiphieu){
	var url = 'Ajax/Default.aspx?Mod=WaitingHealthServiceResultInfo&Type=Detail&SoPhieu=' + sophieu + '&LoaiPhieu=' + loaiphieu;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

function WaitingHealthServiceResultStatus(targetid, sophieu, loaiphieu){
	var url = 'Ajax/Default.aspx?Mod=WaitingHealthServiceResultInfo&Type=Status&SoPhieu=' + sophieu + '&LoaiPhieu=' + loaiphieu;
	AjaxRequest.get(
	{
		'url':url
		,'onLoading':function(){document.getElementById(targetid).innerHTML='<img src=\"images/loading_grey.gif\">'}
		,'onSuccess':function(req){document.getElementById(targetid).innerHTML=req.responseText;}
		,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
		}
	);
}

function ProductValidate(){
	var validate = true;
	if($('input[name*=\"txtTenSanPham\"]').val().trim()==''){
		alert("Bạn phải nhập Tên sản phẩm!");
		return false;
	}
	
	$('select[name*=\"ddlDonVi\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			alert("Bạn phải chọn một Đơn vị!");
			validate = false;
		}
	});
	
	return validate;
}

function DrugValidate(){
	var validate = true;
	
	$('select[name*=\"ddlDonVi\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			alert("Bạn phải chọn một Đơn vị!");
			validate = false;
		}
	});
	
	return validate;
}
function InventoryValidate(){
	var validate = true;
	
	if($('input[name*=\"txtNgayKiemKe\"]').val().trim()==''){
		alert("Bạn phải nhập Ngày kiểm kê!");
		return false;
	}
	
	$('input[name*=\"txtSoLuongKiemKe\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('select[name*=\"ddlQuyCachDongGoi\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			validate = false;
		}
	});
	
	if(validate==false){
		alert("Bạn phải nhập đầy đủ thông tin vào các ô: Đơn vị, Số lượng!");
	}
	return validate;
}
function IECustomerValidate(){
	var validate = true;
	
	if($('input[name*=\"txtNgayHoaDon\"]').val().trim()==''){
		alert("Bạn phải nhập Ngày hoá đơn!");
		return false;
	}
	
	$('input[name*=\"txtSoLuong\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('input[name*=\"txtDonGia\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	if(validate==false){
		alert("Bạn phải nhập đầy đủ thông tin vào các ô: Số lượng, Đơn giá!");
	}
	return validate;
}
function IESupplierValidate(){
	var validate = true;
	
	if($('input[name*=\"txtNgayHoaDon\"]').val().trim()==''){
		alert("Bạn phải nhập Ngày chứng từ!");
		return false;
	}
	
	$('input[name*=\"txtSoLuong\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('input[name*=\"txtDonGia\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('select[name*=\"ddlQuyCachDongGoi\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			validate = false;
		}
	});
	
	if(validate==false){
		alert("Bạn phải nhập đầy đủ thông tin vào các ô: Số lượng, Đơn giá, Đơn vị!");
	}
	return validate;
}

function IEStoreValidate(){
	var validate = true;
	if($('input[name*=\"txtSoHoaDon\"]').val().trim()==''){
		alert("Bạn phải nhập Số chứng từ!");
		return false;
	}
	
	if($('input[name*=\"txtNgayHoaDon\"]').val().trim()==''){
		alert("Bạn phải nhập Ngày chứng từ!");
		return false;
	}
	
	$('select[name*=\"ddlKho\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			validate = false;
		}
	});
	
	if(validate==false){
		alert("Bạn phải chọn một Kho nội bộ!");
		return false;
	}
	
	$('input[name*=\"txtSoLuong\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('input[name*=\"txtDonGia\"]').each(function(index) {
		if($(this).val().trim()==''){
			validate = false;
		}
	});
	
	$('select[name*=\"ddlQuyCachDongGoi\"]').each(function(index) {
		if($(this).val().trim()=='0'){
			validate = false;
		}
	});
	
	if(validate==false){
		alert("Bạn phải nhập đầy đủ thông tin vào các ô: Số lượng, Đơn giá, Đơn vị!");
	}
	return validate;
}
function checkICDCode(status, value){
	if(isNonblank(value)){
		var url = 'Ajax/Default.aspx?Mod=CheckICDCode&ICDCode=' + value;
		AjaxRequest.get(
		{
			'url':url
			,'onLoading':function(){document.getElementById(status).innerHTML='<img src=\"images/loading_grey.gif\">'}
			,'onSuccess':function(req){document.getElementById(status).innerHTML=req.responseText;}
			,'onError':function(req){ alert('Error!\nStatusText='+req.statusText+'\nContents='+req.responseText);}
			}
		);
	}else{
		document.getElementById(status).innerHTML='';
	}
}
function checkAddNewAppointment(ThanhVienID, NgayHenKhamID, BuoiID){
	var isChecked = true;
	var ddlThanhVien = document.getElementById(ThanhVienID);
	var txtNgayHenKham = document.getElementById(NgayHenKhamID);
	var ddlBuoi = document.getElementById(BuoiID);
	
	if(ddlThanhVien.value=='0'){
		alert('Bạn phải chọn một thành viên!');
		ddlThanhVien.focus();
		return false;
	}
	
	if(!isNonblank(txtNgayHenKham.value)){
		alert('Bạn phải nhập Ngày hẹn khám!');
		txtNgayHenKham.focus();
		return false;
	}
	
	if(ddlBuoi.value==''){
		alert('Bạn phải chọn thời gian khám!');
		ddlBuoi.focus();
		return false;
	}
	
	return isChecked;
}
function checkAddNewMedicine(NgayKeDonID, ChanDoanID){
	var isChecked = false;
	var txtNgayKeDon = document.getElementById(NgayKeDonID);
	var txtChanDoan = document.getElementById(ChanDoanID);
	
	if(isNonblank(txtNgayKeDon.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Ngày khám!');
		txtNgayKeDon.focus();
		return false;
	}
	
	if(isNonblank(txtChanDoan.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập thông tin Chẩn đoán!');
		txtChanDoan.focus();
		return false;
	}
	return isChecked;
}
function checkPrescription(HoVaTenID, TuoiID, GioiTinhID, DiaChiaID, ChanDoanID){
	var isChecked = false;
	var txtHoVaTen = document.getElementById(HoVaTenID);
	var txtTuoi = document.getElementById(TuoiID);
	//var txtHoVaTen = document.getElementById(GioiTinhID);
	var txtDiaChi = document.getElementById(DiaChiaID);
	var txtChanDoan = document.getElementById(ChanDoanID);
	
	if(isNonblank(txtHoVaTen.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Họ và tên bệnh nhân!');
		txtHoVaTen.focus();
		return false;
	}
	
	if(isNonblank(txtTuoi.value)){
		if(isWhole(txtTuoi.value)){
			isChecked = true;
		}else{
			alert('Tuổi bệnh nhân không hợp lệ!\nTuổi phải là một số');
			txtTuoi.focus();
			return false;
		}
	}else{
		alert('Bạn phải nhập Tuổi bệnh nhân!');
		txtTuoi.focus();
		return false;
	}
	
	if(isNonblank(txtDiaChi.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Địa chỉ của bệnh nhân!');
		txtDiaChi.focus();
		return false;
	}
	
	if(isNonblank(txtChanDoan.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Chẩn đoán cho bệnh nhân!');
		txtChanDoan.focus();
		return false;
	}
	return isChecked;
}
function checkRegistration(HoVaTenID, TuoiID, DiaChiaID, NhuCauKhamID, ChiPhiKhamID, NgayDangKyID){
	var isChecked = false;
	var txtHoVaTen = document.getElementById(HoVaTenID);
	var txtTuoi = document.getElementById(TuoiID);
	var txtDiaChi = document.getElementById(DiaChiaID);
	var txtNhuCauKham = document.getElementById(NhuCauKhamID);
	var txtChiPhiKham = document.getElementById(ChiPhiKhamID);
	var txtNgayDangKy = document.getElementById(NgayDangKyID);
	
	if(isNonblank(txtHoVaTen.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Họ và tên bệnh nhân!');
		txtHoVaTen.focus();
		return false;
	}
	
	if(isNonblank(txtTuoi.value)){
		if(isWhole(txtTuoi.value)){
			isChecked = true;
		}else{
			alert('Tuổi bệnh nhân không hợp lệ!\nTuổi phải là một số lớn hơn không.');
			txtTuoi.focus();
			return false;
		}
	}else{
		alert('Bạn phải nhập Tuổi bệnh nhân!');
		txtTuoi.focus();
		return false;
	}
	
	if(isNonblank(txtDiaChi.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Địa chỉ của bệnh nhân!');
		txtDiaChi.focus();
		return false;
	}
	
	if(isNonblank(txtNhuCauKham.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Nhu cầu khám bệnh!');
		txtNhuCauKham.focus();
		return false;
	}
	
	if(isNonblank(txtChiPhiKham.value)){
		if(isWhole(txtChiPhiKham.value)){
			isChecked = true;
		}else{
			alert('Chi phí khám bệnh không hợp lệ!\nChi phí khám bệnh phải là một số lớn hơn không.');
			txtChiPhiKham.focus();
			return false;
		}
	}else{
		alert('Bạn phải nhập Chi phí khám bệnh!');
		txtChiPhiKham.focus();
		return false;
	}
	
	if(isNonblank(txtNgayDangKy.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Ngày khám!');
		txtNgayDangKy.focus();
		return false;
	}
	
	return isChecked;
}
function checkAssignHealthService(HoVaTenID, TuoiID, GioiTinhID, DiaChiaID){
	var isChecked = false;
	var txtHoVaTen = document.getElementById(HoVaTenID);
	var txtTuoi = document.getElementById(TuoiID);
	var txtDiaChi = document.getElementById(DiaChiaID);
	
	if(isNonblank(txtHoVaTen.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Họ và tên bệnh nhân!');
		txtHoVaTen.focus();
		return false;
	}
	
	if(isNonblank(txtTuoi.value)){
		if(isWhole(txtTuoi.value)){
			isChecked = true;
		}else{
			alert('Tuổi bệnh nhân không hợp lệ!\nTuổi phải là một số');
			txtTuoi.focus();
			return false;
		}
	}else{
		alert('Bạn phải nhập Tuổi bệnh nhân!');
		txtTuoi.focus();
		return false;
	}
	
	if(isNonblank(txtDiaChi.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Địa chỉ của bệnh nhân!');
		txtDiaChi.focus();
		return false;
	}
	return isChecked;
}
function checkPayService(HoVaTenID, TuoiID, GioiTinhID, DiaChiaID){
	var isChecked = false;
	var txtHoVaTen = document.getElementById(HoVaTenID);
	var txtTuoi = document.getElementById(TuoiID);
	var txtDiaChi = document.getElementById(DiaChiaID);
	
	if(isNonblank(txtHoVaTen.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Họ và tên bệnh nhân!');
		txtHoVaTen.focus();
		return false;
	}
	
	if(isNonblank(txtTuoi.value)){
		if(isWhole(txtTuoi.value)){
			isChecked = true;
		}else{
			alert('Tuổi bệnh nhân không hợp lệ!\nTuổi phải là một số');
			txtTuoi.focus();
			return false;
		}
	}else{
		alert('Bạn phải nhập Tuổi bệnh nhân!');
		txtTuoi.focus();
		return false;
	}
	
	if(isNonblank(txtDiaChi.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Địa chỉ của bệnh nhân!');
		txtDiaChi.focus();
		return false;
	}
	return isChecked;
}
function checkLogin(UserNameID, PasswordID, TypeID){
	var isChecked = false;
	var txtUserName = document.getElementById(UserNameID);
	var txtPassword = document.getElementById(PasswordID);
	var txtType = document.getElementById(TypeID);
	
	if(isNonblank(txtUserName.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Tên đăng nhập!');
		txtUserName.focus();
		return false;
	}
	
	if(isNonblank(txtPassword.value)){
		isChecked = true;
	}else{
		alert('Bạn phải nhập Mật khẩu!');
		txtPassword.focus();
		return false;
	}
	
	if(isNonblank(txtType.value)){
		isChecked = true;
	}else{
		alert('Bạn phải chọn một Chức năng!');
		txtType.focus();
		return false;
	}
	
	return isChecked;
}
function checkSearchInList(keywordid){
	var txtKeyword = document.getElementById(keywordid);
	if(isNonblank(txtKeyword.value)){
		return true;
	}else{
		alert('Bạn phải nhập một từ để tìm kiếm!');
		txtKeyword.focus();
		return false;
	}
}