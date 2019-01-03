package com.capgemini.mmbankwebproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

@WebServlet("*.mm")
public class MMBankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	boolean flag=false;
	public MMBankController() {
		super();

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM ACCOUNT");
			preparedStatement.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
		String path = request.getServletPath();
		System.out.println(path);
		SavingsAccount savingsAccount = null,savingsAccount1=null;
		int accountNumber=0;
		double amount=0.0;
		RequestDispatcher dispatcher;
		
		switch (path) {
		case "/addnewaccount.mm":
			response.sendRedirect("AddNewSavingsAccount.jsp");
			break;
		case "/addnewsavings.mm":
			String accountHolderName = request.getParameter("holderName");
			double accountBalance = Double.parseDouble(request
					.getParameter("accountbalance"));
			boolean salary = request.getParameter("salary").equalsIgnoreCase(
					"yes") ? true : false;
			try {
				savingsAccountService.createNewAccount(accountHolderName,
						accountBalance, salary);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/closeform.mm":
			response.sendRedirect("closeAccount.jsp");
			break;
		case "/closeaccount.mm":
			int accountId=Integer.parseInt(request.getParameter("accountid"));
			try {
				savingsAccountService.deleteAccount(accountId);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/depositform.mm":
			response.sendRedirect("Deposit.jsp");
			break;
		case "/deposit.mm":
			accountNumber=Integer.parseInt(request.getParameter("accountnumber"));
			amount = Double.parseDouble(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				savingsAccountService.deposit(savingsAccount, amount);
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/withdrawform.mm":
			response.sendRedirect("Withdraw.jsp");
			break;
		case "/withdraw.mm":
			accountNumber=Integer.parseInt(request.getParameter("accountnumber"));
			amount = Double.parseDouble(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				savingsAccountService.withdraw(savingsAccount, amount);
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/fundtransferform.mm":
			response.sendRedirect("FundTransfer.jsp");
			break;
		case "/fundtransfer.mm":
			int senderaccountNumber=Integer.parseInt(request.getParameter("senderaccountnumber"));
			int receiveraccountNumber=Integer.parseInt(request.getParameter("receiveraccountnumber"));
			amount = Double.parseDouble(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(senderaccountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				savingsAccount1 = savingsAccountService
						.getAccountById(receiveraccountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				savingsAccountService.fundTransfer(savingsAccount, savingsAccount1, amount);
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/currentbalance.mm":
			response.sendRedirect("GetCurrentBalance.jsp");
			break;
		case "/getcurrentbalance.mm":
			accountNumber=Integer.parseInt(request.getParameter("accountnumber"));
			try {
				double currentbalance=savingsAccountService.checkBalance(accountNumber);
				PrintWriter out=response.getWriter();
				out.println("Current Balance is :"+currentbalance);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			accountNumber = Integer.parseInt(request.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService.getAccountById(accountNumber);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/getAll.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/sortByName.mm":
			flag=!flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result= arg0.getBankAccount().getAccountHolderName().compareTo
								(arg1.getBankAccount().getAccountHolderName());
						if(flag==true)
						{
							return result;
						}
						return -result;
					}
				});
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case "/sortByBalance.mm":
			flag=!flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result= (int) (arg0.getBankAccount().getAccountBalance()-
								(arg1.getBankAccount().getAccountBalance()));
						if(flag==true)
						{
							return result;
						}
						return -result;
					}
				});
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		
		case "/sortByactnumber.mm":
			flag=!flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result= (int) (arg0.getBankAccount().getAccountNumber()-
								(arg1.getBankAccount().getAccountNumber()));
						if(flag==true)
						{
							return result;
						}
						return -result;
					}
				});
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/updatesa.mm":
			response.sendRedirect("updatesaform.jsp");
			break;
		case "/updateform.mm":
			accountNumber = Integer.parseInt(request.getParameter("accountnumber"));
			try {
				 savingsAccount=savingsAccountService.getAccountById(accountNumber);
				 request.setAttribute("accounts", savingsAccount);
				 dispatcher = request.getRequestDispatcher("UpdateDetails.jsp");
				 dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			break;
		
		case "/updatesavings.mm":
			accountNumber = Integer.parseInt(request.getParameter("actnumber"));
			try {
				savingsAccount = savingsAccountService.getAccountById(accountNumber);
				String actHName = request.getParameter("holderName");
				savingsAccount.getBankAccount().setAccountHolderName(actHName);
				double actBal = Double.parseDouble(request.getParameter("accountbalance"));
				boolean isSalary = request.getParameter("salary").equalsIgnoreCase("no")?false:true;
				savingsAccount.setSalary(isSalary);
				savingsAccountService.updateAccount(savingsAccount);
				DBUtil.commit();
				response.sendRedirect("getAll.mm");
				
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		}
		
			
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
