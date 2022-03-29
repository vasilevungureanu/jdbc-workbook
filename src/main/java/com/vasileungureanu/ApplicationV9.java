package com.vasileungureanu;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.sql.DataSource;

public class ApplicationV9 {

  public static void main(String[] args) throws SQLException {
    DataSource dataSource = createDataSource();

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement stmt = connection.prepareStatement(
          "insert into" +
              " users (first_name, last_name, registration_date) " +
              "values (?,?,?)",
          Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, "[Some FirstName]");
        stmt.setString(2, "[Some LastName]");
        stmt.setObject(3, LocalDateTime.now());
        stmt.addBatch();

        stmt.setString(1, "[Some Other FirstName]");
        stmt.setString(2, "[Some Other LastName]");
        stmt.setObject(3, LocalDateTime.now());
        stmt.addBatch();

        final int[] insertCounts = stmt.executeBatch();
        System.out.println("I inserted " + Arrays.toString(insertCounts) + " rows");
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
