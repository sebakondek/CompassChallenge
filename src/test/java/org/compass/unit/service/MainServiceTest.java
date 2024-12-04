package org.compass.unit.service;

import org.apache.commons.csv.CSVFormat;
import org.compass.model.Accuracy;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.ComparisonService;
import org.compass.service.FileService;
import org.compass.service.MainService;
import org.compass.service.impl.MainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MainServiceTest {

    private final ComparisonService comparisonService = mock(ComparisonService.class);
    private final FileService fileService = mock(FileService.class);

    private MainService mainService;

    @BeforeEach
    void setUp() {
        mainService = new MainServiceImpl(comparisonService, fileService);
    }

    @Test
    void execute_ok() {
        // Given
        String inputFile = "inputFile.csv";
        String outputFile = "outputFile.csv";

        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");
        Contact contact2 = new Contact(2, "b", "b", "b@b", "b", "b");

        Comparison comparison1 = new Comparison(contact1, contact2, 0.85, Accuracy.HIGH);
        Comparison comparison2 = new Comparison(contact2, contact1, 0.85, Accuracy.HIGH);

        when(fileService.readCsvFile(
                eq(inputFile),
                any(Function.class),
                any(Function.class),
                eq(1))
        ).thenReturn(List.of(contact1, contact2));

        when(comparisonService.compare(List.of(contact1, contact2))).thenReturn(List.of(comparison1, comparison2));

        // When
        assertDoesNotThrow(() -> mainService.execute(inputFile, outputFile));

        // Then
        verify(fileService).writeCsvFile(eq(outputFile), any(CSVFormat.class), eq(List.of(comparison1, comparison2)));
    }
}
