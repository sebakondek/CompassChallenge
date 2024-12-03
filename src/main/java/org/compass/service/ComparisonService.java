package org.compass.service;

import org.compass.model.Comparison;
import org.compass.model.Contact;

import java.util.List;

public interface ComparisonService {
    List<Comparison> compare(List<Contact> contacts);
}
