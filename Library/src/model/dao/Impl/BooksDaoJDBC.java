package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.BooksDao;
import model.entities.Books;
import model.entities.Department;

public class BooksDaoJDBC implements BooksDao{
	
	private Connection conn;
	
	public BooksDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Books obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Books obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Books findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try { 
			st = conn.prepareStatement(
					"SELECT books.*,department.Name as DepName "
					+ "FROM books INNER JOIN department "
					+ "ON books.DepartmentId = department.Id "
					+ "WHERE books.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				Books obj = new Books();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setAuthor(rs.getString("Author"));
				obj.setMarketPrice(rs.getDouble("MarketPrice"));
				obj.setReleaseDate(rs.getDate("ReleaseDate"));
				obj.setDonateDate(rs.getDate("DonateDate"));
				obj.setDepartment(dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Books> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
