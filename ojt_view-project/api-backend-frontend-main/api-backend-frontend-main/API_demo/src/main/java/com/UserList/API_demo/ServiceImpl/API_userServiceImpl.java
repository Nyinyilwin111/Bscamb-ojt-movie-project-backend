package com.UserList.API_demo.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.UserList.API_demo.Service.Api_userService;
import com.UserList.API_demo.entity.Api_User;
@Service
public class API_userServiceImpl implements Api_userService {
	
	List<Api_User> users=new ArrayList<>();

	public API_userServiceImpl() {
	    Api_User user = new Api_User();
	    user.setId(1);
	    user.setName("User123");
	    user.setPwd("123");
	    user.setRole("user");
	    user.setDescription("It is user");
	    users.add(user);

	    Api_User admin = new Api_User();
	    admin.setId(2);
	    admin.setName("admin123");
	    admin.setPwd("123");
	    admin.setRole("admin");
	    admin.setDescription("It is admin");
	    users.add(admin);
	}

	@Override
	public boolean saveUserInfo(Api_User info) {
	   
		boolean result=users.add(info);
		System.out.println(users.get(users.size()-1));
		return result;
	}
	
	@Override
	public List<Api_User> getAll() {
		
		return users;
	}

	@Override
	public Api_User getById(int id) {		
		Api_User user=users.stream().filter(u -> u.getId()==id).collect(Collectors.toList()).get(0);
		return user;
	}

	@Override
	public String getRole(int id) {
		String role=users.stream().filter(u -> u.getId()==id).collect(Collectors.toList()).get(0).getRole();
		return role;
	}

	@Override
	public Api_User LoginCheck(String name, String password) {
	    List<Api_User> user = users.stream()
	        .filter(u -> u.getName().equals(name) && u.getPwd().equals(password))
	        .collect(Collectors.toList());
	    System.out.println("rytfuygihoijp"+ user.get(0));
	    if (!user.isEmpty())
	        return user.get(0);
	    else
	        return null;
	}



}
