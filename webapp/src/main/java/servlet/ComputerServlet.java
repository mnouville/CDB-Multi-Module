package servlet;

import dto.Dto;
import dto.UserDto;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import service.ServiceComputer;
import service.ServiceUser;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Servlet implementation class ComputerServlet.
 */
@RestController
@ApiIgnore
@RequestMapping("/")
public class ComputerServlet {

  private ServiceComputer serviceComputer;
  private ServiceUser serviceUser;
  
  public ComputerServlet(ServiceComputer serviceComputer, ServiceUser serviceUser) {
    this.serviceComputer = serviceComputer;
    this.serviceUser = serviceUser;
  }
  
  @GetMapping
  public ModelAndView doGet(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                            ModelAndView modelView, Principal principal) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      int page = Integer.parseInt(pageNumber);
      List<Dto> computers = this.serviceComputer.getComputers((page - 1) * 50);
      String login = principal.getName(); 
      UserDto user = this.serviceUser.getUser(login);
      modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.addObject("user",user);
      modelView.setViewName("Dashboard");
      
      return modelView;
  }
  
  @RequestMapping(value = "/Sort")
  public ModelAndView sortByName(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                                 @RequestParam(value = "type", defaultValue = "ASC") String type,
                                 @RequestParam(value = "sort", defaultValue = "name") String sort,
                                 ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      int page = Integer.parseInt(pageNumber);
      int offset = (page - 1) * 50;
      //List<Dto> computers = this.serviceComputer.sortByColumn(type, offset, sort, "");
      //modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      if (type.equals("ASC")) {
        modelView.addObject("type", "DESC");
      } else {
        modelView.addObject("type", "ASC");
      }
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  @RequestMapping(value = "/Search")
  public ModelAndView search(WebRequest request, ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      //List<Dto> computers = this.serviceComputer.searchName(request.getParameter("search"));
      //modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  @RequestMapping(value = "/Delete")
  public ModelAndView delete(WebRequest request, ModelAndView modelView) throws SQLException {
      String[] parts = request.getParameter("selection").split(",");
      for (int i = 0; i < parts.length; i++) {
        this.serviceComputer.deleteComputer(Integer.parseInt(parts[i]));
      }
      List<Dto> dtos = this.serviceComputer.getComputers();
      int totalComputer = this.serviceComputer.getCount();
      modelView.addObject("computers", dtos);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  
}
