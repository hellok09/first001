function employeeCheck(){
	if(document.frm.id.value.length==0){
		alert("아이디를 써주세요.");
		document.frm.id.focus();
		return false;
	}
	
	if(document.frm.pass.value.length==0){
		alert("비밀번호를 입력해주세요.");
		document.frm.pass.focus();
		return false;
	}
	
	if(document.frm.name.value.length==0){
		alert("사원의 이름을 입력해주세요.");
		document.frm.name.focus();
		return false;
	}
	
	return true;
}