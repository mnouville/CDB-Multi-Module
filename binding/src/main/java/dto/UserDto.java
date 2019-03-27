package dto;

public class UserDto {

  private String id;
  private String login;
  private String password;
  private String firstname;
  private String lastname;
  private String email;
  private String enabled;
  private String accountNonExpired;
  private String credentialsNonExpired;
  private String accountNonLocked; 
  
  public UserDto(String id, String login, String password, String firstname, String lastname, String email,
                 String enabled, String accountNonExpired, String credentialsNonExpired, String accountNonLocked) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public String getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(String accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public String getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(String credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public String getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(String accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }
  
}
