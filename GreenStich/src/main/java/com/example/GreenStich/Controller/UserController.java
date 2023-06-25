package com.example.GreenStich.Controller;

import com.example.GreenStich.Common.BaseResponse;
import com.example.GreenStich.Common.JwtRequest;
import com.example.GreenStich.Common.JwtResponse;
import com.example.GreenStich.DTO.UserRequestDto;
import com.example.GreenStich.Exceptions.BadCredentialsExceptions;
import com.example.GreenStich.Exceptions.UserAlreadyExist;
import com.example.GreenStich.Repository.UserRepository;
import com.example.GreenStich.Security.JWTHelper;
import com.example.GreenStich.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTHelper helper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/Signup")
    public ResponseEntity<BaseResponse> Signup(@RequestBody UserRequestDto userRequestDto) throws UserAlreadyExist {
        LOGGER.info("Inside the SignUp Method");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = userServices.addUser(userRequestDto);
        LOGGER.info("Process of calling API is Completed : ",(System.currentTimeMillis()-startTime));
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody JwtRequest request) {
        LOGGER.info("Inside the Login Method");
        long startTime = System.currentTimeMillis();
        BaseResponse baseResponse = new BaseResponse();
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        baseResponse.setPayLoad(response);
        baseResponse.setMessage("Process successful.");
        baseResponse.setStatusCode(HttpStatus.OK.toString());
        baseResponse.setSuccess(true);
        LOGGER.info("Process of calling API is Completed : ",(System.currentTimeMillis()-startTime));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) throws BadCredentialsExceptions{
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (Exception e) {
            throw new BadCredentialsExceptions("Invalid credential.");
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Invalid Credentials";
    }
}
