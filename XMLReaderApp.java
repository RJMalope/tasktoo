import org.json.JSONObject;
import org.json.JSONArray;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.*;

public class XMLReaderApp {

    // SAX handler to process the XML
    static class XMLHandler extends DefaultHandler {
        private JSONArray jsonRecords;
        private JSONObject currentRecord;
        private StringBuilder currentFieldValue;
        private Set<String> validFields;

        public XMLHandler(Set<String> validFields) {
            this.jsonRecords = new JSONArray();
            this.validFields = validFields;
        }

        // When we encounter the start of an element, we'll check if it's one of the valid fields
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            // If we're encountering a valid field, we need to prepare to store its value
            if (validFields.contains(qName)) {
                currentFieldValue = new StringBuilder();
            }

            // If we encounter the "record" element, we need to start a new record
            if ("record".equals(qName)) {
                currentRecord = new JSONObject();
            }
        }

        // When we encounter text content inside an element, we store it
        @Override
        public void characters(char[] ch, int start, int length) {
            if (currentFieldValue != null) {
                currentFieldValue.append(new String(ch, start, length));
            }
        }

        // When we encounter the end of an element, we save its content in the current record
        @Override
        public void endElement(String uri, String localName, String qName) {
            // If we were processing a valid field, add its value to the current record
            if (validFields.contains(qName)) {
                currentRecord.put(qName, currentFieldValue.toString().trim());
            }

            // If we've encountered the end of a record, add it to the records list
            if ("record".equals(qName)) {
                jsonRecords.put(currentRecord);
            }
        }

        // Retrieve the resulting JSON records
        public JSONArray getJsonRecords() {
            return jsonRecords;
        }
    }

    public static void main(String[] args) {
        try {
            // Path to your XML file
            File file = new File("C:\\Users\\User\\Desktop\\TaskToo\\tasktoo\\data.xml");

            // Set up the valid fields for validation
            Set<String> validFields = new HashSet<>(Arrays.asList("name", "postalZip", "region", "country", "address", "list"));

            // Set up a scanner for user input
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

            // Set up SAX parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler(new HashSet<>(validSelectedFields));

            // Parse the XML using SAX
            parser.parse(file, handler);

            // Retrieve the JSON output from the handler
            JSONArray jsonRecords = handler.getJsonRecords();

            // Print the JSON output
            System.out.println("Generated JSON output:");
            System.out.println(jsonRecords.toString(2)); // Pretty-print the JSON

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
