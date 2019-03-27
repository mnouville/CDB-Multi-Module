package mappers;

import dto.Dto;
import dto.UserDto;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.User;
import model.Computer;
import service.ServiceCompany;

@Component
public class MapperDto {

  private static MapperDto instance;
  
  @Autowired
  private ServiceCompany serviceCompany;
  
  /**
   * Method that convert a Computer to a DTO.
   * @param c Computer
   * @return CdbDto
   */
  public Dto computerToDto(Computer c) throws SQLException {
    return new Dto(c.getId() + "",c.getName(), 
                      parseDateToString(c.getIntroduced()),
                      parseDateToString(c.getDiscontinued()),
                      c.getCompany() != null ? c.getCompany().getId() + "" : null,
                      c.getCompany() != null ? c.getCompany().getName() : null);
  }
  
  /**
   * Method that convert a CdbDto to a Computer.
   * @param dto CdbDto
   * @return Computer
   */
  public Computer dtoToComputer(Dto dto) {
    Computer c = new Computer();
    
    try {
      int id = Integer.parseInt(dto.getId());
      c.setId(id);
      c.setName(dto.getName());

      if (!dto.getIntroduced().equals("")) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date introduced = formatter.parse(dto.getIntroduced());
        c.setIntroduced(introduced);
      }
      
      if (!dto.getDiscontinued().equals("")) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date discontinued = formatter.parse(dto.getDiscontinued());
        c.setDiscontinued(discontinued);
      }

      if (dto.getCompanyId() != null) {
        c.setCompany(this.serviceCompany.getCompany(Integer.parseInt(dto.getCompanyId())));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return c;
  }
  
  /**
   * Method that convert a list of computers into a list of Dtos.
   * @param computers List of computer
   * @return List of Dtos
   */
  public List<Dto> computersToDtos(List<Computer> computers) throws SQLException {
    List<Dto> dtos = new ArrayList<Dto>();
    for (int i = 0; i < computers.size(); i++) {
      dtos.add(computerToDto(computers.get(i)));
    }
    return dtos;
  }
  
  /**
   * Parse a date object for printing.
   *
   * @param date The date object
   * @return The output string
   */
  private String parseDateToString(Date date) {
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH),
          calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    return "";
  }
  
  public UserDto userToUserDto(User user) {
    return new UserDto(user.getId()+"", user.getLogin(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getLastname(),
                       user.getEnabled()+"", user.getAccountNonExpired()+"", user.getCredentialsNonExpired()+"", 
                       user.getAccountNonBlocked()+"" );
  }
  
  public User dtoToUser(UserDto user) {
    int id = Integer.parseInt(user.getId());
    boolean enabled = user.getEnabled() == "true" ? true : false;
    boolean accountNonExpired = user.getAccountNonExpired() == "true" ? true : false;
    boolean credentialsNonExpired = user.getCredentialsNonExpired() == "true" ? true : false;
    boolean accountNonBlocked = user.getAccountNonLocked() == "true" ? true : false;
    return new User(id,user.getLogin(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(),
                       enabled, accountNonExpired, credentialsNonExpired, accountNonBlocked);
  }
  
  public List<UserDto> usersToUserDtos(List<User> users) {
    List<UserDto> list = new ArrayList<UserDto>();
    for (int i = 0; i < users.size(); i++) {
      list.add(userToUserDto(users.get(i)));
    }
    return list;
  }

  /**
   * Get the unique instance of MapperDto.
   * @return MapperDto
   */
  public static MapperDto getInstance() {
    if (instance == null) {
      instance = new MapperDto();
    }
    return instance;
  }
  
}
