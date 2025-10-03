package com.UserList.API_demo.Service;

import java.util.List;

import com.UserList.API_demo.entity.Api_User;

public interface Api_userService {
	List<Api_User> getAll();
	Api_User getById(int id);
	String getRole(int id);
	boolean saveUserInfo(Api_User info);
	Api_User LoginCheck(String name, String password);


}
