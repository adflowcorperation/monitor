<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.lang.*,java.io.*" %>






<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Join Us</title>
<script type="text/javascript">
function idCheck(id){	
	var idValidateCount = 0;
	if(id == '')
	{
		document.getElementById("formalert").innerHTML="���̵� �Է��� �ּ���.";
		document.joinus.id.focus();
		return;
	}
	else
	{	
		//document.location="/EyleeBoard/validateID?id="+id+"&idValidateCount="+idValidateCount;
		url = "/hellobyteman/validateID?id="+id+"&idValidateCount="+idValidateCount;
		window.open(url,"post","width=300 height=150");
		
	
	}
}
function inputCheck()
{
	if(document.joinus.id.value == '')
	{
		document.getElementById("formalert").innerHTML="ID�� �Է��� �ּ���.";
		return;
	}
	
	if(document.joinus.password.value == '')
	{
		document.getElementById("formalert").innerHTML="��й�ȣ�� �Է��� �ּ���.";
		document.joinus.password.focus();
		return;
	}
	
	if(document.joinus.repassword.value == '')
	{
		document.getElementById("formalert").innerHTML="��й�ȣ�� Ȯ���� �ּ���.";
		document.joinus.repassword.focus();
		return;
	}
	
	if(document.joinus.password.value != document.joinus.repassword.value)
	{
		document.getElementById("formalert").innerHTML="��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
		document.joinus.repassword.focus();
		return;
	}
	if(document.joinus.email.value == '')
	{
		document.getElementById("formalert").innerHTML="email�� �Է��� �ּ���.";
		document.joinus.email.focus();
		return;
	}
	
		
	document.joinus.submit();
}
</script>
</head>
<body>
<div class="joinus_form">
JOIN US <br/>
<form name="joinus" method="post" action="/hellobyteman/JoinusServlet">
<p id="formalert"></p>
<div class="idform">

<hr>
<span class="idtext"> ID * </span> 
<input type="text" name="id" size=20 onMouseOver=this.style.backgroundColor="skyblue" onMouseOut=this.style.backgroundColor="white">
<input type="button" name="idvalidate" value="confirm" onclick="idCheck(this.form.id.value)">
</div>
<div class="passwordform">
<span class="passwordtext">�н����� * </span>
<input type="password" name="password" size=15>
</div>
<div class="repasswordform">
<span class="repasswordtext">�н����� Ȯ�� * </span>
<input type="password" name="repassword" size=15>
</div>
<div class="emailform">
<span class="emailtext">email * </span>
<input name="email" type="text" size=27 onMouseOver=this.style.backgroundColor="skyblue" onMouseOut=this.style.backgroundColor="white">
</div>
<div class="nameform">
<span class="nametext">�̸� </span>
<input name="username" type="text" size=27 onMouseOver=this.style.backgroundColor="skyblue" onMouseOut=this.style.backgroundColor="white">
</div>
<div class="ageform">
<span class="agetext">���� </span>
<input name="age" type="text" size=27 onMouseOver=this.style.backgroundColor="skyblue" onMouseOut=this.style.backgroundColor="white">
</div>
<div class="ageform">
<span class="agetext">gender </span>
<select name="gender">
<option value="����">����</option>
<option value="����">����</option>
</select>
</div>
<div>
<span><input type="button" value="ȸ������" onClick="javascript:inputCheck()"/></span>
<span><input type="reset" value="�ٽþ���"/></span>
</div>
</form>
</div>  <!--  idform close -->
</body>
</html>