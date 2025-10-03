package com.UserList.API_demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserList.API_demo.Service.Api_userService;
import com.UserList.API_demo.entity.Api_User;
import com.UserList.API_demo.entity.LoginAccess;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api_user")
public class Api_UserController {
	
	@Autowired
	Api_userService service;
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Api_User> getById(@PathVariable int id) {
		Api_User user=service.getById(id);
		if (user ==null) {
			return new ResponseEntity<Api_User>(user, HttpStatus.BAD_REQUEST);
		}
		else
		return new ResponseEntity<Api_User>(user, HttpStatus.OK);
		
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Api_User>> getAll() {
		List<Api_User> users=service.getAll();		
		return new ResponseEntity<List<Api_User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/getRole/{id}")
	public ResponseEntity<String> getRoleById(@PathVariable int id) {
		String role=service.getRole(id);
		if (role ==null) {
			return new ResponseEntity<String>(role, HttpStatus.BAD_REQUEST);
		}
		else
		return new ResponseEntity<String>(role, HttpStatus.OK);
		
	}
	@PostMapping("/save")
	public ResponseEntity<Api_User> saveUser(@RequestBody Api_User new_user) {
		System.out.println("testing : "+new_user.getName()+" "+new_user.getPwd());
		boolean result=service.saveUserInfo(new_user);
		if (result == false) {
			return new ResponseEntity<Api_User>(new_user, HttpStatus.BAD_REQUEST);
		}
		else
		return new ResponseEntity<Api_User>(new_user, HttpStatus.OK);
		
	}
	@PostMapping("/check")
	public ResponseEntity<Api_User> check(@RequestBody LoginAccess login) {
		System.out.println("testing1 : "+login.getName()+ " "+login.getPassword() );
		Api_User user= service.LoginCheck(login.getName(),login.getPassword());
		if (user == null) {
			return new ResponseEntity<Api_User>(user, HttpStatus.BAD_REQUEST);
		}
		else
		return new ResponseEntity<Api_User>(user, HttpStatus.OK);
		
	}
}