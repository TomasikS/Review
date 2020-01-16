/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sovy.review;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stefan.tomasik
 */
public class Database {

    public Connection createConnection() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        Connection connection;
        Properties properties = new Properties();

        String currentWorkingDir = System.getProperty("user.dir");
        String subor = currentWorkingDir + "/src//main//java//com//sovy//review//application.properties";

        ///   System.out.println("pom vypis"+subor);
        //properties.load(new java.io.FileInputStream("C://Users//stefan.tomasik//Documents//NetBeansProjects//Review//src//main//java//com//sovy//review//application.properties"));
        properties.load(new java.io.FileInputStream(subor));
        String url = properties.getProperty("host");
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        Class.forName(driver);

        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public List readData() throws SQLException, IOException {

        Connection connection = null;
        String sql = "SELECT * from review;";

        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "postgres");
        try {
            connection = createConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Review> zaners = new ArrayList();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("text");
                int hodnotenie = rs.getInt("hodnotenie");
                int idBook = rs.getInt("idBook");
                int idUser = rs.getInt("idUser");

                zaners.add(new Review((long) id, text, hodnotenie, idBook, idUser));
            }
        }
        connection.close();
        return zaners;
    }

    public void insertData(Review review) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO review (text,hodnotenie) VALUES (?, ?)";
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "postgres");
        Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        // statement.setInt(1, id);
        statement.setString(1, review.getText());
        statement.setInt(2, review.getHodnotenie());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new review was inserted successfully!");
        }
        connection.close();

    }

    void remove(Review review) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException {

        /*  String url = "jdbc:postgresql://localhost:5432/bookstore";
        String user = "postgres";
        String password = "postgres";
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "postgres");*/
        Connection connection = createConnection();
        String sql = "DELETE FROM review WHERE id=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, review.getId());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A review was deleted successfully!");
        }

    }

    public Review getZanerById(Long id) throws IOException, SQLException, FileNotFoundException, ClassNotFoundException {
        /* String url = "jdbc:postgresql://localhost:5432/bookstore";
        String user = "postgres";
        String password = "postgres";*/

        List<Review> reviewList = new ArrayList();
        Review zaner = null;
        Long idKniha = null;
        List<Long> list = new ArrayList();

        String dopyt = "SELECT id from Book where id>0;";
        Connection connectionBook = createConnection();
        PreparedStatement preparedStatementBook = connectionBook.prepareStatement(dopyt);
        ResultSet rss = preparedStatementBook.executeQuery();

        while (rss.next()) {
            idKniha = rss.getLong(1);
            list.add(idKniha);
        }

        connectionBook.close();

        Long idUsera = null;
        List<Long> listUser = new ArrayList();

        String dopytPouzivatel = "SELECT id from Pouzivatel where id>0;";
        Connection connectionUser = createConnection();
        PreparedStatement preparedStatementUser = connectionUser.prepareStatement(dopytPouzivatel);
        ResultSet rsss = preparedStatementUser.executeQuery();

        while (rsss.next()) {
            idUsera = rsss.getLong(1);
            listUser.add(idUsera);
        }

        connectionUser.close();

        // if (idKniha != null) {
        String query = "select *  from review where id=" + id;

        try (Connection connection = createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long idzaner = rs.getLong(1);
                String name = rs.getString(2);
                int hodnotenie = rs.getInt(3);
                int idBook = rs.getInt(4);
                int idUser = rs.getInt(5);
                zaner = (new Review(idzaner, name, hodnotenie, idBook, idUser));
                reviewList.add(zaner);
            }

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot connect to database " + ex.getMessage(), ex);
        }

        int j = 0;
        for (int i = 0; i < reviewList.size(); i++) {

            if ((reviewList.get(i).getIdUser() == listUser.get(i)) && (reviewList.get(i).getIdBook() == list.get(i))) {
                j = i;
            }
        }

        if (reviewList.size() > 0) {
            zaner = reviewList.get(j);
        } else {
            zaner = reviewList.get(0);
        }

        return zaner;
    }

    public void updateText(Review zaner) throws IOException, SQLException, ClassNotFoundException {
        /*  String url = "jdbc:postgresql://localhost:5432/bookstore";
        String user = "postgres";
        String password = "postgres";*/

        String sql = "update review set text = '" + zaner.getText() + "'where id=" + zaner.getId();

        try (Connection conn = createConnection();
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(sql);
            System.out.println("Database updated successfully ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
