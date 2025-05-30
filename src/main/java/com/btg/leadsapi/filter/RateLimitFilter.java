package com.btg.leadsapi.filter;

import com.btg.leadsapi.config.RateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    private RateLimiter rateLimiter;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = rateLimiter.getClientIp(request);
        RateLimiter.RequestCounter counter = rateLimiter.getRequestCounter(clientIp);

        // Adiciona headers informativos em todas as respostas
        response.setHeader("X-RateLimit-Limit", String.valueOf(RateLimiter.REQUEST_LIMIT));
        response.setHeader("X-RateLimit-Remaining",
                String.valueOf(Math.max(0, RateLimiter.REQUEST_LIMIT - counter.getCount())));
        response.setHeader("X-RateLimit-Reset",
                String.valueOf(TimeUnit.MILLISECONDS.toSeconds(
                        counter.getFirstRequestTime() +
                                TimeUnit.MINUTES.toMillis(RateLimiter.TIME_LIMIT))));

        if (!rateLimiter.allowRequest(request)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Rate limit exceeded\", " +
                            "\"message\": \"Maximum " + RateLimiter.REQUEST_LIMIT +
                            " requests per minute.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}