package pl.edu.ur.lib.dataModel;

import java.util.LinkedList;

/**
 *
 * @author Marcin
 */
public class Author {

    private int authorId = 0;
    private String name = "";
    private String lastName = "";
    
        public Author(LinkedList list){
        list = new LinkedList<Author>();
    }
        
        /**
         * Constructor of author's.
         * @param ID
         * @param name
         * @param lastname
         */

    public Author(int ID, String name, String lastname) {
        this.authorId = ID;
        this.name = name;
        this.lastName = lastname;
    }
    
    /**
     * Constructor of the author object.
     * @param name
     * @param lastName 
     */
    public Author(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
    }
    
    
    /**
     * Method to get the author id value.
     * @return Author ID.
     */
    public int getID() {
        return authorId;
    }

    /**
     * Method to set the author id value.
     * @param ID.
     */
    public void setID(int ID) {
        this.authorId = ID;
    }

    /**
     * Method to get the author name.
     * @return Name of author.
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the author name.
     * @param Name. 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method to get the author lastname.
     * @return Lastname of author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to set the author lastname.
     * @param Lastname. 
     */
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }
    
    /**
     * Method to get the author name and lastname
     * @return Name and lastname of author.
     */
    public String getNameAndLastName() {
        return name + " " + lastName;
    }

}
