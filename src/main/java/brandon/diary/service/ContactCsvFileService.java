package brandon.diary.service;

import brandon.diary.model.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactCsvFileService {
    private static final String csv = "/contact.csv";

    private int currentMaxId = 0;

    public List<Contact> readCsvFile() throws IOException, ParseException {
        System.out.println("loading csv: " + csv);
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(csv)))) {
            List<Contact> contactList = new ArrayList<>();

            String[] headerLine = csvReader.readNext();

            if (headerLine == null) {
                System.out.println("CSV file is empty at: " + csv);

                return contactList;
            }

            System.out.println("headerLine = " + headerLine);

            Map<String, Integer> headerMap = getHeaderMap(headerLine);

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Contact contact = parseLine(headerMap, line);
                contactList.add(contact);
                currentMaxId = Math.max(currentMaxId, contact.getId());
            }

            System.out.println("number of contacts loaded from csv = " + contactList.size());

            return contactList;
        }
    }

    public void writeCsvFile(List<Contact> data) throws IOException {
        System.out.println("writing csv: " + getClass().getResource(csv).getPath() + ", number of lines: " + data.size());

        try (CSVWriter writer = new CSVWriter(new FileWriter(getClass().getResource(csv).getPath()))) {
            String[] header = {"ID", "FIRST NAME","LAST NAME","MIDDLE NAME","EMAIL","CELL"};
            writer.writeNext(header);

            for(Contact contact : data) {
                int id = contact.getId();
                if (id == -1) {
                    id = currentMaxId++;
                }

                writer.writeNext(new String[]{
                    String.valueOf(id),
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getMiddleName(),
                    contact.getEmail(),
                    contact.getCell()
                });
            }
        }

        System.out.println("finished writting, currentMaxId = " + currentMaxId);
    }

    private static Map<String, Integer> getHeaderMap(String[] headerLine) {
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headerLine.length; i++) {
            headerMap.put(headerLine[i], i);
        }

        return headerMap;
    }

    private Contact parseLine(Map<String, Integer> headerMap, String[] line) throws ParseException {
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(line[headerMap.get("ID")]));
        contact.setFirstName(line[headerMap.get("FIRST NAME")]);
        contact.setLastName(line[headerMap.get("LAST NAME")]);
        contact.setMiddleName(line[headerMap.get("MIDDLE NAME")]);
        contact.setEmail(line[headerMap.get("EMAIL")]);
        contact.setCell(line[headerMap.get("CELL")]);

        return contact;
    }

    private static boolean getBoolean(String data) {
        return !(data == null
                || data.equalsIgnoreCase("false")
                || data.equalsIgnoreCase("no")
                || data.equals("0"));
    }
}
