package servlet;

import java.sql.SQLException;
import java.util.List;

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
import service.ServiceComputer;

@RestController
@RequestMapping("/webservice/computer/")
public class ControllerRestComputer {
  
  private ServiceComputer serviceComputer;
  
  public ControllerRestComputer(ServiceComputer serviceComputer) {
      this.serviceComputer = serviceComputer;
  }
  
  @GetMapping(value = "/{id}")
  public Dto getComputerRest(@PathVariable String id) throws NumberFormatException, SQLException {
      Dto computer = this.serviceComputer.getComputer(Integer.parseInt(id));
      return computer;
  }
  
  @GetMapping(value = "/")
  public List<Dto> getComputersRest() throws NumberFormatException, SQLException {
      List<Dto> listComputers = this.serviceComputer.getComputers();
      return listComputers;
  }
  
  @GetMapping(value = "/page/")
  public List<Dto> getComputersRest(@RequestParam(value = "page", defaultValue = "1") String pageNumber) throws SQLException {
      int page = Integer.parseInt(pageNumber);
      int offset = (page - 1) * 50;
      List<Dto> listComputers = this.serviceComputer.getComputers(offset);
      return listComputers;
  }
  
  @PostMapping(path = "/")
  public void addMember(@RequestBody Dto dto) throws SQLException {
      System.out.println(dto.toString());
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
