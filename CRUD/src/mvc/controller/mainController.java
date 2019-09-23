package mvc.controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mvc.model.UTIL;
import mvc.model.DAO;
import mvc.model.USERS;

@Controller
public class mainController {
	
	 @RequestMapping("/")
	 public String execute() 
	 {
		 return "login";
	 }
	 
	 @RequestMapping("/login")
	 public String login(USERS user, ModelMap model) throws ClassNotFoundException, SQLException 
	 {
		 DAO dao = new DAO();
		
		 if (dao.checkPasssword(user.getName(), user.getPassword()))
		 {
			model.addAttribute("user", user.getName());
			return "tasks";
		 }
		 
		 else
		 {
			model.addAttribute("message", "Authentication failed!");
			return "login";
		 }
	 }

	 @RequestMapping(value = "/singup", method = {RequestMethod.POST})
	 public String singup(USERS user, ModelMap model) throws ClassNotFoundException, SQLException 
	 {
		 DAO dao = new DAO();
		 
		 if(user.getName().equals(""))
		 {
			model.addAttribute("message", "Empty name");
			return "singup";
		 }
		 
		 else if(user.getPassword().equals("") || user.getRepeatPassword().equals(""))
		 {
			model.addAttribute("message", "Enter a password");
			return "singup";
		 }
		 
		 else if (user.getPassword().equals(user.getRepeatPassword()))
		 {
			dao.createUser(user.getName(), user.getPassword());
			model.addAttribute("user", user.getName());
			return "tasks";
		 }
		  
		 else
		 {
			model.addAttribute("message", "Passwors do not match!");
			return "singup";
		 }
	 }
	 
	 @RequestMapping(value = "/singup", method = {RequestMethod.GET})
	 public String singup()
	 {
		 return "singup";
	 }
	 
	 @RequestMapping(value = "/add", method = {RequestMethod.GET})
	 public String add(UTIL add, ModelMap model) throws ClassNotFoundException, SQLException
	 {
		DAO dao = new DAO();

		model.addAttribute("list", dao.getList(add.getUser(), add.getDay()));

		return "addw";
	 }
	 
	 @RequestMapping(value = "/hm", method = {RequestMethod.GET})
	 public String hm(UTIL hm, ModelMap model) throws SQLException, ClassNotFoundException
	 {
		DAO dao = new DAO();
		model.addAttribute("list", dao.getList(hm.getUser(), hm.getDay()));

		return "hm";
	 }
	 
	 @RequestMapping(value = "/add", method = {RequestMethod.POST})
	 public String addP(UTIL add, ModelMap model) throws ClassNotFoundException, SQLException
	 {
		DAO dao = new DAO();
		
		String list = dao.getList(add.getUser(), add.getDay());
		
		if (list.length() != 0) { dao.updateColumn(list + " _ " + add.getWorkout(), add.getUser(), add.getDay()); }
		else { dao.updateColumn(add.getWorkout(), add.getUser(), add.getDay()); }
		model.addAttribute("user", add.getUser());
		
		return "tasks";
	 }
	 
	 @RequestMapping("/edit")
	 public String edit(UTIL edit, ModelMap model) throws ClassNotFoundException, SQLException
	 {	
		DAO dao  = new DAO();
		String newValue = dao.getList(edit.getUser(), edit.getDay()).replace(edit.getWorkout(), edit.getReplace());

		dao.updateColumn(newValue, edit.getUser(), edit.getDay());
		model.addAttribute("user", edit.getUser());
		
		return "tasks";
	 }
	 
	 @RequestMapping("/del")
	 public String del(UTIL delete, ModelMap model) throws ClassNotFoundException, SQLException
	 {	
		DAO dao  = new DAO();
		String[] workouts = dao.getList(delete.getUser(), delete.getDay()).split(" _ ");
		List<String> wordList = new ArrayList<String>(Arrays.asList(workouts));  
		wordList.remove(delete.getWorkout());
		
		String update = String.join(" _ ", wordList);
		
		dao.updateColumn(update, delete.getUser(), delete.getDay());
		model.addAttribute("user", delete.getUser());
		
		return "tasks";
	 }
}