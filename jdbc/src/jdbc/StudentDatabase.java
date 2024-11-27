package jdbc;

import java.sql.CallableStatement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//public class Main {
//	public static void main(String[] args) throws ClassNotFoundException ,SQLException{
//		Class.forName("com.mysql.cj.jdbc.Driver");
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:/prac","root","Anisetty@2104");
//		System.out.println("Connection Successful");
//		
//	}
//
//}
public class StudentDatabase{
	private static Connection connection = null;
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)/*throws ClassNotFoundException*/ {
		StudentDatabase StudentDatabase = new StudentDatabase();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbUrl = "jdbc:mysql://localhost:3306/prac";
			String username = "root";
			String password = "Anisetty@2104";
			connection = DriverManager.getConnection(dbUrl, username, password); 
			System.out.println("Connection Successful");
			
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				insertRecord();
				break;
			case 2:
				showRecord();
				break;
			case 3:
				selectAllRecords();
				break;
			case 4:
				updatRecord();
				break;
			case 5:
				deleteRecord();
			case 6:
				transaction();
				break;
			case 7:
				batchProcessing();
				break;
			default:
				break;
				
			}
			
		}
		
		
		catch(Exception e) {
//			throw new ClassNotFoundException("Connection unsuccessful");
			System.out.println("Connection is Unsuccessful" +e.getMessage());
		}
		
		
		
	
	}
	
	private static void insertRecord() throws SQLException {
		System.out.println("Insert");
		System.out.println("Enter ur name");
		String name = sc.nextLine();
		sc.nextLine();
		System.out.println("Enter ur per");
		double percentage = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter ur address");
		String address = sc.nextLine();
		sc.nextLine();
		//String sql = "insert into student(name, percentage, address) values('Chopricaa', 90, 'CIT')";
		String sql = "insert into student(name, percentage, address) values(?,?,?)";
		PreparedStatement preparedstatement = connection.prepareStatement(sql);
		preparedstatement.setString(1, name);
		preparedstatement.setDouble(2, percentage);
		preparedstatement.setString(3, address);
		
		
		int rows = preparedstatement.executeUpdate();
		if(rows > 0) {
			System.out.println("Record inserted successfully");
			
		}
		
	}
	public static void showRecord() throws SQLException {
		System.out.println("Select record");
		//String sql = "select * from student where rollno=1";
		int number = sc.nextInt();
		//String sql = "select * from student";
		String sql = "select * from student where rollno=" +number;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			int rollno = result.getInt("rollno");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			
			System.out.println("Roll num is :"+rollno);
			System.out.println("name is  is :"+name);
			System.out.println("percenateg is :"+percentage);
			System.out.println("addresss is :"+address);
			
			
		}
		else {
			System.out.println("No records found");
		}
		
		
		
	
		
		
		
	}
	
	public static void selectAllRecords() throws SQLException {
		CallableStatement callableStatement = connection.prepareCall("{call GET_ALL}");
		ResultSet result = callableStatement.executeQuery();
		while(result.next()) {
			int rollno = result.getInt("rollno");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			
			System.out.println("Roll num is :"+rollno);
			System.out.println("name is  is :"+name);
			System.out.println("percentage is :"+percentage);
			System.out.println("addresss is :"+address);
			
			
		}
		
		
	}
	
	private static void updatRecord() throws SQLException {
		System.out.println("before updating the record");
		//String sql = "select * from student where rollno=1";
		int number= sc.nextInt();
		String sql = "select * from student where rollno="+number;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			int rollno = result.getInt("rollno");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			
			System.out.println("Roll num is :"+rollno);
			System.out.println("name is  is :"+name);
			System.out.println("percenateg is :"+percentage);
			System.out.println("addresss is :"+address);
			
			System.out.println("what to be updated");
			System.out.println("1.name");
			System.out.println("2.percentage");
			System.out.println("3.address");
			
			int choice = sc.nextInt();
			String sqlQuery = "update student set ";
			switch(choice) {
			case 1: 
				System.out.println("Name to be updated");
				System.out.println("enter name updated");
				String newname = sc.next();
				sqlQuery = sqlQuery+"name=? where rollno ="+number;
				PreparedStatement preparedstatement = connection.prepareStatement(sqlQuery);
				preparedstatement.setString(1, newname);
				int rows = preparedstatement.executeUpdate();
				if(rows > 0) {
					System.out.println("Record updated successfully");
					
				}
				break;
			case 2:
				System.out.println("Enter Percentage");
				double newper = sc.nextInt();
				sqlQuery = sqlQuery+"percentage=? where rollno ="+number;
				PreparedStatement preparedstatement1 = connection.prepareStatement(sqlQuery);
				preparedstatement1.setDouble(1, newper);
				int rows1 = preparedstatement1.executeUpdate();
				if(rows1 > 0) {
					System.out.println("Record updated successfully");
					
				}
				break;
			case 3:
				System.out.println("Enter Address");
				String newadd = sc.next();
				sqlQuery = sqlQuery + "address= ? where rollno="+number;
				PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
				preparedStatement1.setString(1, newadd);
				int rows11 = preparedStatement1.executeUpdate();
				if(rows11>0) {
					System.out.println("Record updates successfulley");
				}
				else {
					System.out.println("Record not found");
				}
			
				break;
				
				
			}
			
		}
		
		
	}
	private static void deleteRecord() throws SQLException {
		System.out.println("Delete record");
		//String sql="delete from student where rollno=1";
		int number = sc.nextInt();
		String sql = "delete from student where rollno="+number;
		Statement statement = connection.createStatement();
		int rows = statement.executeUpdate(sql);
		if(rows>0) {
			System.out.println("Records deleted successfully");
		}
		
		
	}
	private static void transaction() throws SQLException {
		//connection.setAutoCommit(false);
		String sql1 = "insert into student(name, percentage, address) values ('geet',75, 'andhra')";
		//String sql2= "insert into student(name, percentage, address) values ('harini',80, 'kerala')";
		String sql2 = "update student set address = 'Hy' where name='raj'";
		PreparedStatement preparedstatement = connection.prepareStatement(sql1);
		int row1 = preparedstatement.executeUpdate();
		
		preparedstatement = connection.prepareStatement(sql2);
		int row2 = preparedstatement.executeUpdate();
		if(row1 > 0 && row2 > 0) {
			System.out.println("Record updated successfully");
			
		}
			
	}
	
	private static void batchProcessing() throws SQLException {
		//connection.setAutoCommit(false);
		
		String sql1 = "insert into student(name, percentage, address) values ('eswari',70, 'AP')";
		//String sql2="insert into student(name, percentage, address) values ('raj',35, 'JP')";
		String sql2 = "update student set address = 'Hyd' where name='raj'";
		String sql3="insert into student(name, percentage, address) values ('pinky',65, 'paris')";
		String sql4="insert into student(name, percentage, address) values ('gnapika',55, 'hp')";
		String sql5="insert into student(name, percentage, address) values ('lasya',30, 'punjab')";
		
		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		
		int[] rows = statement.executeBatch();
		for(int i:rows) {
			if(i>0) {
				continue;
			}
			else {
				connection.rollback();
				
			}
			connection.commit();
		}
	}
 
	
	
}
