package application;

import java.util.Date;

import model.entities.Books;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		Department dep = new Department(1, "loucos");
		
		Books book = new Books(12, "Nanico", "Bufador", "Zé nenem", 14.00, new Date(), new Date(), dep);
		System.out.println(book);
	}

}
