<jsp:useBean id="userBean" scope="session" class="userstuff.User"/>

<html>
<head><title>Welcome</title></head>
<body>


User ID <jsp:getProperty name="userBean" property="userID"/><br>
Password <jsp:getProperty name="userBean" property="password"/><br>
Contact Name <jsp:getProperty name="userBean" property="contactName"/><br>
City <jsp:getProperty name="userBean" property="city"/><br>
Country <jsp:getProperty name="userBean" property="country"/><br>

</body>
</html>