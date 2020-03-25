package com.arobs.internship.library.util.status;

public enum RentRequestStatus {

    WAITINGAVAILABLECOPY("waiting_available_copy"),
    WAITINGCONFIRMATION("waiting_confirmation"),
    DECLINED("declined"),
    GRANTED("granted");

    private final String status;

    RentRequestStatus(String s) {
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
