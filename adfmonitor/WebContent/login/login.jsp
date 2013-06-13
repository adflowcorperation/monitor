<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Login Page</title>
	<script>		
		function signup()
		{
			document.location="/adfmonitor/login/joinus.jsp";
		}

		function inputValidate()
		{
			if(document.loginform.userId.value == '')
			{
				document.getElementById("formalert").innerHTML="ID를 입력해 주세요.";
				return;
			}
			
			if(document.loginform.password.value == '')
			{
				document.getElementById("formalert").innerHTML="비밀번호를 입력해 주세요.";
				document.joinus.password.focus();
				return;
			}
			
			
			document.loginform.submit();
		}
		</script>
</head>
<body>
<form name="loginform" action="/adfmonitor/login/bbslogin" method="post">
<div class="formcontent">
<br> User ID: <input type="text" name="userId" />
<br> Password: <input type="password" name="password" />
</div>
<p id="formalert"></p>
<div>
<input type="button" value="login" onclick="javascript:inputValidate()"/>
<input type="button" value="cancel" onclick="history.back(-1)" />
</div>
<div>
<input type="button" value="signup" onclick="javascript:signup()" />
<input type="button" value="continue without login" onclick="window.location.href='/adfmonitor/bbslist'" />
</div>
</form>
</body>
</html>