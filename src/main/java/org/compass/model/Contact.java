package org.compass.model;

public record Contact(
        Integer contactId,
        String firstName,
        String lastName,
        String email,
        String zipcode,
        String address
) {
    public Contact(String[] row) {
        this(
                Integer.parseInt(row[0]),
                row[1],
                row[2],
                row[3],
                row[4],
                row[5]
        );
    }
}
