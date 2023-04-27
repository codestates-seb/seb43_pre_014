package com.undefined14.pre.auth.config;

import com.undefined14.pre.auth.filter.JwtAuthenticationFilter;
import com.undefined14.pre.auth.filter.JwtVerificationFilter;
import com.undefined14.pre.auth.handler.MemberAccessDeniedHandler;
import com.undefined14.pre.auth.handler.MemberAuthenticationEntryPoint;
import com.undefined14.pre.auth.handler.MemberAuthenticationFailureHandler;
import com.undefined14.pre.auth.handler.MemberAuthenticationSuccessHandler;
import com.undefined14.pre.auth.intercepter.JwtParseInterceptor;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.auth.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "X-AUTH-TOKEN")
@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {
    private final CustomAuthorityUtils authorityUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()

                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()

                .apply(new CustomFilterConfigurer())
                .and()

                .authorizeHttpRequests(authorize -> authorize
                        //TODO
                                // 멤버십
                                .antMatchers(HttpMethod.GET, "/members").hasRole("USER")
                                .antMatchers(HttpMethod.POST, "/members").permitAll()
                                .antMatchers(HttpMethod.PATCH, "/members").hasRole("USER")
                                .antMatchers(HttpMethod.DELETE, "/members").hasRole("USER")

                                // 로그인/아웃
                                .antMatchers(HttpMethod.GET, "/logout").hasRole("USER")

                                // 게시판 - 질문
                                .antMatchers(HttpMethod.GET, "/board/questions").hasRole("USER") // 페이지네이션
                                .antMatchers(HttpMethod.GET, "/board/questions/**").hasRole("USER") // 하나 조회
                                .antMatchers(HttpMethod.POST, "/board/questions").hasRole("USER")
                                .antMatchers(HttpMethod.PATCH, "/board/questions/**").hasRole("USER")
                                .antMatchers(HttpMethod.DELETE, "/board/questions/**").hasRole("USER")

                                // 게시판 - 답변
                                .antMatchers(HttpMethod.PATCH,"/board/answers/**").hasRole("USER")
                                .antMatchers(HttpMethod.DELETE,"/board/answers/**").hasRole("USER")
                                .antMatchers(HttpMethod.POST,"/board/answers").hasRole("USER")

                                // 게시판 = 댓글
                                .antMatchers(HttpMethod.POST,"/board/questions/*/comments").hasRole("USER")
                                .antMatchers(HttpMethod.POST,"/board/answers/*/comments").hasRole("USER")
                                .antMatchers(HttpMethod.PATCH, "/board/comments/**").hasRole("USER")
                                .antMatchers(HttpMethod.DELETE,"/board/comments/**").hasRole("USER")
                );

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    }

    // PasswordEncoder Beans 객체 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // setAllowedOrigins()을 통해 모든 출처(Origin)에 대해 스크립트 기반의 HTTP 통신을 허용하도록 설정
        // 이 설정은 운영 서버 환경에서 요구사항에 맞게 변경이 가능
        configuration.setAllowedOrigins(Arrays.asList("*"));

        // setAllowedMethods()를 통해 파라미터로 지정한 HTTP Method에 대한 HTTP 통신을 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));

        // UrlBasedCorsConfigurationSource 클래스의 객체를 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 URL에 앞에서 구성한 CORS 정책(CorsConfiguration)을 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(jwtTokenizer());
    }

    @Bean
    public JwtTokenizer jwtTokenizer() {
        return new JwtTokenizer();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {  // (2-1)
        @Override
        public void configure(HttpSecurity builder) throws Exception {  // (2-2)
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);  // (2-3)

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer());  // (2-4)
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");          // (2-5)
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());  // (3) 추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());  // (4) 추가

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtils(), authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);  // (2-6)
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtParseInterceptor(jwtUtils()))
                .addPathPatterns("/boards/**");
    }
}