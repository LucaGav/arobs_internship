package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.statistics.TopReadingEmployee;
import com.arobs.internship.library.entities.statistics.TopRentedBook;
import com.arobs.internship.library.util.handler.ValidationException;

import java.sql.Date;
import java.util.List;

public interface StatisticsService {

    List<TopRentedBook> topXRentedBooks(int top, Date startDate, Date endDate) throws ValidationException;

    List<TopReadingEmployee> topXReadingEmployees(int top, Date startDate, Date endDate) throws ValidationException;
}
