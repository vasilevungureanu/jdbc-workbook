package com.vasileungureanu;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

public class ApplicationV13 {

  public static void main(String[] args) throws SQLException {
    DataSource dataSource = createDataSource();

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement stmt = connection.prepareStatement(
          "select * from users where id > ?")) {

        stmt.setInt(1, 2);

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
          System.out.println(resultSet.getInt("id"));
        }
      }
    }
  }

  private static DataSource createDataSource() {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl("jdbc:h2:~/mydatabase2;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
    ds.setUsername("sa");
    ds.setPassword("s3cr3tPassword");

    return ProxyDataSourceBuilder
        .create(ds)
        .logQueryToSysOut()
        .build();
  }
}
