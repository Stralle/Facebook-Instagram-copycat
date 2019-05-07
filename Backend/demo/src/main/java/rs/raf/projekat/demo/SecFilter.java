package rs.raf.projekat.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.security.TokenHandlerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecFilter implements Filter {

    @Autowired
    private TokenHandlerService tokenHandlerService;

    private static final String HEADER_KEY = "X-AUTH-TOKEN";
    public static final String METHOD_OPTIONS = "OPTIONS";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET,PUT,PATCH,DELETE,POST,OPTIONS");
        httpResponse.addHeader("Access-Control-Max-Age", "3600");
        httpResponse.addHeader("Access-Control-Allow-Headers",
                "Origin, x-requested-with, X-AUTH-TOKEN, Content-Type, Accept");

        if (httpRequest.getRequestURI().contains("/auth")) {
            chain.doFilter(request, response);
        } else {
            // DRAZA'S M@G1C B00GFEEX
            if(httpRequest.getMethod().equals(METHOD_OPTIONS)){
                chain.doFilter(request, response);
                return;
            }
            String token = httpRequest.getHeader(HEADER_KEY);
            if(token == null || token.isEmpty()) {
                httpResponse.setStatus(401);
                return;
            }
            User user = tokenHandlerService.getUserFromToken(token);
            httpRequest.setAttribute("USER", user);
            chain.doFilter(request, response);
        }

    }

}
