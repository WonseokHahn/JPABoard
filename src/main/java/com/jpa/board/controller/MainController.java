package com.jpa.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/main/index")
    public String index(Model model){
        model.addAttribute("message", "안녕하세요!");
        return "main/index";
    }
}
