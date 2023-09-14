package com.huseynsharif.talkflow.entities.concretes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoResponse {


    private int id;
    private String username;
    private String email;
    private List<String> roles;

}
