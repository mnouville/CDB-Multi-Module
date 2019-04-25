package service;

import java.sql.SQLException;
import java.util.List;

import dto.Dto;

/**
 * Class ServiceComputer that implement every methods of ServiceComputerImpl.
 * 
 * @author mnouville
 * @version 1.0
 */
public interface ServiceComputer {
  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  void addComputer(Dto c) throws SQLException;

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
  Dto getComputer(int id) throws SQLException;

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  List<Dto> getComputers() throws SQLException;

  /**
   * This method return a list of every computers in the Database with a begin index.
   * 
   * @return List of Objects Computer
   */
  List<Dto> getComputers(int begin) throws SQLException;
  
  /**
   * This method return a list of every computers in the Database with a begin index and a limit.
   * 
   * @return List of Objects Computer
   */
  List<Dto> getComputers(int begin, int limit) throws SQLException;

  /**
   * Return the Maximum Id in the database.
   * 
   * @return Int that correspond to the Max Id
   */
  int getMaxId() throws SQLException;

  /**
   * This method permit updates on computers in the database.
   * 
   * @param c Computer
   */
  void updateComputer(Dto c) throws SQLException;

  /**
   * Return the number of computer in the database.
   * 
   * @return int
   */
  int getCount() throws SQLException;

  /**
   * Return a list of computers that contains in their name the following words.
   * 
   * @param search String
   * @return List of Computer Objects
   */
  List<Dto> searchName(String search, int page, int limit) throws SQLException;
  
  /**
   * Return a list of computers ordered by a specific column.
   * @param type String
   * @param begin int
   * @return
   */
  List<Dto> sortByColumn(String type, int begin, String column, String search, int limit) throws SQLException;
}
