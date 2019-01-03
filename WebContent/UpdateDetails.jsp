
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="updatesavings.mm">
<h1 align="center">Update Savings Account</h1>
	<table align="center">
	<tr>
	<td><label>Account Number </label><td>
	<td><input type="number" name="actnumber" readonly="readonly" value="${requestScope.accounts.bankAccount.accountNumber}"></td>
	</tr>
	<tr>
	<td><label>Account Holder Name </label><td>
	<td><input type="text" name="holderName" value="${requestScope.accounts.bankAccount.accountHolderName}"></td>
	</tr>
	<tr>
	<td><label>Account Balance </label><td>
	<td><input type="number" name="accountbalance" readonly="readonly" value="${requestScope.accounts.bankAccount.accountBalance}"></td>
	</tr>
	<tr>
	<td><label>Salaried Type </label><td>
	<td><input type="radio" name="salary" value="yes" ${requestScope.accounts.salary==true?"checked":""}>Yes</td>
	<td><input type="radio" name="salary" value="no" ${requestScope.accounts.salary==true?"":"checked"}>No</td>
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