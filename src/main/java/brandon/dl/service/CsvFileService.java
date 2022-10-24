package brandon.dl.service;

import brandon.dl.model.DriverLicense;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CsvFileService {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final String csv = "/dl.csv";

    public List<DriverLicense> readCsvFile() throws IOException, ParseException {
        System.out.println("loading csv: " + csv);
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(csv)))) {
            List<DriverLicense> driverLicenseList = new LinkedList<>();

            String[] headerLine = csvReader.readNext();

            if (headerLine == null) {
                System.out.println("CSV file is empty at: " + csv);

                return driverLicenseList;
            }

            Map<String, Integer> headerMap = getHeaderMap(headerLine);

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                driverLicenseList.add(parseLine(headerMap, line));

            }

            System.out.println("number of dls loaded from csv = " + driverLicenseList.size());

            return driverLicenseList;
        }
    }

    public void writeCsvFile(List<DriverLicense> data) throws IOException {
        System.out.println("writing csv: " + csv + ", number of lines: " + data.size());

        try (CSVWriter writer = new CSVWriter(new FileWriter(getClass().getResource(csv).getPath()))) {
            String[] header = {"DL NUMBER","FIRST NAME","LAST NAME","GENDER","ADDRESS","DOB","EXPIRATION",
                    "POINTS","DONOR","PICTURE LOCATION"};
            writer.writeNext(header);

            for(DriverLicense driver : data) {
                writer.writeNext(new String[]{
                        driver.getDlNumber(),
                        driver.getFirstName(),
                        driver.getLastName(),
                        driver.getGender(),
                        driver.getAddress(),
                        driver.getDob().format(dateTimeFormatter),
                        driver.getExpiration().format(dateTimeFormatter),
                        String.valueOf(driver.getPoints()),
                        driver.isDonor() ? "yes" : "no",
                        driver.getPictureLocation()
                });
            }
        }
    }

    private static Map<String, Integer> getHeaderMap(String[] headerLine) {
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headerLine.length; i++) {
            headerMap.put(headerLine[i], i);
        }

        return headerMap;
    }

    private DriverLicense parseLine(Map<String, Integer> headerMap, String[] line) throws ParseException {
        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setDlNumber(line[headerMap.get("DL NUMBER")]);
        driverLicense.setFirstName(line[headerMap.get("FIRST NAME")]);
        driverLicense.setLastName(line[headerMap.get("LAST NAME")]);
        driverLicense.setGender(line[headerMap.get("GENDER")]);
        driverLicense.setAddress(line[headerMap.get("ADDRESS")]);
        driverLicense.setPictureLocation(line[headerMap.get("PICTURE LOCATION")]);

        driverLicense.setDob(LocalDate.parse(line[headerMap.get("DOB")], dateTimeFormatter));
        driverLicense.setExpiration(LocalDate.parse(line[headerMap.get("EXPIRATION")], dateTimeFormatter));

        driverLicense.setPoints(Integer.parseInt(line[headerMap.get("POINTS")]));
        driverLicense.setDonor(getBoolean(line[headerMap.get("DONOR")]));

        return driverLicense;
    }

    private static boolean getBoolean(String data) {
        return !(data == null
                || data.equalsIgnoreCase("false")
                || data.equalsIgnoreCase("no")
                || data.equals("0"));
    }
}
