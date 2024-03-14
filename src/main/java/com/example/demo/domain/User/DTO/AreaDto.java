package com.example.demo.domain.User.DTO;

import com.example.demo.domain.User.User;
import lombok.Data;

@Data
public class AreaDto {
    private String phoneNumber;
    private String areaName;

    public User toEntity() {
        return new User(this.phoneNumber, this.areaName);
    }
    public void updateArea(String areaName){
        this.areaName = areaName;
    }
}
