package pl.edu.ur.lib.dataModel;

import java.util.LinkedList;

/**
 *
 * @author Marcin
 */
public class Data {
    Author author;
    Book book;
    LinkedList<Data> lista;
    
    public Data(LinkedList lista){
        lista = new LinkedList<Data>();
    }
    
    public LinkedList getLista(){
        return lista;
    }
    
    /**
     * Constructor of data object.
     * Data object include a book and the author objects.
     * @param author
     * @param book 
     */
    public Data(Author author, Book book) {
        this.author = author;
        this.book = book;
    }

    /**
     * Method to get the author.
     * @return Author.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Method to set the author.
     * @param Author. 
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Method to get a book.
     * @return Book.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Method to set a book.
     * @param Book. 
     */
    public void setBook(Book book) {
        this.book = book;
    }
    
    
    
}
