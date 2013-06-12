<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>JoinusResult page</title>
</head>
<body>
<div class="content">
가입을 축하드립니다.  <br/>
Enjoy our project!~
</div>
<jsp:useBean id="memberBean" scope="session" class="kr.co.adflow.member.MemberBean" />
	id : <jsp:getProperty property="id" name="memberBean"/> <br/>
	email : <jsp:getProperty property="email" name="memberBean"/><br/>
	name : <jsp:getProperty property="name" name="memberBean"/><br/>	


 <a href="/hellobyteman/bbslist">글목록</a>
</body>
</html>