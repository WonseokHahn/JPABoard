package com.jpa.board.controller.api;

import com.jpa.board.repository.AccountRepository;
import com.jpa.board.model.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountApi")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/setPassword")
    public String setPassword(String email, String password) {
        accountRepository
            .findByEmail(email)
            .map(savedAccount -> {
                savedAccount.setPassword(passwordEncoder.encode(password));
                return accountRepository.save(savedAccount);
            })
            .orElseGet(Account::new);
        return "success";
    }
}

