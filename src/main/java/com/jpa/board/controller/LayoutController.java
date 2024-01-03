package com.jpa.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LayoutController {

    @GetMapping("/layout/page")
    public String page(Model model){
        model.addAttribute("content","페이지 레이아웃 예제입니다.");
        return "layout/page";
    }
}
