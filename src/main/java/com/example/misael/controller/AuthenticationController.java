package com.example.misael.controller;

import com.example.misael.UserDTO;
import com.example.misael.model.User;
import com.example.misael.repository.UserRepo;
import com.example.misael.service.impl.AuthenticationService;
import com.example.misael.service.impl.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import static com.example.misaelservice.impl.AuthenticationConstants.URL_PRIVATE_AUTHETICATION;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS })
@RequestMapping(path = URL_PRIVATE_AUTHETICATION, produces= MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse>  login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        return new ResponseEntity<>(new GenericResponse(200,"success",authenticationService.loginAuthentication(username,pwd)), HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/v1/user")
    public @ResponseBody
    UserDTO newUser(@RequestBody User user) {
        return authenticationService.createUser(user);
    }


    @GetMapping({ URL_PRIVATE_AUTHETICATION, "/voltux" })
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/api/v1/especialrequest/test")
    String test(){
        return "Success";
    }


}