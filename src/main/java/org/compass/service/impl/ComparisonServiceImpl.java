package org.compass.service.impl;

import org.apache.commons.text.similarity.JaccardSimilarity;
import org.compass.model.Accuracy;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.ComparisonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComparisonServiceImpl implements ComparisonService {

    private static final double EMAIL_WEIGHT = 0.4;
    private static final double FIRST_NAME_WEIGHT = 0.1;
    private static final double LAST_NAME_WEIGHT = 0.1;
    private static final double ADDRESS_WEIGHT = 0.3;
    private static final double ZIP_CODE_WEIGHT = 0.1;

    private final JaccardSimilarity jaccardSimilarity;

    public ComparisonServiceImpl() {
        this.jaccardSimilarity = new JaccardSimilarity();
    }

    @Override
    public List<Comparison> compare(List<Contact> contacts) {
        List<Comparison> comparisons = new ArrayList<>();

        // This is a O(n^2) operation, so it will be slow for large datasets, but for this one it's fine
        // There are better options to optimize this, but because the records are so inconsistent I need to iterate through them all
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                Contact contact1 = contacts.get(i);
                Contact contact2 = contacts.get(j);

                // Email similarity
                double emailSimilarity = jaccardSimilarity.apply(contact1.email(), contact2.email());

                // Complete name similarity
                double firstNameSimilarity = jaccardSimilarity.apply(contact1.firstName(), contact2.firstName());
                double lastNameSimilarity = jaccardSimilarity.apply(contact1.lastName(), contact2.lastName());

                // Address similarity
                double addressSimilarity = jaccardSimilarity.apply(contact1.address(), contact2.address());

                // ZipCode similarity
                double zipCodeSimilarity = jaccardSimilarity.apply(contact1.zipcode(), contact2.zipcode());

                // Total weighted similarity
                double totalSimilarity = (emailSimilarity * EMAIL_WEIGHT)
                        + (firstNameSimilarity * FIRST_NAME_WEIGHT)
                        + (lastNameSimilarity * LAST_NAME_WEIGHT)
                        + (addressSimilarity * ADDRESS_WEIGHT)
                        + (zipCodeSimilarity * ZIP_CODE_WEIGHT);

                // Calculate accuracy based on weighted similarity
                Accuracy accuracy = (totalSimilarity > 0.75) ? Accuracy.HIGH
                        : (totalSimilarity > 0.5) ? Accuracy.MEDIUM
                        : Accuracy.LOW;

                comparisons.add(new Comparison(contact1, contact2, totalSimilarity, accuracy));
            }
        }

        return comparisons;
    }
}
