package com.example.project3355.global.jwt;

import com.example.project3355.global.common.CommonResponseDto;
import com.example.project3355.user.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  private final UserDetailsService userDetailsService;

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = jwtUtil.resolveToken(request);

    if (Objects.nonNull(token)) { // 토큰이 Null이 아니면
      if (jwtUtil.validationToken(token)) { // 토큰을 validationToken 메소드로 검사
        Claims info = jwtUtil.getUserInfoFromToken(token); // 토큰에서 user 정보를 가져옴

        // 인증 정보에 유저정보(username) 넣기
        // username -> user 조회
        String username = info.getSubject();
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // userDetails에 저장
        UserDetails userDetails = userDetailsService.getUserDetails(username);

        // authentication의 principal에 저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        // 저장한 내용을 securityContent에 저장
        context.setAuthentication(authentication);

        // securityContext를 SecurityContextHolder에 저장
        SecurityContextHolder.setContext(context);
        // 위 작업으로 인해 @AuthenticationPrincipal로 조회 가능

      } else {
        // 인증 토큰이 유효하지 않을 떄
        CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않습니다.",
            HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}