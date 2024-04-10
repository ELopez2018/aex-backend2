package com.aex.platform.service;

import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.UserDTO;
import com.aex.platform.repository.UserRepository;
import lombok.extern.java.Log;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class MenuService {

    @Autowired
    private UserRepository userRepository;

    public User getUserWithMenus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Hibernate.initialize(user.getMenu());
        return user;
    }
}
