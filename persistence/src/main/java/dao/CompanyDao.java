package dao;

import java.sql.SQLException;
import java.util.List;

import model.Company;

/**
 * CompanyDao is the interface of the CompanyDaoImpl class. She instantiate every methods that you
 * can find in CompanyDaoImpl.java
 * 
 * @author mnouville
 * @version 1.0
 */

public interface CompanyDao {

  /**
   * This method take a Company in parameter and add it into the Database.
   * 
   * @param c Company
   */
  void addCompany(Company c) throws SQLException;

  /**
   * This method delete companies by ID.
   * 
   * @param id int
   */
  void deleteCompany(int id) throws SQLException;

  /**
   * This method return the Company with this ID in the Database.
   * 
   * @param id int
   * @return Object Company
   */
  Company getCompany(int id) throws SQLException;

  /**
   * This method return a list of every companies in the Database.
   * 
   * @return List of Object Company
   */
  List<Company> getCompanies() throws SQLException;

  /**
   * This method return a true if a Company ID exist in the Database.
   * 
   * @return boolean
   */
  boolean companyExist(int id) throws SQLException;
}
