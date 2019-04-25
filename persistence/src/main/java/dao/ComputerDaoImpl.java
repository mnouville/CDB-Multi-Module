package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.Computer;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Class that contains every method concerning Computer in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {


  private static final Logger LOG = LoggerFactory.getLogger(ComputerDaoImpl.class);
  private final String getall = "FROM Computer";
  private final String update = "update Computer set name = :name, introduced = :intro, discontinued = :disc, company_id = :companyid where id = :id";
  private final String delete = "delete Computer where id = :id";
  private final String get = "from Computer where id = :id";
  private final String maxid = "SELECT MAX(id) FROM Computer";
  private final String count = "SELECT COUNT(id) FROM Computer";
  private final String searchname = "from Computer where name like ";
  private final String sortcompanyname = "SELECT cpu FROM Computer cpu LEFT JOIN Company cpa on cpu.company = cpa.id ";

  private HikariDataSource dataSource;
  private SessionFactory sessionFactory;
  private Session session;

  /**
   * Method that return the connection of Hikari
   * @return the connection to the database
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  
  /**
   * Constructor of ComputerDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  ComputerDaoImpl(HikariDataSource dataSource, SessionFactory sessionFactory ) { 
    this.dataSource = dataSource;
    this.sessionFactory = sessionFactory;
    this.session = this.sessionFactory.openSession();
  }

  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  @Override
  public void addComputer(Computer c) throws SQLException {
    try {
      this.sessionFactory.getCurrentSession().saveOrUpdate(c);    
    } catch (TransactionException hibernateException) {
      try {
      } catch(RuntimeException runtimeEx){
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);
      }
      hibernateException.printStackTrace();
    } finally {
        session.close();
    }  
    LOG.info("COMPUTER ADDED");
  }

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> getComputers() throws SQLException {
    List<Computer> result = (List<Computer>) this.sessionFactory.getCurrentSession().createQuery(getall).list();
    return result;
  }

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> getComputers(int begin) throws SQLException {
    Query<Computer> query;
    query = this.sessionFactory.getCurrentSession().createQuery(getall);
    query.setMaxResults(50);
    query.setFirstResult(begin);
    List<Computer> result = (List<Computer>) query.list();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> getComputers(int begin, int limit) throws SQLException {
    Query<Computer> query;
    query = this.sessionFactory.getCurrentSession().createQuery(getall);
    query.setMaxResults(limit);
    query.setFirstResult(begin);
    List<Computer> result = (List<Computer>) query.list();
    return result;
  }

  /**
   * This method delete computers by ID.
   * 
   * @param id int
   */
  @SuppressWarnings("unchecked")
  @Override
  public void deleteComputer(int id) throws SQLException {

    try {
      Query<Computer> query = this.sessionFactory.getCurrentSession().createQuery(delete);
      query.setParameter("id", id);
      query.executeUpdate();
    } catch (TransactionException hibernateException) {
      try {
      } catch(RuntimeException runtimeEx){
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);
      }
      hibernateException.printStackTrace();
    } finally {
      this.session.close();
    }  
  }

  /**
   * This method return the Computer with this ID in the Database.
   * 
   * @param i int
   * @return an object Computer
   */
  @SuppressWarnings("unchecked")
  @Override
  public Computer getComputer(int i) throws SQLException {
    Query<Computer> query;
    
    try {
      query = this.sessionFactory.getCurrentSession().createQuery(get);
      query.setParameter("id", i);
      List<Computer> result = (List<Computer>) query.list();
      return result.get(0);
    } catch (TransactionException hibernateException) {
      try {
      } catch(RuntimeException runtimeEx){
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);
      }
      hibernateException.printStackTrace();
    } finally {
      this.session.close();
    }  
    
    return null;
  }

  /**
   * This method permit updates on computers in the database.
   * 
   * @param c Computer
   */
  @SuppressWarnings("unchecked")
  @Override
  public void updateComputer(Computer c) throws SQLException {
    
    try {
      Query<Computer> query = this.sessionFactory.getCurrentSession().createQuery(update);
      query.setParameter("name", c.getName());
      query.setParameter("intro", c.getIntroduced()  == null ? null : new Timestamp(c.getIntroduced().getTime()));
      query.setParameter("disc", c.getDiscontinued() == null ? null : new Timestamp(c.getDiscontinued().getTime()));
      query.setParameter("companyid", c.getCompany().getId());
      query.setParameter("id", c.getId());
      query.executeUpdate();
    } catch (TransactionException hibernateException) {
      try {
      } catch(RuntimeException runtimeEx){
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);
      }
      hibernateException.printStackTrace();
    } finally {
      this.session.close();
    }  
    
  }

  /**
   * Return the Maximum Id in the database.
   * 
   * @return Int that correspond to the Max Id
   */
  
  @SuppressWarnings("rawtypes")
  @Override
  public int getMaxId() throws SQLException {
    LOG.info("MAX ID requested");
    Query query = this.sessionFactory.getCurrentSession().createQuery(maxid);
    return Integer.parseInt(query.list().get(0).toString())+1;
  }

  /**
   * Return the number of row in the database.
   * 
   * @return int
   */
  @SuppressWarnings("rawtypes")
  public int getCount() throws SQLException {
    LOG.info("ROW COUNT requested");
    Query query = this.sessionFactory.getCurrentSession().createQuery(count);
    return Integer.parseInt(query.list().get(0).toString());
  }

  /**
   * Method that sort all computers by name.
   */
  @SuppressWarnings("unchecked")
  public List<Computer> searchName(String search, int page, int limit) throws SQLException {
    int offset = (page - 1) * limit;
    Query<Computer> query;
    query = this.sessionFactory.getCurrentSession().createQuery(searchname + "'%" + search + "%'");
    query.setMaxResults(limit);
    query.setFirstResult(offset);
    List<Computer> result = (List<Computer>) query.list();
    return result;
  }
  
  /**
   * Method that sort all computers by introduced.
   */
  @SuppressWarnings("unchecked")
  public List<Computer> sortByColumn(String type, int begin, String column, String search, int limit) throws SQLException {    
    Query<Computer> query;
    
    if (column.equals("company")) {
      query = this.sessionFactory.getCurrentSession().createQuery(sortcompanyname + " WHERE name like '%" + search + "%' order by ISNULL(cpa.name),cpa.name " + type);
    } else {
      query = this.sessionFactory.getCurrentSession().createQuery(getall + " WHERE name like '%" + search + "%' order by " + column + " " + type);
    }
    
    query.setMaxResults(limit);
    query.setFirstResult(begin);
    List<Computer> result = (List<Computer>) query.list();
    LOG.info("Request succesfully executed (sort by column) size : " + result.size());
    return result;
  }
}
