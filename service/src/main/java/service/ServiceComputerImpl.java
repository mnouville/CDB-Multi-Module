package service;

import dao.ComputerDao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Computer;

/**
 * Class ServiceComputerImpl.
 * 
 * @author mnouville
 * @version 1.0
 */
@Service
public class ServiceComputerImpl implements ServiceComputer {

  @Autowired
  private ComputerDao computerDao;
  
  /**
   * Method for adding a computer in the database.
   * 
   * @param c Computer
   */
  @Override
  @Transactional
  public void addComputer(Computer c) throws SQLException {
    this.computerDao.addComputer(c);
  }

  /**
   * Method for deleting a computer.
   * 
   * @param id int
   */
  @Override
  @Transactional
  public void deleteComputer(int id) throws SQLException {
    this.computerDao.deleteComputer(id);
  }

  /**
   * Method for updating a computer.
   * 
   * @param c Computer
   */
  @Override
  @Transactional
  public void updateComputer(Computer c) throws SQLException {
    this.computerDao.updateComputer(c);
  }

  /**
   * Method for getting a particular Computer from the database.
   * 
   * @param id int
   */
  @Override
  @Transactional
  public Computer getComputer(int id) throws SQLException {
    Computer c = this.computerDao.getComputer(id);

    return c;
  }

  /**
   * Method for having the list of every Computers in the Database.
   */
  @Override
  @Transactional
  public List<Computer> getComputers() throws SQLException {
    List<Computer> list = this.computerDao.getComputers();

    return list;
  }

  /**
   * Return a list a computer base on a beginning ID.
   * 
   * @param begin int
   */
  @Override
  @Transactional
  public List<Computer> getComputers(int begin) throws SQLException {
    return this.computerDao.getComputers(begin);
  }

  /**
   * Method for having the max Id in the database.
   */
  @Override
  @Transactional
  public int getMaxId() throws SQLException {
    return this.computerDao.getMaxId();
  }

  /**
   * Method that return the number of computers.
   */
  @Override
  @Transactional
  public int getCount() throws SQLException {
    return this.computerDao.getCount();
  }

  /**
   * Method that list some computer sorted by name.
   * @param search String
   */
  @Override
  @Transactional
  public List<Computer> searchName(String search) throws SQLException {
    return this.computerDao.searchName(search);
  }
  
  /**
   * Method that list computers ordered by a specific column.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  @Transactional
  public List<Computer> sortByColumn(String type, int begin, String column) throws SQLException {
    return this.computerDao.sortByColumn(type, begin, column);
  }
}
