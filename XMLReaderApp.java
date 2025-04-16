import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;
import java.util.*;

public class XMLReaderApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for fields to print
        System.out.println("Enter the fields you want to print (comma-separated), e.g. name,region,country:");
        String input = scanner.nextLine();
        String[] requestedFields = input.split(",");

        // Clean up field names (remove spaces)
        Set<String> fieldSet = new HashSet<>();
        for (String field : requestedFields) {
            fieldSet.add(field.trim().toLowerCase());
        }

        try {
            // Update this path if needed
            File file = new File("C:\\Users\\User\\Desktop\\TaskToo\\tasktoo\\data.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList records = doc.getElementsByTagName("record");

            for (int i = 0; i < records.getLength(); i++) {
                Node node = records.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("Record " + (i + 1));
                    for (String field : fieldSet) {
                        String value = getTagValue(field, element);
                        if (!value.isEmpty()) {
                            System.out.println(field + ": " + value);
                        }
                    }
                    System.out.println("-------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) return "";
        NodeList children = nodeList.item(0).getChildNodes();
        Node node = (children != null && children.getLength() > 0) ? children.item(0) : null;
        return node != null ? node.getNodeValue() : "";
    }
}
