package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.book.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface CopyDTOConverter {

    List<CopyDTO> listCopyToDTO(List<Copy> copies);

    Copy dtoToCopy(CopyDTO copyDTO) throws ValidationException;

    CopyDTO copyToDTO(Copy copy);
}
