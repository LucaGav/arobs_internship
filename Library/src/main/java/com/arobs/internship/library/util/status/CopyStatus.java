package com.arobs.internship.library.util.status;

public enum CopyStatus {

    AVAILABLE("available"),
    PENDING("pending"),
    RENTED("rented");

    private final String status;

    CopyStatus(String s) {
        this.status = s;
    }

    String getStatus() {
        return status;
    }

    public static boolean contains(String text) {
        for (CopyStatus s : CopyStatus.values()) {
            if (s.name().equals(text)) {
                return true;
            }
        }
        return false;
    }

}
