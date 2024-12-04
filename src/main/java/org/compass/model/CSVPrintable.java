package org.compass.model;

/***
 * Interface used to indicate which fields to print into a CSV
 */
@FunctionalInterface
public interface CSVPrintable {
    Object[] print();
}
