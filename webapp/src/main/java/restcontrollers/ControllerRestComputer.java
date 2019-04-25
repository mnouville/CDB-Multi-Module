package restcontrollers;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<Dto>> getComputers(HttpServletRequest req) throws Exception {
      Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
        List<Dto> listComputers = this.serviceComputer.getComputers();
        return new ResponseEntity<List<Dto>>(listComputers,HttpStatus.OK);
      } else {
        return new ResponseEntity<List<Dto>>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @GetMapping(value = "/page")
  public ResponseEntity<List<Dto>> getComputersByPage(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                                                      @RequestParam(value = "limit", defaultValue= "10") String limit, HttpServletRequest req) 
                                                          throws NumberFormatException, Exception {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
        int offset = (Integer.parseInt(pageNumber) - 1) * Integer.parseInt(limit);
        List<Dto> listComputers = this.serviceComputer.getComputers(offset,Integer.parseInt(limit));
        System.out.println("Size : " + listComputers.size());
        return new ResponseEntity<List<Dto>>(listComputers,HttpStatus.OK);
      } else {
        return new ResponseEntity<List<Dto>>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @GetMapping(value = "/count")
  public ResponseEntity<Integer> getCount(HttpServletRequest req) throws Exception {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
        return new ResponseEntity<Integer>(this.serviceComputer.getCount(),HttpStatus.OK);
      } else {
        return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @PostMapping(path = "/")
  public void addComputer(@RequestBody Dto dto, HttpServletRequest req) throws Exception {
      
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
          DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
          
          if (!(dto.getIntroduced().isEmpty()) && dto.getIntroduced() != null ) {
            Date intro = formatter.parse(dto.getIntroduced().replace('/', '-'));
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dto.setIntroduced(formatter.format(intro));
          } 
          
          formatter = new SimpleDateFormat("dd-MM-yyyy");
          
          if (!(dto.getDiscontinued().isEmpty()) && dto.getDiscontinued() != null ) {
            Date disc = formatter.parse(dto.getDiscontinued().replace('/', '-'));
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dto.setDiscontinued(formatter.format(disc));
          }
          
          this.serviceComputer.addComputer(dto);
      } 
  }
  
  @DeleteMapping("/{id}")
  public void deleteComputer(@PathVariable String id, HttpServletRequest req) throws Exception
  {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
        this.serviceComputer.deleteComputer(Integer.parseInt(id));
      }
      
  }
  
  @PutMapping("/{id}")
  public void updateComputer(@PathVariable String id, @RequestBody Dto dto, HttpServletRequest req) throws Exception
  {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
          DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
  
          if (!(dto.getIntroduced().isEmpty()) && dto.getIntroduced() != null ) {
            Date intro = formatter.parse(dto.getIntroduced().replace('/', '-'));
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dto.setIntroduced(formatter.format(intro));
          } 
          
          formatter = new SimpleDateFormat("dd-MM-yyyy");
          
          if (!(dto.getDiscontinued().isEmpty()) && dto.getDiscontinued() != null ) {
            Date disc = formatter.parse(dto.getDiscontinued().replace('/', '-'));
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dto.setDiscontinued(formatter.format(disc));
          }
          
          this.serviceComputer.updateComputer(dto);
      }  
  }
  
  @GetMapping(value = "/Sort")
  public ResponseEntity<List<Dto>> sortByName(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                                              @RequestParam(value = "type", defaultValue = "ASC") String type,
                                              @RequestParam(value = "sort", defaultValue = "name") String sort,
                                              @RequestParam(value = "search", defaultValue = "") String search,
                                              @RequestParam(value = "limit", defaultValue = "15") String limit,
                                              HttpServletRequest req) throws NumberFormatException, Exception {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
          System.out.println(search);
          int page = Integer.parseInt(pageNumber);
          int offset = (page - 1) * 50;
          List<Dto> computers = this.serviceComputer.sortByColumn(type, offset, sort, search, Integer.parseInt(limit));
          return new ResponseEntity<List<Dto>>(computers,HttpStatus.OK);
      } else {
          return new ResponseEntity<List<Dto>>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @GetMapping(value = "/Search")
  public ResponseEntity<List<Dto>> search(@RequestParam(value = "name") String name, 
                                          @RequestParam(value = "page", defaultValue = "1") String page,
                                          @RequestParam(value = "limit", defaultValue = "15") String limit, HttpServletRequest req) throws Exception {
      Token token = new Token(req.getHeader("authorization"));
      
      if ( token.isValidToken()) {
          List<Dto> computers = this.serviceComputer.searchName(name, Integer.parseInt(page), Integer.parseInt(limit));
          return new ResponseEntity<List<Dto>>(computers,HttpStatus.OK);
      } else {
          return new ResponseEntity<List<Dto>>(HttpStatus.BAD_REQUEST);
      }
      
  }
}
