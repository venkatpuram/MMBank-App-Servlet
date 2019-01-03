
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="updateform.mm">
<h1 align="center">Update Form</h1>
	<table align="center">
	<tr>
	<td><label>Account Number </label><td>
	<td><input type="number" name="accountnumber"></td>
	</tr>
	<tr>
	<td><input type="submit" name="submit" value="submit" ></td>
		<td><input type="reset" name="reset" value="reset"></td>
	</tr>	
	</table>
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
	
</body>
</html>