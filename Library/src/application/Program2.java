package application;

import java.util.ArrayList;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("==== TEST 1: department findById ====");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		
		System.out.println("==== TEST 2: department findAll ====");
		ArrayList<Department> list = (ArrayList<Department>) departmentDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}
		
	}

}
