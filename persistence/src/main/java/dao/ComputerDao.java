package dao;

import java.sql.SQLException;
import java.util.List;

import model.Computer;

/**
 * ComputerDao is the interface of the ComputerDaoImpl class. She instantiate every methods that you
 * can find in ComputerDaoImpl.java.
 * 
 * @author mnouville
 * @version 1.0
 */
public interface ComputerDao {

  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  void addComputer(Computer c) throws SQLException;

  /**
   * This method delete computers by ID.
   * 
   * @param id int
   */
  void deleteComputer(int id) throws SQLException;

  /**
   * This method return the Computer with this ID in the Database.
   * 
   * @param id int
   * @return an object Computer
   */
  Computer getComputer(int id) throws SQLException;

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  List<Computer> getComputers() throws SQLException;

  /**
   * This method return a list of every computers in the Database with a begin index.
   * 
   * @return List of Objects Computer
   */
  List<Computer> getComputers(int begin) throws SQLException;

  /**
   * This method return a list of every computers in the Database with a begin index and a limit.
   * 
   * @return List of Objects Computer
   */
  List<Computer> getComputers(int begin, int limit) throws SQLException;
  
  /**
   * Return the Maximum Id in the database.
   * 
   * @return Int that correspond to the Max Id
   */
  int getMaxId() throws SQLException;

  /**
   * Return the number of computer in the database.
   * 
   * @return Int
   */
  int getCount() throws SQLException;

  /**
   * This method permit updates on computers in the database.
   * 
   * @param c Computer
   */
  void updateComputer(Computer c) throws SQLException;

  /**
   * Return a list of computers that contains in their name the following words.
   * 
   * @param search String
   * @return List of Computer Objects
   */
  List<Computer> searchName(String search, int page, int limit) throws SQLException;
  
  /**
   * Return a list of computers ordered by Company Name.
   * @param type String
   * @param begin int
   * @return
   */
  List<Computer> sortByColumn(String type, int begin, String column, String search, int limit) throws SQLException;
}
