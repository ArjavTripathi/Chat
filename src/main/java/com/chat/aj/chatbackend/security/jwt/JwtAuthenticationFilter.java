package com.chat.aj.chatbackend.security.jwt;

import com.chat.aj.chatbackend.Service.User.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtTokenProvider;

    private final UserDetailsService userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                            throws ServletException, IOException {

        try{
            String jwt = jwtTokenProvider.getJwtFromHeader(request);

            if(jwt != null && jwtTokenProvider.validateToken(jwt)){
                String username = jwtTokenProvider.getUserNameFromJwtToken(jwt);
                UserDetails userDetail = userDetails.loadUserByUsername(username);
                if(userDetail != null){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);

    }
}
