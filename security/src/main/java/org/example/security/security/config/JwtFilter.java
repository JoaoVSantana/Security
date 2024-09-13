package org.example.security.security.config;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.security.entity.Usuario;
import org.example.security.security.utils.CookieUtils;
import org.example.security.security.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws ServletException, IOException {
        //URI é só o caminho apos o localhost, o endpoint basicamente
        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (!isPublicEndPoint(uri, method)) {
            try {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> cookieOptional = Arrays.stream(cookies).
                    filter(cookie -> cookie.getName().equals("USERTOKEN")).findFirst();

                if (cookieOptional.isPresent()) {
                    Cookie cookie = cookieOptional.get();
                    String token = cookie.getValue();
                    Authentication authentication = jwtUtils.validarToken(token);
                    //O securityContext é um objeto que guarda o contexto de segurança da aplicação
                    // setando o usuario como autenticado
                    SecurityContext securityContext =
                            SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                    
                    if(!uri.equals("/auth/logout") && method.equals("POST")){
                        String novoToken = jwtUtils.criarToken((Usuario) authentication.getPrincipal());
                        cookie = cookieUtils.criarCookie(novoToken);
                        response.addCookie(cookie);
                    }
                }
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isPublicEndPoint(String uri, String method) {
        return uri.equals("/auth/login") && method.equals("POST");
    }
}
