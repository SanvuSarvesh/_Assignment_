package com.example.GreenStich.Services;

import com.example.GreenStich.Common.BaseResponse;
import com.example.GreenStich.DTO.UserRequestDto;
import com.example.GreenStich.Exceptions.UserAlreadyExist;
import com.example.GreenStich.Models.User;
import com.example.GreenStich.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;
    public BaseResponse addUser(UserRequestDto userRequestDto) throws UserAlreadyExist {
        BaseResponse baseResponse = new BaseResponse();
        if(userRepository.findByUname(userRequestDto.getUname())!=null) {
            throw new UserAlreadyExist("User already exists.");
        }
        User user=new User();
        user.setUname(userRequestDto.getUname());
        user.setEmail(userRequestDto.getEmail());
        User user1 = userRepository.save(user);
        baseResponse.setSuccess(true);
        baseResponse.setMessage("User Details has been saved.");
        baseResponse.setStatusCode(HttpStatus.CREATED.toString());
        baseResponse.setPayLoad(user1);
        return baseResponse;
    }
}
