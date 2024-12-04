package org.compass.unit.service;

import org.compass.model.Accuracy;
import org.compass.model.Comparison;
import org.compass.model.Contact;
import org.compass.service.ComparisonService;
import org.compass.service.impl.ComparisonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparisonServiceTest {

    private ComparisonService comparisonService;

    @BeforeEach
    void setUp() {
        comparisonService = new ComparisonServiceImpl();
    }

    @Test
    void compare_ok_high() {
        // Given
        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");
        Contact contact2 = new Contact(2, "aa", "aa", "a@a", "aa", "aa");

        // When
        List<Comparison> resultList = comparisonService.compare(List.of(contact1, contact2));

        // Then
        assertEquals(2, resultList.size());
        assertEquals(Accuracy.HIGH, resultList.getFirst().accuracy());
        assertEquals(1, resultList.getFirst().contact1().contactId());
        assertEquals(2, resultList.getFirst().contact2().contactId());

        assertEquals(Accuracy.HIGH, resultList.getLast().accuracy());
        assertEquals(2, resultList.getLast().contact1().contactId());
        assertEquals(1, resultList.getLast().contact2().contactId());
    }

    @Test
    void compare_ok_medium() {
        // Given
        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");
        Contact contact2 = new Contact(2, "ab", "ab", "ab@a", "ab", "ab");

        // When
        List<Comparison> resultList = comparisonService.compare(List.of(contact1, contact2));

        // Then
        assertEquals(2, resultList.size());
        assertEquals(Accuracy.MEDIUM, resultList.getFirst().accuracy());
        assertEquals(1, resultList.getFirst().contact1().contactId());
        assertEquals(2, resultList.getFirst().contact2().contactId());

        assertEquals(Accuracy.MEDIUM, resultList.getLast().accuracy());
        assertEquals(2, resultList.getLast().contact1().contactId());
        assertEquals(1, resultList.getLast().contact2().contactId());
    }

    @Test
    void compare_ok_low() {
        // Given
        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");
        Contact contact2 = new Contact(2, "b", "b", "b@b", "b", "b");

        // When
        List<Comparison> resultList = comparisonService.compare(List.of(contact1, contact2));

        // Then
        assertEquals(2, resultList.size());
        assertEquals(Accuracy.LOW, resultList.getFirst().accuracy());
        assertEquals(1, resultList.getFirst().contact1().contactId());
        assertEquals(2, resultList.getFirst().contact2().contactId());

        assertEquals(Accuracy.LOW, resultList.getLast().accuracy());
        assertEquals(2, resultList.getLast().contact1().contactId());
        assertEquals(1, resultList.getLast().contact2().contactId());
    }

    @Test
    void compare_returns0IfOnly1ToCompare() {
        // Given
        Contact contact1 = new Contact(1, "a", "a", "a@a", "a", "a");

        // When
        List<Comparison> resultList = comparisonService.compare(List.of(contact1));

        // Then
        assertEquals(0, resultList.size());
    }

    @Test
    void compare_returns0IfNothingToCompare() {
        // When
        List<Comparison> resultList = comparisonService.compare(List.of());

        // Then
        assertEquals(0, resultList.size());
    }
}
