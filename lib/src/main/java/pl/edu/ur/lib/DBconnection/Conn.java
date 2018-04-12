package pl.edu.ur.lib.DBconnection;

import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Class to connect database operations.
 *
 * @author Marcin
 */
public class Conn {

    //public static final String DRIVER = "org.firebirdsql.jdbc.FBDriver";
    public static String DB_SID = "lib";
    public static String login = "";
    public static String pass = "";
    public Connection conn;
    Statement statement;
    private String SQLcommand;

    /**
     * Constructor of connection object. Driver is loaded here.
     */
//    public Conn() {
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "No driver here " + ex);
//        }
//
//    }

    /**
     * Method to create connection to database.
     *
     * @return True if connection is successful or false if it is not.
     */
    public boolean openConnection() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/"+ DB_SID, login, pass);
                    //DriverManager.getConnection("jdbc:firebirdsql://localhost/" + DB_URL, login, pass);
            statement = conn.createStatement();
            return true;
        } catch (SQLException ex) {
            return false;

        }
    }

    /**
     * Method to close connection with database.
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection closing problem. " + ex);
        }
    }

    /**
     * Sql query to load books from database.
     *
     * @return List of books.
     */
    public List<Book> loadBooks() {
        String SQLcommand = "select *"
                + " from KSIAZKI";

        String bookId = "";
        String title = "";

        Book b;
        java.util.List<Book> bookList = new LinkedList<Book>();

        try {
            ResultSet result = statement.executeQuery(SQLcommand);
            while (result.next()) {
                bookId = result.getString("ISBN");
                title = result.getString("TYTUL");

                b = new Book(bookId, title);
                bookList.add(b);
            }
            return bookList;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych 1" + ex);
            return null;
        }

    }

    /**
     * Method to load all data from database.
     *
     * @return List of all the authors and books.
     */
    public java.util.List loadAllData() {
        String SQLcommand = "SELECT * FROM books "
                + "JOIN books_authors ON books.id = books_authors.book_id\n" +
                "  JOIN authors \n" +
               "    ON authors.id = books_authors.author_id";
        String bookId = "";
        String title = "";
        String publish = "";
        int year = 0;
        String desc = "";
        int authorId = 0;
        String name = "";
        String lastname = "";

        Book b;
        Author a;
        java.util.List<Data> bookList = new LinkedList<Data>();

        try {
            ResultSet result = statement.executeQuery(SQLcommand);
            while (result.next()) {
                bookId = result.getString("id");
                title = result.getString("title");
                publish = result.getString("publisher");
                year = result.getInt("year");
                desc = result.getString("description");
                authorId = result.getInt("author_id");
                name = result.getString("name");
                lastname = result.getString("surname");
                b = new Book(bookId, title, publish, year, desc);
                a = new Author(authorId, name, lastname);
                bookList.add(new Data(a, b));
            }
            return bookList;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych 2" + ex);
            return null;
        }

    }

    /**
     * Method to load authors from database.
     *
     * @return List of authors.
     */
    public java.util.List loadAuthor() {
        String SQLcommand = "SELECT * FROM authors";

        int authorId = 0;
        String name = "";
        String lastname = "";

        Author a;
        java.util.List<Author> listAuthor = new LinkedList<Author>();

        try {
            ResultSet result = statement.executeQuery(SQLcommand);
            while (result.next()) {

                authorId = result.getInt("id");
                name = result.getString("name");
                lastname = result.getString("surname");

                a = new Author(authorId, name, lastname);
                listAuthor.add(a);
            }
            return listAuthor;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych 3" + ex);
            return null;
        }

    }

    /**
     * Method to load books of selected authors.
     *
     * @param authorID
     * @return List of author's books.
     */
    public java.util.List loadAuthorBooks(int authorID) {

        String SQLcommand = "select *"
                + " from KSIAZKI"
                + " join KSIAZKI_AUTORZY using(ISBN)"
                + " join AUTORZY using(ID_AUTORA)"
                + "WHERE ID_AUTORA = " + authorID;

        String bookId = "";
        String title = "";
        String publish = "";
        int year = 0;
        String desc = "";
        int authorId = 0;
        String name = "";
        String lastname = "";

        Book b;
        Author a;
        java.util.List<Data> bookList = new LinkedList<Data>();

        try {
            ResultSet result = statement.executeQuery(SQLcommand);
            while (result.next()) {
                bookId = result.getString("ISBN");
                title = result.getString("TYTUL");
                publish = result.getString("WYDAWNICTWO");
                year = result.getInt("ROK");
                desc = result.getString("OPIS");
                authorId = result.getInt("ID_AUTORA");
                name = result.getString("IMIE");
                lastname = result.getString("NAZWISKO");
                b = new Book(bookId, title, publish, year, desc);
                a = new Author(authorId, name, lastname);
                bookList.add(new Data(a, b));
            }
            return bookList;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się pobrać danych 4" + ex);
            return null;
        }
    }

    /**
     * Method to gain max value of ID_AUTORA from database.
     *
     * @return ID.
     */
    public int returnIdCount() {
        int ID = 0;
        try {
            ResultSet result = statement.executeQuery(("SELECT * FROM AUTORZY WHERE ID_AUTORA IN (SELECT MAX(ID_AUTORA) FROM AUTORZY)"));
            while (result.next()) {
                ID = result.getInt("ID_AUTORA");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ID problem." + ex);

        }
        return ID;
    }

    /**
     * Method to add a new author to database.
     *
     * @param AUTORZY
     * @return True if add or false if it is not.
     */
    public boolean addAuthor(Author AUTORZY) {

        int id = checkAuthorID(AUTORZY.getLastName());
        if (id != 0) {
            //JOptionPane.showMessageDialog(null, "Author already exist in base.");
        } else {

            try {

                PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO AUTORZY "
                        + "(ID_AUTORA, IMIE, NAZWISKO) VALUES(?,?,?);");
                prepStmt.setInt(1, AUTORZY.getID());
                prepStmt.setString(2, AUTORZY.getName());
                prepStmt.setString(3, AUTORZY.getLastName());
                prepStmt.execute();
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Can't insert new record to database" + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Method to add a new book to database.
     *
     * @param KSIAZKI
     * @return True if insert operation is sucessful and false if it is not.
     */
    public boolean addBook(Book KSIAZKI) {
        
        String isbn = checkBookISBN(KSIAZKI.getID());
        
        
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO KSIAZKI (ISBN, TYTUL, WYDAWNICTWO, ROK, OPIS) VALUES(?,?,?,?,?);");
            prepStmt.setString(1, KSIAZKI.getID());
            prepStmt.setString(2, KSIAZKI.getTitle());
            prepStmt.setString(3, KSIAZKI.getPublishingHouse());
            prepStmt.setInt(4, KSIAZKI.getYear());
            prepStmt.setString(5, KSIAZKI.getDescription());
            prepStmt.execute();
            return true;
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Can't insert new record to database" + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to add new bond between author and book to database.
     *
     * @param KSIAZKI_AUTORZY
     * @return True if insert operation is succesfull and false if it is not.
     */
    public boolean addBound(Data KSIAZKI_AUTORZY) { 
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO KSIAZKI_AUTORZY (ISBN, ID_AUTORA) VALUES(?,?);");
            prepStmt.setString(1, KSIAZKI_AUTORZY.getBook().getID());
            prepStmt.setInt(2, KSIAZKI_AUTORZY.getAuthor().getID());
            prepStmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can't insert new record to database" + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to check ID of author's with selected last name. Used with add
     * operation to check that autor is in the base or not.
     *
     * @param oldLastName
     * @return Author ID.
     */
    public int checkAuthorID(String oldLastName) {
        int id = 0;
        String commandId = "SELECT ID_AUTORA FROM AUTORZY"
                + " WHERE NAZWISKO = '" + oldLastName + "';";

        try {
            ResultSet result = statement.executeQuery(commandId);
            while (result.next()) {
                id = result.getInt("ID_AUTORA");
            }
            return id;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Author with this ID already exist." + ex);
            return id;
        }

    }

    /**
     * Method to check bond between selected author and selected book
     *
     * @param isbn, authorId
     * @return ID if bond between book isbn and author ID exist.
     */
    public int checkBond(String isbn, int id) {

        int idTemp = 0;
        String commandId = "SELECT ID_AUTORA FROM KSIAZKI_AUTORZY"
                + " WHERE ISBN = '" + isbn + "' AND ID_AUTORA =" + id + ";";

        try {
            ResultSet result = statement.executeQuery(commandId);
            while (result.next()) {
                idTemp = result.getInt("ID_AUTORA");
            }
            return idTemp;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can't update data!" + ex.getMessage());
            return idTemp;
        }

    }

    /**
     *
     * Method which checks author's ID after edition. When new last name of author
     * already exists in base, add operation doesn't add the author but it will add new bond
     * between the books of old the author and the new one. If new author doesnt't exist in base
     * after edition, add operation will add the author.
     *
     * @param newLastName
     * @return ID.
     */
    public int checkNewAuthorID(String newLastName) {
        int id = 0;
        String commandId = "SELECT ID_AUTORA FROM AUTORZY"
                + " WHERE NAZWISKO = '" + newLastName + "';";

        try {
            ResultSet result = statement.executeQuery(commandId);
            while (result.next()) {
                id = result.getInt("ID_AUTORA");
            }
            return id;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can't update data!" + ex.getMessage());
            return id;
        }

    }

    /**
     * Method to check ISBN of selected book from database.
     *
     * @param isbn
     * @return ISNB.
     */
    public String checkBookISBN(String isbn) {
        //String isbn = "";
        String commandId = "SELECT ISBN FROM KSIAZKI"
                + " WHERE ISBN = '" + isbn + "';";

        try {
            ResultSet result = statement.executeQuery(commandId);
            while (result.next()) {
                isbn = result.getString("ISBN");
                return isbn;
            }
            return "";
        } catch (SQLException ex) {
            return "";
        }

    }

    /**
     * Method to update the author after edition.
     *
     * @param oldLastName
     * @param firstName
     * @param nLastName
     * @return True if update successful or false if it is not.
     */
    public boolean updateAuthor(String oldLastName, String firstName, String nLastName) {

        int id = checkAuthorID(oldLastName);
        if (id != 0) {
            String command
                    = "UPDATE AUTORZY "
                    + "SET IMIE=?, "
                    + "NAZWISKO=? "
                    + "WHERE ID_AUTORA=" + id + ";";
            try {
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.setString(1, firstName);
                pstmt.setString(2, nLastName);
                pstmt.execute();
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Can't update data!" + ex.getMessage());
                return false;
            }
        } else {

            id = returnIdCount();
            id = id + 1;
            Author a = new Author(id, firstName, nLastName);
            addAuthor(a);

            return true;
        }
    }

    /**
     * Method to update a bond after edition.
     *
     * @param isbn
     * @param firstName
     * @param nLastName
     * @return True if update successful or false if it is not.
     */
    public boolean updateBond(String isbn, String firstName, String nLastName) {
        String command
                = "UPDATE AUTORZY "
                + "SET FIRST_NAME=?, "
                + "LAST_NAME=?, "
                + "WHERE ISBN='" + isbn + "';";
        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setString(1, firstName);
            pstmt.setString(2, nLastName);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can't update data!" + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to update a book in database after edition.
     *
     * @param isbn
     * @param title
     * @param publishingHouse
     * @param year
     * @param description
     * @param oldLastName
     * @return True if update successful or false if it is not.
     */
    public boolean updateBook(String isbn, String title, String publishingHouse, int year, String description, String oldLastName) {
        String command
                = "UPDATE KSIAZKI "
                + "SET TYTUL=?, "
                + "WYDAWNICTWO=?, "
                + "ROK=?, "
                + "OPIS=? "
                + "WHERE ISBN='" + isbn + "';";
        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setString(1, title);
            pstmt.setString(2, publishingHouse);
            pstmt.setInt(3, year);
            pstmt.setString(4, description);

            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can't update data!" + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to check the author in database. If he exists already or not.
     *
     * @param firstName
     * @param lastName
     * @return True if update successful or false if it is not.
     */
    public boolean checkAuthorInBase(String firstName, String lastName) {

        String command
                = "SELECT ID_AUTORA "
                + "FROM AUTORZY "
                + "WHERE LAST_NAME='" + lastName + "' and IMIE='" + firstName + ";";
        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error!" + ex.getMessage());
            return false;
        }

    }

    /**
     * Method to delete the author from database, from table AUTORZY.
     *
     * @param lastName
     * @return True if update successful or false if it is not.
     */
    public boolean deleteAuthorFromAuthors(String lastName) {

        int id = checkAuthorID(lastName);

        String command
                = "DELETE "
                + "FROM AUTORZY "
                + "WHERE ID_AUTORA= " + id + ";";

        try {
            PreparedStatement pstmt = conn.prepareStatement(command);

            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete author error." + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to delete the bond between the author and the book. Used with delete author
     * operation.
     *
     * @param lastName
     * @return True if update successful or false if it is not.
     */
    public boolean deleteBondFromBonds(String lastName) {

        int id = checkAuthorID(lastName);

        String command
                = "DELETE "
                + "FROM KSIAZKI_AUTORZY "
                + "WHERE ID_AUTORA= " + id + "";
              //  + "AND ISBN = '" +ISBN+"';";

        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            //pstmt.setInt(1, id);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete bond error." + ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteBondFromBondsTitle(String title) {

        String id = checkBookISBN(title);

        String command
                = "DELETE "
                + "FROM KSIAZKI_AUTORZY "
                + "WHERE ISBN= '" + id + "'";
              //  + "AND ISBN = '" +ISBN+"';";

        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            //pstmt.setInt(1, id);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete bond error." + ex.getMessage());
            return false;
        }
    }

    /**
     * Method to delete a book from database.
     *
     * @param title
     * @return True if update successful or false if it is not.
     */
    public boolean deleteBook(String title) {
        String isbn = checkBookISBN(title);

        String command
                = "DELETE "
                + "FROM KSIAZKI "
                + "WHERE ISBN= '" + isbn + "'";

        try {
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Delete book error." + ex.getMessage());
            return false;
        }

    }

//    public boolean deleteAuthorAndBooks(String lastName) {
//
//        int id = checkAuthorID(lastName);      
//        String command
//                = "DELETE "
//                + "FROM KSIARZKI "
//                + "WHERE ISBN= " + id + ";";
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(command);
//
//            pstmt.execute();
//            return true;
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Delete author error." + ex);
//            return false;
//        }
//    }
}
