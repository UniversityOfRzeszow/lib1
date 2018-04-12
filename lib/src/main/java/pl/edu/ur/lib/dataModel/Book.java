package pl.edu.ur.lib.dataModel;

import java.util.LinkedList;

/**
 *
 * @author Marcin
 */
public class Book {

    private String bookId = "";
    private String title = "";
    private String publishingHouse = "";
    private int year;
    private String sYear;
    private String description;

    public Book(LinkedList list) {
        list = new LinkedList<Book>();
    }

    public Book(String isbn, String title) {
        this.bookId = isbn;
        this.title = title;
    }

    /**
     * Constructor of book object.
     *
     * @param ID
     * @param title
     * @param publishingHouse
     * @param year
     * @param description
     */
    public Book(String ID, String title, String publishingHouse, int year, String description) {
        this.bookId = ID;
        this.title = title;
        this.publishingHouse = publishingHouse;
        this.year = year;
        this.description = description;
    }

    /**
     * Constructor of book object.
     *
     * @param ID
     * @param title
     * @param publishingHouse
     * @param year
     * @param description
     */
    public Book(String ID, String title, String publishingHouse, String year, String description) {
        this.bookId = ID;
        this.title = title;
        this.publishingHouse = publishingHouse;
        this.sYear = year;
        this.description = description;
    }

    /**
     * Method to get the book ISBN.
     *
     * @return ISBN
     */
    public String getID() {
        return bookId;
    }

    /**
     * Method to set the book ISBN.
     *
     * @param ID.
     */
    public void setID(String ID) {
        this.bookId = ID;
    }

    /**
     * Method to get the book title.
     *
     * @return Title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set the book title.
     *
     * @param Title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get publisher of the book.
     *
     * @return Publisher.
     */
    public String getPublishingHouse() {
        return publishingHouse;
    }

    /**
     * Method to set publisher of the book.
     *
     * @param Publishing House.
     */
    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    /**
     * Method to get year of book.
     *
     * @return Year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Method to get year of book as a String.
     * @return Year (String type).
     */
    public String getStringYear() {
        return sYear;
    }

    /**
     * Method to set year of the book.
     * @param Year. 
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Method to get description of the book.
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set description of the book.
     * @param Description. 
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
