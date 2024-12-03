package org.compass;

import org.apache.commons.csv.CSVFormat;
import org.compass.model.CSVPrintable;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.ComparisonService;
import org.compass.service.FileService;

import java.util.Comparator;
import java.util.List;

public class Application {
    private static final String INPUT_FILE = "src/main/resources/files/input/CompassSampleCode.csv";
    private static final String OUTPUT_FILE = "src/main/resources/files/output/CompassSampleCodeResults.csv";
    private static final ComparisonService comparisonService = new ComparisonService();
    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        List<Contact> contactList = fileService.readCsvFile(
                INPUT_FILE,
                line -> line.split(",", 6), // Limit 6 in order to catch the records that ends with a comma and also limit it to 5 groups
                Contact::new,
                1); // Skipping header

        List<Comparison> comparisonResultsList = comparisonService.compare(contactList);

        fileService.writeCsvFile(
                OUTPUT_FILE,
                CSVFormat.Builder.create()
                        .setHeader("ContactID Source", "ContactID Match", "Accuracy")
                        .build(),
                comparisonResultsList.stream()
                        .sorted(Comparator.comparing((Comparison c) -> c.contact1().contactId())
                                .thenComparing((Comparison c) -> c.accuracy().getOrder()))
                        .map(comparison -> (CSVPrintable) comparison) //Explicit conversion to printable interface because of type erasure
                        .toList());
    }
}
