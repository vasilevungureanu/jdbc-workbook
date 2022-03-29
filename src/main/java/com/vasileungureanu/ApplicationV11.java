package com.vasileungureanu;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.sql.DataSource;

public class ApplicationV11 {

  public static void main(String[] args) throws SQLException {
    DataSource dataSource = createDataSource();

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement stmt = connection.prepareStatement(
          "update users set first_name=concat(first_name, ?) where id > ?"
      )) {
        stmt.setString(1, "-yay!");
        stmt.setInt(2, 2);

        int updatedRows = stmt.executeUpdate();

        System.out.println("I just updated " + updatedRows + " rows");
      }

      try (PreparedStatement stmt = connection.prepareStatement("select * from users")) {
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String firstName = resultSet.getString("first_name");
          String lastName = resultSet.getString("last_name");
          LocalDateTime registrationDate = resultSet.getObject(
              "registration_date",
              LocalDateTime.class
          );

          System.out.println("Found user: " + id + " | " + firstName +
              " | " + lastName + " | " + registrationDate);
        }
      }
    }
  }

  private static DataSource createDataSource() {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl("jdbc:h2:~/mydatabase2;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
    ds.setUsername("sa");
    ds.setPassword("s3cr3tPassword");

    return ds;
  }
}
