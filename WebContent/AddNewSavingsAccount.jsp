<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="addnewsavings.mm">
<h1 align="center">Create New Savings Account</h1>
	<table align="center">
	<tr>
	<td><label>Account Holder Name </label><td>
	<td><input type="text" name="holderName"></td>
	</tr>
	<tr>
	<td><label>Account Initial Balance </label><td>
	<td><input type="number" name="accountbalance"></td>
	</tr>
	<tr>
	<td><label>Salaried Type </label><td>
	<td><input type="radio" name="salary" value="yes">Yes</td>
	<td><input type="radio" name="salary" value="no">No</td>
	</tr>
	<tr>
	<td><input type="submit" name="submit" value="submit"></td>
		<td><input type="reset" name="reset" value="reset"></td>
	</tr>	
	</table>
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>