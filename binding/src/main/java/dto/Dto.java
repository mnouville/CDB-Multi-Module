package dto;

import java.sql.SQLException;

/**
 * DTO for objects Company and Computers.
 * @author mnouville
 * @version 1.0
 */
public class Dto {

  private String id;
  private String name;
  private String introduced;
  private String discontinued;
  private String companyid;
  private String companyname;
  
  /**
   * Constructor of CdbDto class.
   * @param id String
   * @param name String
   * @param introduced String
   * @param discontinued String
   * @param companyid String
   * @param companyname String
   */
  public Dto(String id, String name, String introduced, String discontinued, 
                String companyid, String companyname) throws SQLException {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyid = companyid;
    this.setCompanyname(companyname);
  }

  /**
   * Getter of the class CdbDto.java
   * @return int
   */
  public String getId() {
    return id;
  }

  /**
   * Setter of the class CdbDto.java
   * @param id int
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Getter of the class CdbDto.java
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Setter of the class CdbDto.java
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of the class CdbDto.java
   * @return Date
   */
  public String getIntroduced() {
    return introduced;
  }

  /**
   * Setter of the class CdbDto.java
   * @param introduced Date
   */
  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }
  
  /**
   * Getter of the class CdbDto.java
   * @return Date
   */
  public String getDiscontinued() {
    return discontinued;
  }

  /**
   * Setter of the class CdbDto.java
   * @param discontinued Date
   */
  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  /**
   * Getter of the class CdbDto.java
   * @return String
   */
  public String getCompanyId() {
    return companyid;
  }

  /**
   * Setter of the class CdbDto.java
   * @param companyid String
   */
  public void setCompanyId(String companyid) {
    this.companyid = companyid;
  }

  /**
   * Getter of the class CdbDto.java
   * @return String
   */
  public String getCompanyname() {
    return companyname;
  }

  /**
   * Setter of the class CdbDto.java
   * @param companyname String
   */
  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }
  
  public String toString() {
    String s = "";
    s += "id : " + this.getId() + ", ";
    s += "name : " + this.getName() + ", ";
    s += "introduced : " + this.getIntroduced() + ", ";
    s += "discontinued : " + this.getDiscontinued() + ", ";
    s += "companyid : " + this.getCompanyId() + ", ";
    s += "companyname : " + this.getCompanyname() + ", ";
    return s;
  }
  
}
