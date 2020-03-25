package com.arobs.internship.library.util.status;

public enum ActiveStatus {

    ACTIVE("active"),
    INACTIVE("inactive");
    private final String status;

    ActiveStatus(String s) {
        this.status = s;
    }

    String getStatus() {
        return status;
    }

    public static boolean contains(String text) {
        for (ActiveStatus s : ActiveStatus.values()) {
            if (s.name().equals(text)) {
                return true;
            }
        }
        return false;
    }
}
