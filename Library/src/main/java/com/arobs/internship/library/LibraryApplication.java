package com.arobs.internship.library;
import com.arobs.internship.library.business.PendingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class LibraryApplication{

    public static void main(String[] args) {

        SpringApplication.run(LibraryApplication.class, args);

    }

}
