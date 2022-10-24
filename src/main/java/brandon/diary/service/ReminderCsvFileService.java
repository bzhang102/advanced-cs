package brandon.diary.service;

import brandon.diary.model.Reminder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReminderCsvFileService {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static final String csv = "/reminder.csv";

    public List<Reminder> readCsvFile() throws IOException, ParseException {
        System.out.println("loading csv: " + csv);
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(csv)))) {
            List<Reminder> reminderList = new ArrayList<>();

            String[] headerLine = csvReader.readNext();

            if (headerLine == null) {
                System.out.println("CSV file is empty at: " + csv);

                return reminderList;
            }

            System.out.println("headerLine = " + headerLine);

            Map<String, Integer> headerMap = getHeaderMap(headerLine);

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Reminder reminder = parseLine(headerMap, line);
                reminderList.add(reminder);
            }

            System.out.println("number of reminders loaded from csv = " + reminderList.size());

            return reminderList;
        }
    }

    public void writeCsvFile(List<Reminder> data) throws IOException {
        System.out.println("writing csv: " + getClass().getResource(csv).getPath() + ", number of lines: " + data.size());

        try (CSVWriter writer = new CSVWriter(new FileWriter(getClass().getResource(csv).getPath()))) {
            String[] header = {"EVENT", "DATE", "START", "COMPLETED"};
            writer.writeNext(header);

            for(Reminder reminder : data) {
                writer.writeNext(new String[]{
                        reminder.getEvent(),
                        reminder.getDate().format(dateTimeFormatter),
                        reminder.getStart(),
                        reminder.isCompleted() ? "yes" : "no"
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

    private Reminder parseLine(Map<String, Integer> headerMap, String[] line) throws ParseException {
        Reminder reminder = new Reminder();
        reminder.setEvent(line[headerMap.get("EVENT")]);
        reminder.setDate(LocalDate.parse(line[headerMap.get("DATE")], dateTimeFormatter));
        reminder.setStart(line[headerMap.get("START")]);
        reminder.setCompleted(getBoolean(line[headerMap.get("COMPLETED")]));

        return reminder;
    }

    private static boolean getBoolean(String data) {
        return !(data == null
                || data.equalsIgnoreCase("false")
                || data.equalsIgnoreCase("no")
                || data.equals("0"));
    }
}
