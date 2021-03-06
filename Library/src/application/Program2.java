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
		/*System.out.println("==== TEST 3: department insert ====");
		Department newDepartment = new Department(null, "Borrowed");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! new Id = " + newDepartment.getId());
		
		System.out.println("==== TEST 4: department update ====");
		department = departmentDao.findById(4);
		department.setName("Emprestado");
		departmentDao.update(department);
		System.out.println("Update Completed!");*/
		
		System.out.println("==== TEST 5: department delete ====");
		departmentDao.deleteById(5);
		System.out.println("Delete completed!");
	}

}
