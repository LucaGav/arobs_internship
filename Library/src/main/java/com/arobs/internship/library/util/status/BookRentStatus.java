package com.arobs.internship.library.util.status;

public enum BookRentStatus {

    ON_GOING("on_going"),
    LATE("late"),
    RETURNED("returned");

    private final String status;

    BookRentStatus(String s){
        this.status = s;
    }
    String getStatus(){
        return status;
    }

    public static boolean contains(String text){
        for(CopyStatus s: CopyStatus.values()){
            if(s.name().equals(text)){
                return true;
            }
        }
        return false;
    }

}
