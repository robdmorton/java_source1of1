<jsp:useBean id="login" scope="request" class="userstuff.LoginFormBean"/>

<html>
<head><title>No User</title></head>
<body>

<p>Login for user with name&nbsp;
<jsp:getProperty name="login" property="user_id"/>
not found.

<a href='/LoginMVC/login.html'> Try login again</A>
</body>
</html>