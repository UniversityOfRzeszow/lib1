package pl.edu.ur.lib.examples;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * Example of postgres connection and procedure calling
 * @author Asia
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO zad 4, 5

        /*
        Example connection to database with
        SID = testdb
        login = postgres
        passwd = postgres
        
        localhost address can be changed to any pgdb address
        */
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb", "postgres", "postgres");

        System.out.println("connection successful");

        /*
        Example of procedure calling.
        Procedure body can be found in lib_script.sql file.
        */
        CallableStatement proc = con.prepareCall("select insertMan(?)");
        proc.setString(1, "Kamil");
        proc.execute();

        PreparedStatement banan = con.prepareStatement("SELECT * FROM db.man");
        banan.execute();

        ResultSet rs = banan.executeQuery();

        while (rs.next()) {
            String lastName = rs.getString("name");
            System.out.println(lastName + "\n");
        }
        
        rs.close();
        banan.close();
        con.close();
    }
}
