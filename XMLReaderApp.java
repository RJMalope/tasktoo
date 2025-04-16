import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class XMLReaderApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for which fields to include
        System.out.println("Enter the fields you want to include in the JSON output (comma-separated):");
        String input = scanner.nextLine();
        String[] requestedFields = input.split(",");

        // Clean field names and store in a Set
        Set<String> fieldSet = new HashSet<>();
        for (String field : requestedFields) {
            fieldSet.add(field.trim().toLowerCase());
        }

        try {
            File file = new File("C:\\Users\\User\\Desktop\\TaskToo\\tasktoo\\data.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList records = doc.getElementsByTagName("record");

            JSONArray jsonRecords = new JSONArray();

            for (int i = 0; i < records.getLength(); i++) {
                Node node = records.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    JSONObject jsonObject = new JSONObject();

                    for (String field : fieldSet) {
                        String value = getTagValue(field, element);
                        if (!value.isEmpty()) {
                            jsonObject.put(field, value);
                        }
                    }

                    if (!jsonObject.isEmpty()) {
                        jsonRecords.put(jsonObject);
                    }
                }
            }

            System.out.println(jsonRecords.toString(4)); // Pretty print with indentation

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
