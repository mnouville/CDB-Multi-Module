package model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for Objects Computer.
 * 
 * @author mnouville
 * @version 1.0
 */
@Entity
@Table(name = "computer")
public class Computer implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  private int id;
  private String name;
  private Date introduced;
  private Date discontinued;
  private Company company;

  /**
   * Constructor of the class Computer.
   * 
   * @param id int
   * @param name String
   * @param introduced Timestamp
   * @param discontinued Timestamp
   * @param c Company
   */
  public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, Company c) {
    this.id = id;
    this.name = name;
    if (introduced != null) {
      this.introduced = new Date(introduced.getTime());
    }
      
    if (discontinued != null) {
      this.discontinued = new Date(discontinued.getTime());
    }
      
    this.company = c;
  }

  /**
   * Constructor of the class Computer.
   * 
   * @param id int
   * @param name String
   * @param introduced Date
   * @param discontinued Date
   * @param c Company
   */
  public Computer(int id, String name, Date introduced, Date discontinued, Company c) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = c;
  }

  /**
   * Empty Constructor for the class Computer.
   */
  public Computer() { }

  /**
   * Getter of Computer, return the id of a Computer.
   * 
   * @return int that correspond to the id of the Computer
   */
  @Id   
  @Column(name="id", unique=true, nullable=false)
  public int getId() {
    return id;
  }

  /**
   * Setter of Computer, set manually the id of a Computer.
   * 
   * @param id int that correspond to the id of the Computer
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter of Computer, return the name of a Computer.
   * 
   * @return String that correspond to the name of the Computer
   */
  @Column(name="name")
  public String getName() {
    return name;
  }

  /**
   * Setter of Computer, set manually the name of a Computer.
   * 
   * @param name String that correspond to the name of the Computer
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of Computer, return the Date when the computer was introduiced.
   * 
   * @return Date
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="introduced", length=19)
  public Date getIntroduced() {
    return introduced;
  }

  /**
   * Setter of Computer, set manually the introduced Date of a Computer.
   * 
   * @param introduced Date that correspond to the introduced Date of a Computer
   */
  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }

  /**
   * Getter of Computer, return the Date when the computer was Discontinued.
   * 
   * @return Date
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="discontinued", length=19)
  public Date getDiscontinued() {
    return discontinued;
  }

  /**
   * Setter of Computer, set manually the discontinued Date of a Computer.
   * 
   * @param discontinued Date that correspond to the discontinued Date of a Computer
   */
  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  /**
   * Getter of Computer, return the Company Id of the Computer.
   * 
   * @return Int that correspond to the Company Id
   */
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="company_id")
  public Company getCompany() {
      return this.company;
  }
  
  public void setCompany(Company company) {
      this.company = company;
  }

  /**
   * toString method for the class Computer Have different utilities for the View part and for
   * debugging.
   * 
   * @return String that correspond to the status of a Computer Object
   */
  @Override
  public String toString() {
    String s = "";
    s += "ID : " + this.id + "\tName : " + this.name + "\tIntroduced : " + this.introduced
        + "\tDiscontinued : " + this.discontinued + "\tCompany_id : " + this.company.toString();
    return s;
  }

  /**
   * HashCode method for Computer Object.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + id;
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * Equals Method for Computer Object.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null) {
        return false;
      }
    } else if (!company.equals(other.company)) {
      return false;
    }
      
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }

    if (id != other.id) {
      return false;
    }
    
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
      return false;
    }
      
    if (name == null) {
      if (other.name != null) {
        return false;
      }
        
    } else if (!name.equals(other.name)) {
      return false;
    }
      
    return true;
  }
}
