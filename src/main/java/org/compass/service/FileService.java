package org.compass.service;

import org.apache.commons.csv.CSVFormat;
import org.compass.model.CSVPrintable;

import java.util.List;
import java.util.function.Function;

public interface FileService {
    <T> List<T> readCsvFile(String inputFile, Function<String, String[]> splitFunction, Function<String[], T> mapToEntityFunction, int linesToSkip);
    void writeCsvFile(String outputFile, CSVFormat csvFormat, List<CSVPrintable> recordsList);
}
