package org.compass.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.compass.model.CSVPrintable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FileService {

    public <T> List<T> readCsvFile(String inputFile, Function<String, String[]> splitFunction, Function<String[], T> mapToEntityFunction, int linesToSkip) {
        try (Stream<String> lines = Files.lines(Path.of(inputFile))) {
            return lines.skip(linesToSkip)
                    .map(splitFunction)
                    .map(mapToEntityFunction)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    public void writeCsvFile(String outputFile, CSVFormat csvFormat, List<CSVPrintable> recordsList) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(outputFile), 524288); // Writes in chunks of 500KB
             CSVPrinter printer = new CSVPrinter(bf, csvFormat)
        ) {
            recordsList.forEach(record -> {
                try {
                    printer.printRecord(record.print());
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Error printing record %s", record));
                }
            });

            printer.flush(); // Flush to empty buffer before close
        } catch (IOException e) {
            throw new RuntimeException("Error writing file", e);
        }
    }
}
