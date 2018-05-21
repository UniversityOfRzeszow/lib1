````java
public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here

        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lib", "postgres", "postgres");

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM authors");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
        }

        connection.close();
    }
````
