import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLReaderApp {
    public static void main(String[] args) {
        try {
            // Path to your XML file
            File file = new File("C:\\Users\\User\\Desktop\\TaskToo\\tasktoo\\data.xml");

            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // Get all "record" elements
            NodeList recordList = doc.getElementsByTagName("record");

            // Set up a list of valid fields for validation
            Set<String> validFields = new HashSet<>(Arrays.asList("name", "postalZip", "region", "country", "address", "list"));

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);

            // Ask the user for the fields they want to see
            System.out.println("Enter the fields you want to display (comma separated, e.g., name, postalZip):");
            String userInput = scanner.nextLine();

            // Validate user input
            String[] selectedFields = userInput.split(",");
            List<String> validSelectedFields = new ArrayList<>();

            for (String field : selectedFields) {
                field = field.trim(); // Remove leading/trailing spaces
                if (validFields.contains(field)) {
                    validSelectedFields.add(field);
                } else {
                    System.out.println("Invalid field: " + field);
                }
            }

            // If no valid fields were selected, exit
            if (validSelectedFields.isEmpty()) {
                System.out.println("No valid fields selected. Exiting...");
                return;
            }

            // Create a JSON array to hold all the records
            JSONArray jsonRecords = new JSONArray();

            // Iterate through each record
            for (int i = 0; i < recordList.getLength(); i++) {
                Node record = recordList.item(i);
                if (record.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) record;
                    JSONObject jsonObject = new JSONObject();

                    // Add only the valid fields selected by the user
                    for (String field : validSelectedFields) {
                        NodeList nodeList = recordElement.getElementsByTagName(field);
                        if (nodeList.getLength() > 0) {
                            String fieldValue = nodeList.item(0).getTextContent();
                            jsonObject.put(field, fieldValue);
                        }
                    }

                    jsonRecords.put(jsonObject);
                }
            }

            // Print the JSON output
            System.out.println("Generated JSON output:");
            System.out.println(jsonRecords.toString(2)); // Pretty-print the JSON

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}