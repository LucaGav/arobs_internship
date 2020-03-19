package com.arobs.internship.library;
import com.arobs.internship.library.business.PendingRequestService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.util.status.CopyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class LibraryApplication implements CommandLineRunner {


    @Autowired
    private PendingRequestService pendingRequestService;


    public static void main(String[] args) {

        SpringApplication.run(LibraryApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(pendingRequestService.findPendingRequests().get(0).toString());
    }

    @Scheduled(cron = "20 20 17 * * *")
    public void faCeva(){
        pendingRequestService.checkPendingRentRequests();
    }
}
