package org.compass.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.compass.model.CSVPrintable;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.ComparisonService;
import org.compass.service.FileService;
import org.compass.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    private static final Logger log = LoggerFactory.getLogger(MainServiceImpl.class);

    private final ComparisonService comparisonService;
    private final FileService fileService;

    public MainServiceImpl(ComparisonService comparisonService, FileService fileService) {
        this.comparisonService = comparisonService;
        this.fileService = fileService;
    }

    @Override
    public void execute(String inputFile, String outputFile) {
        log.info("Starting process");

        List<Contact> contactList = fileService.readCsvFile(
                inputFile,
                line -> line.split(",", 6), // Limit 6 in order to catch the records that ends with a comma and also limit it to 5 groups
                Contact::new,
                1); // Skipping header

        List<Comparison> comparisonResultsList = comparisonService.compare(contactList);

        fileService.writeCsvFile(
                outputFile,
                CSVFormat.Builder.create()
                        .setHeader("ContactID Source", "ContactID Match", "Accuracy")
                        .build(),
                comparisonResultsList.stream()
                        .sorted(Comparator.comparing((Comparison c) -> c.contact1().contactId())
                                .thenComparing((Comparison c) -> c.accuracy().getOrder()))
                        .map(comparison -> (CSVPrintable) comparison) //Explicit conversion to printable interface because of type erasure
                        .toList());

        log.info("Process finished successfully");
    }
}
