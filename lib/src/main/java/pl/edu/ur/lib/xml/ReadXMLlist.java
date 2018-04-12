package pl.edu.ur.lib.xml;

import pl.edu.ur.lib.dataModel.Data;
import java.util.ArrayList;

/**
 * Class of list who can contain files of Data class.
 *
 * @author Marcin
 */
public class ReadXMLlist {

    private ArrayList<Data> listOfData;

    /**
     * Constructor of readXMLlist object.
     */
    public ReadXMLlist() {
        this.listOfData = new ArrayList<Data>();
    }

    /**
     * Method to get size of list.
     * @return size of list
     */
    public int size() {
        return listOfData.size();
    }

    /**
     * Method to add Data type object to list.
     * @param data 
     */
    public void addData(Data data) {
        listOfData.add(data);
    }

    /**
     * Method to get list of data from list.
     * @return list of data
     */
    public ArrayList<Data> getListOfData() {
        return listOfData;
    }

    /**
     * Method to get data from list.
     * @param i
     * @return Data
     */
    public Data getData(int i) {
        return listOfData.get(i);
    }
}
