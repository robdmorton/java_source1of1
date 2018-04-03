<jsp:useBean id="userBean" scope="session" class="userstuff.User"/>

<html>
<head><title>Welcome</title></head>
<body>

User ID:&nbsp;<jsp:getProperty name="userBean" property="userID"/></br>
Contact Name:&nbsp;<jsp:getProperty name="userBean" property="contactName"/></br>
City:&nbsp;<jsp:getProperty name="userBean" property="city"/></br>
Country:&nbsp;<jsp:getProperty name="userBean" property="country"/></br>
</body>
</html>