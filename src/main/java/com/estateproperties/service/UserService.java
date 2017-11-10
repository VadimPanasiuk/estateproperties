package com.estateproperties.service;


import com.estateproperties.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
