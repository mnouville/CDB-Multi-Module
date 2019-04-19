package restcontrollers;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.Dto;
import model.Token;
import service.ServiceComputer;

@RestController
@RequestMapping("/api/computers/")
@CrossOrigin
public class ControllerRestComputer {
  
  private ServiceComputer serviceComputer;
  
  public ControllerRestComputer(ServiceComputer serviceComputer) {
      this.serviceComputer = serviceComputer;
  }
  
  @GetMapping(value = "/{id}")
  public Dto getComputer(@PathVariable String id) throws NumberFormatException, SQLException {
      Dto computer = this.serviceComputer.getComputer(Integer.parseInt(id));
      return computer;
  }
  
  @GetMapping(value = "/")
  public List<Dto> getComputers(HttpServletRequest req) throws Exception {
      /*Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
        List<Dto> listComputers = this.serviceComputer.getComputers();
        return listComputers;
      } */
      List<Dto> listComputers = this.serviceComputer.getComputers();
      return listComputers;
    
  }
  
  @GetMapping(value = "/page")
  public List<Dto> getComputersByPage(@RequestParam(value = "page", defaultValue = "1") String pageNumber) throws SQLException {
      int page = Integer.parseInt(pageNumber);
      int offset = (page - 1) * 50;
      List<Dto> listComputers = this.serviceComputer.getComputers(offset);
      return listComputers;
  }
  
  @PostMapping(path = "/")
  public void addMember(@RequestBody Dto dto) throws SQLException, ParseException {
      DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      Date intro = formatter.parse(dto.getIntroduced().replace('/', '-'));
      Date disc = formatter.parse(dto.getDiscontinued().replace('/', '-'));
      formatter = new SimpleDateFormat("yyyy-MM-dd");
      dto.setIntroduced(formatter.format(intro));
      dto.setDiscontinued(formatter.format(disc));
      
      this.serviceComputer.addComputer(dto);
  }
  
  @DeleteMapping("/{id}")
  public void deleteComputer(@PathVariable String id) throws NumberFormatException, SQLException
  {
      this.serviceComputer.deleteComputer(Integer.parseInt(id));
  }
  
  @PutMapping("/{id}")
  public void updateUser(@PathVariable String id, @RequestBody Dto dto) throws SQLException
  {
      System.out.println(dto.getName());
      this.serviceComputer.updateComputer(dto);
  }
  
  @GetMapping(value = "/Sort")
  public List<Dto> sortByName(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                              @RequestParam(value = "type", defaultValue = "ASC") String type,
                              @RequestParam(value = "sort", defaultValue = "name") String sort) throws SQLException {
      int page = Integer.parseInt(pageNumber);
      int offset = (page - 1) * 50;
      List<Dto> computers = this.serviceComputer.sortByColumn(type, offset,sort);
      return computers;
  }
  
  @GetMapping(value = "/Search")
  public List<Dto> search(@RequestParam(value = "name") String name) throws SQLException {
      List<Dto> computers = this.serviceComputer.searchName(name);
      return computers;
  }
}