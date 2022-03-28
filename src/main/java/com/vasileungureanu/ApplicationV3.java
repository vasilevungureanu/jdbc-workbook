package com.vasileungureanu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationV3 {

  public static void main(String[] args) throws SQLException {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:h2:~/mydatabase",
        "sa",
        "s3cr3tPassword"
    )) {
      System.out.println("connection.isValid(0) = " + connection.isValid(0));
    }
  }
}
