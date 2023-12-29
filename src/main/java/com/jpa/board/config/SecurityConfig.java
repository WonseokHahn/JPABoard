package com.jpa.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // SecurityFilterChain을 생성하는 메소드.
        // 이 빈은 Spring security 의 필터체인을 구성하는 역할을 한다.
        // @Bean 어노테이션을 사용하여 빈을 정의하고, 'HttpSecurity 를 매개변수로 받아 필요한 보안설정을 수행한다.

        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/account/login", "/dist/**", "/plugins/**", "/accountApi/setPassword", "/layout/page").permitAll()
                        .anyRequest().authenticated())
                // HTTP 요청에 대한 인가 규칙을 설명한다.
                // permitAll() : 누구나 접근할수 있도록 허용
                // 그 외의 요청은 인증된 사용자만 접근할 수 있다.

                // 로그인 설정 '/login' 으로 들어오는 로그인 요청을 처리한다.
                .formLogin((form) -> form
                        .loginProcessingUrl("/login")
                        .loginPage("/account/login") // 로그인 페이지
                        .permitAll()
                        .failureUrl("/account/login") // 로그인 실패 시 이동 할 페이지
//                        .usernameParameter("email") // 사용자 이름 파라미터
                        .defaultSuccessUrl("/main/index")
                ) // 로그인 성공시 이동페이지

                // 로그아웃 설정
                .logout((out) -> out.logoutSuccessUrl("/account/login") // 로그아웃성공 페이지
                        .logoutUrl("/logout") //로그아웃성공 시 이동할 url
                        .invalidateHttpSession(true) /*로그아웃시 세션 제거*/
                        .deleteCookies("JSESSIONID") /*쿠키 제거*/
                        .clearAuthentication(true) /*권한정보 제거*/
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());
        // CSRF(Cross-Site Request Forgery) 보호를 비활성화 한다.
        // .csrf(csrf -> csrf.disabl())' : CSRF 보호를 비활성화한다. 이 설정은 웹 애필리케이션이 외부 사이트에서 발생하는 요청을 막지 않는다.

        return http.build();
        // 마지막으로 SecurityFilterChain 을 반환한다.
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select email as user_id, password, CASE WHEN enabled = 1 THEN true ELSE false END "
                        + "from account "
                        + "where email = ?")
                .authoritiesByUsernameQuery("select a.email as user_id, c.code "
                        + "from account a inner join account_role b on b.account_id=a.id  "
                        + "inner join role c on b.role_id = c.id "
                        + "where a.email = ? ")
                .groupAuthoritiesByUsername(
                        "select b.id, b.name, b.role_type "
                                + "from account a "
                                + "inner join company b on b.id = a.company_id "
                                + "where a.email = ?"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
