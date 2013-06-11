<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
if(session.getAttribute("id")!=null && session.getAttribute("id")!="")
{
	String id = (String)session.getAttribute("id");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function logoutgo()
{
	document.logoutform.submit();
}
</script>
<body>
<div stype="border:1px solid #000000;">
   ${ id} is connected...
</div>
<div>
<form name="logoutform" action="bbslogout" method="post">
<input type="button" value="logout" onclick="javascript:logoutgo()"/>
</form>
</div>
</body>
</html>