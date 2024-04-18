package com.dsinnovators.todo.helpers;

import com.dsinnovators.todo.entities.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream writeCsv(List<Task> tasks) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), CSVFormat.DEFAULT);

        csvPrinter.printRecord("Id", "Title", "Description", "StartDate", "EndDate", "Status");

        for (Task task : tasks) {
            csvPrinter.printRecord(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStartDate(),
                    task.getEndDate(),
                    task.getStatus()
            );
        }

        csvPrinter.flush();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
