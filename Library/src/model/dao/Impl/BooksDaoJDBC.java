package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO books "
					+ "(Name, Genre, Author, MarketPrice, ReleaseDate, DonateDate, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getGenre());
			st.setString(3, obj.getAuthor());
			st.setDouble(4, obj.getMarketPrice());
			st.setDate(5, new java.sql.Date(obj.getReleaseDate().getTime()));
			st.setDate(6, new java.sql.Date(obj.getDonateDate().getTime()));
			st.setInt(7, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected! ");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Books obj) {
		PreparedStatement st = null;
		try {
		st = conn.prepareStatement("UPDATE books "
				+ "SET Name = ?, Genre = ?, Author = ?, MarketPrice = ?, ReleaseDate = ?, DonateDate = ?, DepartmentId = ? "
				+ "WHERE Id = ?");
		
		st.setString(1, obj.getName());
		st.setString(2, obj.getGenre());
		st.setString(3, obj.getAuthor());
		st.setDouble(4, obj.getMarketPrice());
		st.setDate(5, new java.sql.Date(obj.getReleaseDate().getTime()));
		st.setDate(6, new java.sql.Date(obj.getDonateDate().getTime()));
		st.setInt(7, obj.getDepartment().getId());
		st.setInt(8, obj.getId());
		
		st.executeUpdate();
	}
	catch (SQLException e) {
		throw new DbException(e.getMessage());
	}
	finally {
		DB.closeStatement(st);
	}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM book WHERE id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
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
				Department dep = instantiateDepartment(rs);
				Books obj = instantiateBooks(rs, dep);
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

	private Books instantiateBooks(ResultSet rs, Department dep) throws SQLException {
		Books obj = new Books();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setAuthor(rs.getString("Author"));
		obj.setGenre(rs.getString("Genre"));
		obj.setMarketPrice(rs.getDouble("MarketPrice"));
		obj.setReleaseDate(rs.getDate("ReleaseDate"));
		obj.setDonateDate(rs.getDate("DonateDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Books> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try { 
			st = conn.prepareStatement(
					"SELECT books.*,department.Name as DepName " + 
					"FROM books INNER JOIN department " + 
					"ON books.DepartmentId = department.Id " + 
 					"ORDER BY Name");
			rs = st.executeQuery();
			
			List<Books> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Books obj = instantiateBooks(rs, dep);
				list.add(obj);
			}
			return list;
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
	public List<Books> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try { 
			st = conn.prepareStatement(
					"SELECT books.*,department.Name as DepName " + 
					"FROM books INNER JOIN department " + 
					"ON books.DepartmentId = department.Id " + 
					"WHERE DepartmentId = ? " + 
					"ORDER BY Name");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Books> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Books obj = instantiateBooks(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
