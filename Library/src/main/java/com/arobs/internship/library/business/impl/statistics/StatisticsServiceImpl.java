package com.arobs.internship.library.business.impl.statistics;

import com.arobs.internship.library.business.StatisticsService;
import com.arobs.internship.library.dao.StatisticsDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.statistics.TopReadingEmployee;
import com.arobs.internship.library.entities.statistics.TopRentedBook;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private DaoFactory daoFactory;

    private StatisticsDao statisticsDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        statisticsDao = factory.getStatisticsDao();
    }

    @Override
    public List<TopRentedBook> topXRentedBooks(int top, Date startDate, Date endDate) throws ValidationException {
        if (endDate.compareTo(startDate) < 0) {
            throw new ValidationException("Ending date of period cannot be anterior to starting date");
        }
        return statisticsDao.getTopXRentedBooks(top, startDate, endDate);
    }

    @Override
    public List<TopReadingEmployee> topXReadingEmployees(int top, Date startDate, Date endDate) throws ValidationException {
        if (endDate.compareTo(startDate) < 0) {
            throw new ValidationException("Ending date of period cannot be anterior to starting date");
        }
        return statisticsDao.getTopXReadingEmployees(top, startDate, endDate);
    }
}
