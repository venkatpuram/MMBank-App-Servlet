<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="withdraw.mm">
<h1 align="center">WithDraw</h1>
	<table align="center">
	<tr>
	<td><label>Account Number </label><td>
	<td><input type="number" name="accountnumber"></td>
	</tr>
	<tr>
	<td><label>Amount to Withdraw </label><td>
	<td><input type="number" name="amount"></td>
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