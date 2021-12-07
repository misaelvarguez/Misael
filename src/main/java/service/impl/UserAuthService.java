package com.example.misael.serviceimpl;

import com.example.misael.Repository.UserDao;
import com.example.misael.Role;
import com.example.misael.User;
import com.example.misael.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        List<Role> roles = userDao.getRoles(username);

        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getRole());
        }).collect(Collectors.toList());

        org.springframework.security.core.userdetails.User usr = new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), grantedAuthorities);

        return usr;
    }

}