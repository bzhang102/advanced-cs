package brandon.diary.service;

import brandon.diary.model.ToDo;
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
import java.util.PriorityQueue;

public class ToDoCsvFileService {

    private static final String csv = "/todo.csv";

    public List<ToDo> readCsvFile() throws IOException, ParseException {
        System.out.println("loading csv: " + csv);
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(csv)))) {
            List<ToDo> toDoList = new ArrayList<>();

            String[] headerLine = csvReader.readNext();

            if (headerLine == null) {
                System.out.println("CSV file is empty at: " + csv);

                return toDoList;
            }

            System.out.println("headerLine = " + headerLine);

            Map<String, Integer> headerMap = getHeaderMap(headerLine);

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ToDo toDo = parseLine(headerMap, line);
                toDoList.add(toDo);
            }

            System.out.println("number of todo loaded from csv = " + toDoList.size());

            return toDoList;
        }
    }

    public void writeCsvFile(PriorityQueue<ToDo> unCompletedToDos, PriorityQueue<ToDo> completedToDos) throws IOException {
        System.out.println("writing csv: " + getClass().getResource(csv).getPath());

        try (CSVWriter writer = new CSVWriter(new FileWriter(getClass().getResource(csv).getPath()))) {
            String[] header = {"EVENT", "PRIORITY","COMPLETED"};
            writer.writeNext(header);

            for(ToDo todo : unCompletedToDos) {
                writer.writeNext(new String[]{
                    todo.getEvent(),
                    String.valueOf(todo.getPriority()),
                    todo.isCompleted() ? "yes" : "no"
                });
            }

            for(ToDo todo : completedToDos) {
                writer.writeNext(new String[]{
                    todo.getEvent(),
                    String.valueOf(todo.getPriority()),
                    todo.isCompleted() ? "yes" : "no"
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

    private ToDo parseLine(Map<String, Integer> headerMap, String[] line) throws ParseException {
        ToDo toDo = new ToDo();
        toDo.setEvent(line[headerMap.get("EVENT")]);
        toDo.setPriority(Integer.parseInt(line[headerMap.get("PRIORITY")]));
        toDo.setCompleted(getBoolean(line[headerMap.get("COMPLETED")]));

        return toDo;
    }

    private static boolean getBoolean(String data) {
        return !(data == null
                || data.equalsIgnoreCase("false")
                || data.equalsIgnoreCase("no")
                || data.equals("0"));
    }
}
