import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;

public class XMLReaderApp {
    public static void main(String[] args) {
        try {
            // Replace with the path to your saved file
            File file = new File("data.xml"); // or "data.html" if it's HTML with embedded XML

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // Replace "record" with the relevant XML parent tag you're targeting
            NodeList nodes = doc.getElementsByTagName("record");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Example field names
                    String name = getTagValue("name", element);
                    String value = getTagValue("", element);

                    System.out.println("Name: " + name);
                    System.out.println("Value: " + value);
                    System.out.println("--------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper function to safely extract field values
    private static String getTagValue(String tag, Element element) {
        NodeList tagList = element.getElementsByTagName(tag);
        if (tagList.getLength() == 0) return "";
        NodeList list = tagList.item(0).getChildNodes();
        Node node = (Node) list.item(0);
        return node != null ? node.getNodeValue() : "";
    }
}
