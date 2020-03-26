package com.arobs.internship.library.business.impl.auxiliary;

import com.arobs.internship.library.business.BookRentService;
import com.arobs.internship.library.business.PendingRequestService;
import com.arobs.internship.library.business.SuspendedEmployeeService;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerComponent {

    @Autowired
    private PendingRequestService pendingRequestService;

    @Autowired
    private BookRentService bookRentService;

    @Autowired
    private SuspendedEmployeeService suspendedEmployeeService;

    @Scheduled(cron = "0 0/5 * * * *")
    private void pendingRequestsScheduler() throws ValidationException {
        pendingRequestService.checkPendingRentRequests();
    }

    @Scheduled(cron = "30 23 12 * * *")
    private void lateRentsScheduler() {
        bookRentService.checkLateBookRents();
    }

    @Scheduled(cron = "30 28 12 * * *")
    private void checkSuspensionsScheduler() {
        suspendedEmployeeService.checkSuspensionDates();
    }
}
