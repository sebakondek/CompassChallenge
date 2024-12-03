package org.compass.model;

public record Comparison(
        Contact contact1,
        Contact contact2,
        double similarity,
        Accuracy accuracy
) implements CSVPrintable {
    @Override
    public Object[] print() {
        return new Object[]{contact1.contactId(), contact2.contactId(), accuracy};
    }
}
