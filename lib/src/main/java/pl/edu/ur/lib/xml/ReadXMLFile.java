package pl.edu.ur.lib.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;

/**
 *
 * @author Marcin
 */
public class ReadXMLFile {

    /**
     * Method to read XML file.
     *
     * @param url
     * @return ReadXMLList
     */
    public ReadXMLlist readXMLFile(String url) {
        ReadXMLlist rXML = new ReadXMLlist();

        try {

            File xmlUrl = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlUrl);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("book");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    rXML.addData(new Data(
                            new Author(eElement.getElementsByTagName("name").item(0).getTextContent(),
                                    eElement.getElementsByTagName("lastName").item(0).getTextContent()),
                            new Book(
                                    eElement.getElementsByTagName("isbn").item(0).getTextContent(),
                                    eElement.getElementsByTagName("title").item(0).getTextContent(),
                                    eElement.getElementsByTagName("publishHouse").item(0).getTextContent(),
                                    Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent()),
                                    eElement.getElementsByTagName("description").item(0).getTextContent())));
                }
            }
            return rXML;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
