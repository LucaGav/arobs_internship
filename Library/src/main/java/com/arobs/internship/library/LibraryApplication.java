package com.arobs.internship.library;

import com.arobs.internship.library.dao.JdbcEmployeeDao;
import javafx.scene.web.HTMLEditorSkin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

	@Autowired
	private JdbcEmployeeDao employeeDao;
	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);

	}

	@Override
	public void run(String... args) {
		runJDBC();
	}

	void runJDBC(){
		employeeDao.save();
	}

}
