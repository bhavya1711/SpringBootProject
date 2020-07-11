package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.AlienRepo;

//Instead of specifying @ResponseBody for Every method as it converts tells spring to consider response and
//not jsp
//We can instead annotate our controller to @RestController so it will always expect response and not search for JSP
//We can remove @ResponseBody deom every method
@RestController
//@Controller
public class AlienController {
	
	//Spring will Inject the implemented class obj for AlienRepo internally
	@Autowired
	private AlienRepo repo;
	
	//Mapping of the request getAlien when url hit is localhost:8080/aliens
	@GetMapping("/aliens")
	//When we implement MVC and we write return type as String then the method by default expects .jsp file
	//In this case if we want to tell Spring that do not expect JSP file instead expect response then we need
	// to specify @ResponseBody- It prints whatever is there in the response and not searches for jsp
	// Now since we use JpaRepo we don't need to convert resposne to String.
	//Also we get our response as JSON as in maven dependecy jackson-core jar converts Java Obj to JSON
	public List<Alien> getAlien() {
		return repo.findAll();
	}
	
	//We use @PathVariable so that value of {aid} in @RequestMapping gets mapped to id in getAlienById
	//By default Spring Boot produces response as JSON, but suppose we want support for XML also
	//Then we need to add maven dependecy for jackson-xml, then response format will be avilable in JSON & XML
	//Now suppose we only want reposne to get restircted to XML then we can use
	//	@RequestMapping(path="/aliens/{aid}",produces={application/xml})- It only sends response as XML
	@GetMapping("/aliens/{aid}")
	//We use Optional so that if Alien obj of specified Id is not present it returns some optional value
	//as null but if we write orElse then we get that value(In our case empty Alien obj)
	public Alien getAlienById(@PathVariable("aid") int aid) {
		return repo.findById(aid).orElse(new Alien());
	}
	
	@DeleteMapping("/alien/{aid}")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = repo.getOne(aid);
		repo.delete(a);
		return "deleted";
	}
	
	@PutMapping(path="/alien/{aid}",consumes = {"application/json"})
	//For server method to accept json we also need to specify the @RequestBody to consume JSON 
	public Alien updateAlien(@PathVariable int aid ,@RequestBody Alien alien) {
		Alien a = repo.getOne(aid);
		a.setAname(alien.getAname());
		a.setTech(alien.getTech());
		repo.save(a);
		return alien;
	}
	
	//To identify wether the req is get or post we annotate it with @PostMapping or @GetMapping
	//In @PostMapping we can specify the path as well as the input that it consumes
	//It only consumes JSON and not XML
	@PostMapping(path="/alien", consumes = {"application/json"})
	//For server method to accept json we also need to specify the @RequestBody to consume JSON 
	public Alien addAlien(@RequestBody Alien alien) {
		repo.save(alien);
		return alien;
	}
}
