package com.arobs.internship.library;

import com.arobs.internship.library.dao.factory.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

	@Autowired
	private DaoFactory daoFactory;

	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);

	}

	@Override
	public void run(String... args) {
		runJDBC();
	}

	void runJDBC(){

		System.out.println("runJDBC");
		/*DaoFactory factory = daoFactory.getInstance();
		EmployeeDao employeeDao = factory.getEmployeeDao();
		employeeDao.returnSmth();
		employeeDao.save();*/

		//employeeDao.save();
	}

}
