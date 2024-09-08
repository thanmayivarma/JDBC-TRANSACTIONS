package TransactionManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionDemo {
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/Adjava";
	static final String USERNAME = "root";
	static final String PASSWORD = "root";

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Scanner scanner = null;
		try {
			// establish connection
			connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// create the statement object
			statement = connection.createStatement();

			// display data before transaction
			System.out.println("Data before transaction ");
			ResultSet rsBefore = statement.executeQuery("select*from accounts");
			while (rsBefore.next()) {
				System.out.println(rsBefore.getString(1) + "\t" + rsBefore.getDouble(2));
			}

			// start transaction
			System.out.println("\n Transaction Begins ");

			// disable autocommit mode
			connection.setAutoCommit(false);
			statement.executeUpdate("update accounts set balance=balance-10000 where name='ASHEEN'");
			statement.executeUpdate("update accounts set balance=balance+10000 where name='JAGGU' ");

			System.out.println("Can you please confirm this transaction of 10000[Yes/No]");

			// confirm the transaction
			scanner = new Scanner(System.in);
			String option = scanner.next();

			if (option.equalsIgnoreCase("yes")) {
				connection.commit();
				System.out.println("Transaction Committed");
			} else {
				connection.rollback();
				System.out.println("Transaction Rollbacked");
			}
			System.out.println("Data after transaction ");
			ResultSet rsAfter = statement.executeQuery("select*from accounts");
			while (rsAfter.next()) {
				System.out.println(rsAfter.getString(1) + "\t" + rsAfter.getDouble(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			scanner.close();
		}
	}
}
