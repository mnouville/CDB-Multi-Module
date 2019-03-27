package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
 
  private int id;
  private String login;
  private String password;
  private String firstname;
  private String lastname;
  private String email;
  private int role;
  private boolean enabled;
  private boolean accountNonExpired;
  private boolean credentialsNonExpired;
  private boolean accountNonLocked;
  
  public User () { }
  
  public User(int id, String login, String password, String firstname, String lastname, String email, int role) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.role = role;
  }
  
  public User(int id, String login, String password, String firstname, String lastname, String email, int role,
              boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
    this.login = login;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.role = role;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
  }

  @Id   
  @Column(name="id", unique=true, nullable=false)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name="login")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  @Column(name="password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name="firstname")
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @Column(name="lastname")
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  
  @Column(name="email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  @Column(name="role")
  public int getRole() {
    return role;
  }
  
  public void setRole(int role) {
    this.role = role;
  }
  
  public boolean getEnabled() {
    return this.enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  public boolean getAccountNonExpired() {
    return this.accountNonExpired;
  }
  
  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }
  
  public boolean getCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }
  
  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }
  
  public boolean getAccountNonBlocked() {
    return this.accountNonLocked;
  }
  
  public void setAccountNonBlocked(boolean accountNonBlocked) {
    this.accountNonLocked = accountNonBlocked;
  }

}
