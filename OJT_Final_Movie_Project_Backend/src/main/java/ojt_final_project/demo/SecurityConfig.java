package ojt_final_project.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/user/getuser").permitAll()
                .requestMatchers("/user/send").permitAll()
                .requestMatchers("/user/verify").permitAll()
                .requestMatchers("/user/reset-password").permitAll()	
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/user/check-email/{gmail}").permitAll()
                .requestMatchers("/user/{userId}/change-password").permitAll()
                .requestMatchers("/user/updatestatus").permitAll()
                .requestMatchers("/user/{id}/image-upload").permitAll()
                .requestMatchers("/storage/**").permitAll()
                .requestMatchers("/crew/**").permitAll()
                .requestMatchers("/crew/update/{id}").permitAll()
                .requestMatchers("/crew/getallcrew").permitAll()
                .requestMatchers("/movie_crew/**").permitAll()
                .requestMatchers("/movie_crew/crew/role-update").permitAll()
                .requestMatchers("/movie_crew/role/{movieId}/{crewId}").permitAll()
                .requestMatchers("/category/**").permitAll()
                .requestMatchers("/category/movie/{movieName}").permitAll()
                .requestMatchers("/category/save").permitAll()
                .requestMatchers("/category/delete/{id}").permitAll()
                .requestMatchers("/movie/**").permitAll()
                .requestMatchers("/movie/update/**").permitAll()
                .requestMatchers("/trendMovie/**").permitAll()
                .requestMatchers("/trendMovie/save").permitAll()    
                .requestMatchers("/trendMovie/getTrendMovie").permitAll()    
                .requestMatchers("/trendMovie/topLiked").permitAll()  
                .requestMatchers("/trendMovie/update/**").permitAll()    
                .requestMatchers("/trendMovie/check/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        // Don't override status from controller
                        response.setStatus(response.getStatus());
                    })
                );

        return http.build();
    }
}
