package com.estateproperties.service;

import java.util.Arrays;
import java.util.HashSet;

import com.estateproperties.model.Role;
import com.estateproperties.model.User;
import com.estateproperties.repository.RoleRepository;
import com.estateproperties.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service("userService")
public class UserServiceImpl implements UserService{

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Qualifier("userRepository")
	@Autowired
	private UserRepository userRepository;
	@Qualifier("roleRepository")
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	private void initAdmin(){
		Role admin = new Role();
		admin.setId(1);
		admin.setRole("ADMIN");
		roleRepository.save(admin);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		LOG.info(String.format("User saving: [%s]", user));
		userRepository.save(user);
		LOG.info(String.format("User saved: [%s]", user));

	}

}
