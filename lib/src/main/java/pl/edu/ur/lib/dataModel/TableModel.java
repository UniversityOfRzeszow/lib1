package pl.edu.ur.lib.dataModel;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class of table model.
 * @author Marcin
 */
public class TableModel extends AbstractTableModel {

protected String[] columnNames = {"Title","Author","Publishing House", "Year", "Descryption", "ISBN"};

    private List<Data> data;

    public TableModel (List<Data> data){
        this.data = data;
    }

    /**
     * Method to get colum count.
     * @return Column count.
     */
    public int getColumnCount() {
        return 6;
    }

    /**
     * Method to get row count.
     * @return Row count.
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Method to get column name.
     * @param column
     * @return Column names.
     */
     public String getColumnName(int column) {
         return columnNames[column];
     }

     /**
      * Method to get value in specyfic place of table.
      * @param row
      * @param column
      * @return Object.
      */
    public Object getValueAt(int row, int column) {

            switch (column){
            case 0: return data.get(row).getBook().getTitle();
            case 1: return data.get(row).getAuthor().getNameAndLastName();
            case 2: return data.get(row).getBook().getPublishingHouse();
            case 3: return data.get(row).getBook().getYear();
            case 4: return data.get(row).getBook().getDescription();
            case 5: return data.get(row).getBook().getID();
            default: return "N/A";
            }

    }
}