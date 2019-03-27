package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.Computer;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
  
  public void validateSession() {
    if(!this.session.isOpen()) {
      this.session = this.sessionFactory.openSession();
    }
  }

  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  @Override
  public void addComputer(Computer c) throws SQLException {
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(c);        
      tx.commit();
    } catch (TransactionException hibernateException) {
      try {
        tx.rollback();
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
    validateSession();
    List<Computer> result = (List<Computer>) this.session.createQuery(getall).list();
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
    validateSession();
    Query<Computer> query;
    query = this.session.createQuery(getall);
    query.setMaxResults(50);
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
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<Computer> query = this.session.createQuery(delete);
      query.setParameter("id", id);
      query.executeUpdate();
      tx.commit();
    } catch (TransactionException hibernateException) {
      try {
        tx.rollback();
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
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<Computer> query = this.session.createQuery(get);
      query.setParameter("id", i);
      List<Computer> result = (List<Computer>) query.list();
      return result.get(0);
    } catch (TransactionException hibernateException) {
      try {
        tx.rollback();
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
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<Computer> query = this.session.createQuery(update);
      query.setParameter("name", c.getName());
      query.setParameter("intro", c.getIntroduced()  == null ? null : new Timestamp(c.getIntroduced().getTime()));
      query.setParameter("disc", c.getDiscontinued() == null ? null : new Timestamp(c.getDiscontinued().getTime()));
      query.setParameter("companyid", c.getCompany().getId());
      query.setParameter("id", c.getId());
      query.executeUpdate();
      tx.commit();
    } catch (TransactionException hibernateException) {
      try {
        tx.rollback();
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
    validateSession();
    Query query = this.session.createQuery(maxid);
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
    validateSession();
    Query query = this.session.createQuery(count);
    return Integer.parseInt(query.list().get(0).toString());
  }

  /**
   * Method that sort all computers by name.
   */
  @SuppressWarnings("unchecked")
  public List<Computer> searchName(String search) throws SQLException {
    validateSession();
    Query<Computer> query;
    query = this.session.createQuery(searchname + "'%" + search + "%'");
    List<Computer> result = (List<Computer>) query.list();
    return result;
  }
  
  /**
   * Method that sort all computers by introduced.
   */
  @SuppressWarnings("unchecked")
  public List<Computer> sortByColumn(String type, int begin, String column) throws SQLException {    
    validateSession();
    Query<Computer> query;
    
    if (column.equals("company")) {
      query = this.session.createQuery(sortcompanyname + " order by ISNULL(cpa.name),cpa.name " + type);
    } else {
      query = this.session.createQuery(getall + " order by " + column + " " + type);
    }
    
    query.setMaxResults(50);
    query.setFirstResult(begin);
    List<Computer> result = (List<Computer>) query.list();
    LOG.info("Request succesfully executed (sort by column) size : " + result.size());
    return result;
  }
}
