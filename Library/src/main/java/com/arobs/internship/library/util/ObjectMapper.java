package com.arobs.internship.library.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapper {

    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
