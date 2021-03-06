package com.demirci.infonal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demirci.infonal.model.User;
import com.demirci.infonal.service.UserService;

/*
 * Class for declaration to obtain and modify user information
 * @author Mustafa Oguz Demirci
 */

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	    * Returns the list of users
	    * @return The model includes user list.
	*/
	@RequestMapping(value = "/", method = RequestMethod.GET) 
	public ModelAndView getUserLists(){
		ModelAndView model = new ModelAndView("home");
		model.addObject("userList", userService.listUser());
		System.out.println("burda");
		return model;
	}	
	
	/**
	    * Adds a new user to the list.
	    * @param request The request coming from client
	    * @return A string includes html data 
	*/
	@RequestMapping(value="/add", method=RequestMethod.POST)		   
    public @ResponseBody String addUser(HttpServletRequest request){    
	    User user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("telephone"));	
		User addedUser = userService.addUser(user);
		String html = "<tr id = userTable" + addedUser.getId() + ">" +			
				  "<td>" + addedUser.getName() + "</td>" + 					
				  "<td>" + addedUser.getSurname() + "</td>" +
				  "<td>" + addedUser.getTelephone() + "</td>" +
				  "<td>" +
				  	"<input type=\"button\" class=\"button\" name=\"deleteButton\" value=\"Delete\" onclick=\"deleteConfirm('" + addedUser.getId() + "');\"/>" +
				  	"<input type=\"button\" class=\"button\" name=\"updateButton\" value=\"Update\" onclick=\"updateConfirm('" + addedUser.getId() + "');\"/>" +
				  "</td>" +				
			  "</tr>";

		return html;
	}
	
	/**
	    * Deletes the user with given identity
	    * @param request The request coming from client
	*/
	@RequestMapping(value = "/delete", method = RequestMethod.GET) 
	public @ResponseBody void deleteUser(HttpServletRequest request){
		//User user = userService.findUser(request.getParameter("id"));
		userService.deleteUser(request.getParameter("id"));
	}	
	
	/**
	    * Updates the user with given identity
	    * @param request The request coming from client
	    * @return A string includes html data 
	*/
	@RequestMapping(value = "/update", method = RequestMethod.GET) 
	public @ResponseBody String updateUser(HttpServletRequest request){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String telephone = request.getParameter("telephone");
		userService.updateUser(id, name, surname, telephone);
		
		String html = "<tr id = userTable" + id + ">" +			
						  "<td>" + name + "</td>" + 					
						  "<td>" + surname + "</td>" +
						  "<td>" + telephone + "</td>" +
						  "<td>" +
						  	"<input type=\"button\" class=\"button\" name=\"deleteButton\" value=\"Delete\" onclick=\"deleteConfirm('" + id + "');\"/>" +
						  	"<input type=\"button\" class=\"button\" name=\"updateButton\" value=\"Update\" onclick=\"updateConfirm('" + id + "');\"/>" +
						  "</td>" +				
					  "</tr>";
		
		System.out.println(html);
		
		return html;
	}	
}
