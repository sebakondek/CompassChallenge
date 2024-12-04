package org.compass.unit.service;

import org.apache.commons.csv.CSVFormat;
import org.compass.model.Accuracy;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.FileService;
import org.compass.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl();
    }

    @Test
    void readFile_ok() throws IOException {
        // Given
        String inputFile = "inputFile.csv";
        String input = "header\n1,firstName1,lastName1,email1,zipcode1,address1\n2,firstName2,lastName2,email2,zipcode2,address2";
        Path filePath = Path.of(inputFile);

        try {
            Files.writeString(filePath, input);

            // When
            List<Contact> resultList = fileService.readCsvFile(
                    inputFile,
                    line -> line.split(",", 6),
                    Contact::new,
                    1);

            // Then
            assertEquals(2, resultList.size());
            assertEquals(1, resultList.getFirst().contactId());
            assertEquals(2, resultList.getLast().contactId());
        } finally {
            Files.delete(filePath);
        }
    }

    @Test
    void readFile_throwsException() {
        // Given
        String inputFile = "inputFile.csv";

        // When
        Exception ex = assertThrows(RuntimeException.class, () -> fileService.readCsvFile(
                inputFile,
                line -> line.split(",", 6),
                Contact::new,
                1));

        // Then
        assertEquals("Error reading file", ex.getMessage());
    }

    @Test
    void writeFile_ok() throws IOException {
        // Given
        String outputFile = "outputFile.csv";
        CSVFormat csvFormat = CSVFormat.newFormat(',');

        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");
        Contact contact2 = new Contact(2, "b", "b", "b@b", "b", "b");

        Comparison comparison1 = new Comparison(contact1, contact2, 0.85, Accuracy.HIGH);

        // When
        assertDoesNotThrow(() -> fileService.writeCsvFile(outputFile, csvFormat, List.of(comparison1)));

        // Then
        Path filePath = Path.of(outputFile);
        try  {
            String line = Files.readString(filePath);

            assertEquals("1,2,HIGH", line);
        } finally {
            Files.delete(filePath);
        }
    }
}
