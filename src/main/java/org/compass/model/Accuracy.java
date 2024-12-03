package org.compass.model;

public enum Accuracy {
    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private int order;

    Accuracy(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
