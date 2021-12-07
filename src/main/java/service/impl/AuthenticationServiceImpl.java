package com.example.misael.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.ut.sm41.dto.UserDTO;
import com.ut.sm41.enums.RoleEnum;
import com.ut.sm41.exception.BusinessException;
import com.ut.sm41.model.Tokenz;
import com.ut.sm41.model.User;
import com.ut.sm41.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${spring.security.jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${spring.security.jwt.expiration.time}")
    private Long expirationTime;

    @Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
    private RSAPublicKey publicKey;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public JSONObject loginAuthentication(String username, String rawPassword) {
        Optional<User> user = userRepo.findByName(username);

        if (!user.isPresent()) {
            // 401 Unauthorized
            throw new BusinessException("Access is denied due to invalid credentials.", HttpStatus.UNAUTHORIZED, 401);
        }

        String encodedPassword = user.get().getPassword();
        boolean isAuthenticated = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!isAuthenticated) {
            // 401 Unauthorized
            throw new BusinessException("Access is denied due to invalid credentials.", HttpStatus.UNAUTHORIZED, 401);
        }

        String token = JWT.create().withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(publicKey.getEncoded()));

        Tokenz tokenz = new Tokenz();
        tokenz.setToken(tokenPrefix + token);
        JSONObject jsonObject = new JSONObject();
        JSONObject usuario = new JSONObject();
        jsonObject.put("permissions", new JSONArray());
        usuario.put("username", user.get().getName());
        usuario.put("role", RoleEnum.roleFromShort(user.get().getRole()));
        jsonObject.put("user", usuario);
        jsonObject.put("token", tokenz.getToken());
        return jsonObject;
    }

    @Override
    @Transactional
    public UserDTO createUser(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(entity.getName());
        userDTO.setStatus(entity.getStatus());
        userDTO.setPassword(passwordEncoder.encode(entity.getPassword()));
        userDTO.setRole(entity.getRole().toString());
        return userService.saveUser(userDTO);
    }

}