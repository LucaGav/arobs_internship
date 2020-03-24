package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.statistics.TopReadingEmployee;
import com.arobs.internship.library.entities.statistics.TopRentedBook;

import java.sql.Date;
import java.util.List;

public interface StatisticsDao {

    List<TopRentedBook> getTopXRentedBooks(int top, Date startDate, Date endDate);

    List<TopReadingEmployee> getTopXReadingEmployees(int top, Date startDate, Date endDate);
}
