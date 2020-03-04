package com.arobs.internship.library.entities.util;

import org.springframework.web.bind.annotation.RequestBody;

public enum RequestStatus {
    ACCEPTED("accepted"),
    PENDING("pending"),
    REJECTED("rejected");

    private final String status;

    RequestStatus(String s){
        this.status = s;
    }
    String getStatus(){
        return status;
    }

    public static boolean contains(String text){
        for(RequestStatus s: RequestStatus.values()){
            if(s.name().equals(text)){
                return true;
            }
        }
        return false;
    }
}

