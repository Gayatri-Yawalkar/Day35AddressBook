package com.bridgelabz.addressbook;
//Uc20
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bridgelabz.employeepayroll.EmployeePayrollData;
public class AddressBookDb {
	Map<String, MultipleContacts> map;
	PreparedStatement prepareStatement;
	Scanner sc=new Scanner(System.in);
	public void mapAddressBookRecords(Map<String, MultipleContacts> addressBookMap) {
		map=addressBookMap;
		int choice;
		int flag=0;
		while(flag==0) {
			choice=takeUserChoice();
			switch(choice) {
				case 1:
					readRecordsFromDatabase();
					break;
				case 2:
					flag=1;
					break;
				default:
					System.out.println("You have Entered Wrong Choice.Please enter option between 1 to 2");
			}
		}
	}
	private int takeUserChoice() {
		System.out.println("Database Operations:");
		System.out.println("1.Display Address Book Record");
		System.out.println("2.Update Phone No.");
		System.out.println("3.Exit");
		System.out.println("Enter choice between 1 to 3");
		int choice=sc.nextInt();
		return choice;
	}
	public int addNewPersonToDb(String firstLastName,String address,String city,String state,String zip,String phoneNum,String email,LocalDate date) {
		int personId=-1;
		int rowAffected=-1;
		Connection connection=null;
		Contacts contacts=null;
		try {
			connection=this.getConnection();
			connection.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		try(Statement statement=connection.createStatement()) {
			String sql=String.format("INSERT INTO person(first_name,phone_no,email,insertion_date)"+
					   "VALUES('%s','%s','%s','%s');",firstLastName,phoneNum,email,Date.valueOf(date));
			rowAffected=statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
			if(rowAffected==1) {
				ResultSet resultSet=statement.getGeneratedKeys();
				if(resultSet.next()) {
					personId=resultSet.getInt(1);
					System.out.println("Person Id="+personId);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return rowAffected;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try(Statement statement=connection.createStatement()) {
			String sql=String.format("INSERT INTO address_details"+
					    "(id,address,city,state,zip) VALUES"+
					    "(%d,'%s','%s','%s','%s');",personId,address,city,state,zip);
			rowAffected=statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return rowAffected;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rowAffected;
	}
	public List<Contacts> readRecordsFromDatabase() {
		String sql="SELECT * FROM person;";
		return this.getAddressDataUsingDB(sql);
		}
	public List<Contacts> getAddressBookData(String name){
		List<Contacts> addressBookList=null;
		if(this.prepareStatement==null) {
			this.prepareStatementForAddressBookData();
		}
		try {
			prepareStatement.setString(1,name);
			ResultSet resultSet=prepareStatement.executeQuery();
			addressBookList=this.getAddressBookData(resultSet);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}
	private void prepareStatementForAddressBookData() {
		try {
			Connection connection=this.getConnection();
			String sql="SELECT * FROM person WHERE name=?";
			prepareStatement=connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private List<Contacts> getAddressDataUsingDB(String sql) {
		List<Contacts> addressBookList=new ArrayList<>();
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			addressBookList=this.getAddressBookData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}
	private List<Contacts> getAddressBookData(ResultSet resultSet) {
		List<Contacts> addressBookList=new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("first_name");
				String phone=resultSet.getString("phone_no");
				String email=resultSet.getString("email");
				addressBookList.add(new Contacts(id, name,phone,email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}
	private Connection getConnection() throws SQLException {
		String jdbcUrl="jdbc:mysql://localhost:3306/address_book?useSSL=false";
		String userName="root";
		String password="root";
		Connection con;
		System.out.println("Connecting to database:"+jdbcUrl);
		con=DriverManager.getConnection(jdbcUrl, userName, password);
		System.out.println("Connection is successfull");
		return con;
	}
	public List<Contacts> getAddressBookDataWithinDateRange(LocalDate startDate, LocalDate endDate) {
		String sql=String.format("SELECT * FROM person WHERE insertion_date BETWEEN '%s' AND '%s';",
								  Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getAddressDataUsingDB(sql);
	}
	public Map<String,Integer> getCountByCityAndState(String choice){
		String sql=null;
		if(choice.equalsIgnoreCase("city")) {
			sql="select city,count(city) from address_details group by city;";
		} else if(choice.equalsIgnoreCase("state")) {
			sql="select state,count(state) from address_details group by state;";
		} else {
			System.out.println("You have Entered wrong choice");
		}
		Map<String,Integer> countMap=new HashMap<>();
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			ResultSet resultSet=null;
			if(sql!=null) {
				resultSet=statement.executeQuery(sql);
			}
			if(resultSet!=null) {
				while(resultSet.next()) {
					String cityOrState=resultSet.getString(1);
					Integer count=resultSet.getInt(2);
					countMap.put(cityOrState,count);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return countMap;
	}
	public int updatePhoneNum(String name,String phone_no) {
		String sql=String.format("update person set phone_no='%s' where first_name='%s';",phone_no,name);
		try (Connection connection=this.getConnection();){
			Statement statement=connection.createStatement();
			return statement.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
