package com.adega.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.Services.UserService;
import com.adega.domains.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/new")
	public String user(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/form-user";
	}
	
	@GetMapping("/list")
	public String listUser(Model model) {
		List<User> listUser = userService.getListUser();
		model.addAttribute("listUser", listUser);
		return "user/list-user";
	}
	
	@GetMapping("/edit/{userid:.+}")
	public String editUser(Model model, @PathVariable(value="userid") String userId) {
		User user = null;
		if(!StringUtils.isEmpty(userId)) {
			user = userService.findUser(userId);
		}
		model.addAttribute("user", user);
		return "user/form-user";
	}
	
	@GetMapping("/delete/{userid:.+}")
	public String deleteUser(Model model, @PathVariable(value="userid") String userId) {
		if(!StringUtils.isEmpty(userId)) {
			userService.deleteUser(userId);
		}
		return listUser(model);
	}
	
	@PostMapping("/save")
	public String saveUser(Model model,User user) {
		userService.save(user);
		return listUser(model);
	}
}
