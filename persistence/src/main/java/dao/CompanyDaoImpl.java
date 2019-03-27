package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Company;
import model.Computer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Class that contains every method concerning Company in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
  private final String getall = "FROM Company";
  private final String get = "from Company where id = :id";
  private final String count = "SELECT COUNT(id) FROM Company";
  private final String delete = "DELETE Company where id = :id";
  private static final String deletecomputer = "DELETE Computer WHERE company_id = :id";
  
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
   * Constructor of CampanyDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  CompanyDaoImpl(HikariDataSource dataSource, SessionFactory sessionFactory ) { 
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
   * This method take a Company in parameter and add it into the Database.
   * 
   * @param c Company
   */
  @Override
  public void addCompany(Company c) throws SQLException {
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
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);;
      }
      hibernateException.printStackTrace();
    } finally {
        session.close();
    }  
    LOG.info("COMPUTER ADDED");
  }

  /**
   * This method return a list of every companies in the Database.
   * 
   * @return List of Object Company
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Company> getCompanies() throws SQLException {
    validateSession();
    List<Company> result = (List<Company>) this.session.createQuery(getall).list();
    return result;
  }

  /**
   * This method delete companies by ID.
   * 
   * @param id int
   */
  @SuppressWarnings("unchecked")
  @Override
  public void deleteCompany(int id) throws SQLException {    
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<Computer> query = this.session.createQuery(deletecomputer);
      query.setParameter("id", id);
      query.executeUpdate();
      query = this.session.createQuery(delete);
      query.setParameter("id", id);
      query.executeUpdate();
      tx.commit();
      LOG.info("COMPANY DELETED");
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
   * This method return the Company with this ID in the Database.
   * 
   * @param i int
   * @return Object Company
   */
  @SuppressWarnings("unchecked")
  @Override
  public Company getCompany(int i) throws SQLException {
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<Company> query = this.session.createQuery(get);
      query.setParameter("id", i);
      List<Company> result = (List<Company>) query.list();
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
   * This method return a true if a Company ID exist in the Database.
   * 
   * @return boolean
   */
  @SuppressWarnings("rawtypes")
  @Override
  public boolean companyExist(int id) throws SQLException {
    LOG.info("ROW COUNT requested");
    validateSession();
    Query query = this.session.createQuery(count);
    return (Integer.parseInt(query.list().get(0).toString())>0);
  }
}
