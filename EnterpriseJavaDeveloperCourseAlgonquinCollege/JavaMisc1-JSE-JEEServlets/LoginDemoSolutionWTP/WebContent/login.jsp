<html>
	<head>
		<title>Login</title>
	</head>
<body>
	<h3>Login</h3>
Context Path is:<%=request.getContextPath() %>	
	
<form action="<%=request.getContextPath()%>/login" method="post">
<table>
	<tr>
	<td><input type="text" name="user_id"></input></td>
	</tr>


<!-- NEED AN INPUT FIELD FOR PASSWORD Hint: use TD tag-->		
	<tr>		
	<td><input type="password" name="password"></input></td>
	</tr>
	<tr>
	<td>
	<input type="submit" value="submit"></input></td>
	</tr>
	</table>
</form>
</body>
</html>
