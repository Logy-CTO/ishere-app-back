package com.example.demo.global.controller;

import com.example.demo.domain.User.SignUpDto;
import com.example.demo.domain.User.User;
import com.example.demo.global.dto.JoinDTO;
import com.example.demo.global.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * JoinController 사용 안합니다. UserServiceImpl 참조 하세요!
 */


@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}

