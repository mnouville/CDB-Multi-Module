package service;

import dao.ComputerDao;
import dto.Dto;
import mappers.MapperDto;

import java.sql.SQLException;
import java.util.List;

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

  private ComputerDao computerDao;
  
  private MapperDto mapper;
  
  public ServiceComputerImpl(ComputerDao computerDao, MapperDto mapper) {
    this.computerDao = computerDao;
    this.setMapper(mapper);
  }
  
  /**
   * Method for adding a computer in the database.
   * 
   * @param c Computer
   */
  @Override
  @Transactional
  public void addComputer(Dto computerDto) throws SQLException {
    Computer c = this.mapper.dtoToComputer(computerDto);
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
  public void updateComputer(Dto computerDto) throws SQLException {
    Computer c = this.mapper.dtoToComputer(computerDto);
    this.computerDao.updateComputer(c);
  }

  /**
   * Method for getting a particular Computer from the database.
   * 
   * @param id int
   */
  @Override
  @Transactional
  public Dto getComputer(int id) throws SQLException {
    Computer c = this.computerDao.getComputer(id);
    Dto computerDto = this.mapper.computerToDto(c);
    return computerDto;
  }

  /**
   * Method for having the list of every Computers in the Database.
   */
  @Override
  @Transactional
  public List<Dto> getComputers() throws SQLException {
    List<Computer> list = this.computerDao.getComputers();
    List<Dto> listDto = this.mapper.computersToDtos(list);
    return listDto;
  }

  /**
   * Return a list a computer base on a beginning ID.
   * 
   * @param begin int
   */
  @Override
  @Transactional
  public List<Dto> getComputers(int begin) throws SQLException {
    return this.mapper.computersToDtos(this.computerDao.getComputers(begin));
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
  public List<Dto> searchName(String search) throws SQLException {
    return mapper.computersToDtos(this.computerDao.searchName(search));
  }
  
  /**
   * Method that list computers ordered by a specific column.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  @Transactional
  public List<Dto> sortByColumn(String type, int begin, String column) throws SQLException {
    return this.mapper.computersToDtos(this.computerDao.sortByColumn(type, begin, column));
  }

  public MapperDto getMapper() {
    return mapper;
  }

  public void setMapper(MapperDto mapper) {
    this.mapper = mapper;
  }
}
