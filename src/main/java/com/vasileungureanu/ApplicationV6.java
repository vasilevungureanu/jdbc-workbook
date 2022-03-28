package com.vasileungureanu;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ApplicationV6 {

  public static void main(String[] args) throws SQLException {
    DataSource dataSource = createDataSource();

    try (Connection connection = dataSource.getConnection()) {
      System.out.println("connection.isValid(0) = " + connection.isValid(0));
    }
  }

  private static DataSource createDataSource() {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl("jdbc:h2:~/mydatabase2");
    ds.setUsername("sa");
    ds.setPassword("s3cr3tPassword");

    return ds;
  }
}
