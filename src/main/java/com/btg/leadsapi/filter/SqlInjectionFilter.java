package com.btg.leadsapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class SqlInjectionFilter implements Filter {

    private static final Pattern[] SQL_INJECTION_PATTERNS = {
            Pattern.compile("(?i).*\\b(OR|AND)\\b\\s*.*=" +
                    ".*"),
            Pattern.compile("(?i).*\\b" +
                    "(SELECT|UPDATE|DELETE|INSERT|DROP" +
                    "|ALTER)\\b.*")
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String queryString = httpRequest.getQueryString();
        String userAgent = httpRequest.getHeader("User-Agent");
        String referer = httpRequest.getHeader("Referer");

        if (containsSqlInjection(queryString)) {
            System.out.println("[SQLiFilter] Bloqueado por queryString: " + queryString);
            rejectRequest(httpResponse);
            return;
        }

        if (containsSqlInjection(userAgent)) {
            System.out.println("[SQLiFilter] Bloqueado por User-Agent: " + userAgent);
            rejectRequest(httpResponse);
            return;
        }
        if (containsSqlInjection(referer)) {
            System.out.println("[SQLiFilter] Bloqueado por Referer: " + referer);
            rejectRequest(httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean containsSqlInjection(String input) {
        if (input == null || input.isEmpty())
            return false;

        for (Pattern pattern : SQL_INJECTION_PATTERNS) {
            if (pattern.matcher(input).matches())
                return true;
        }
        return false;
    }

    private void rejectRequest(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"error\": \"Invalid request\", " +
                        "\"message\": \"Potential SQL " +
                        "injection detected\"}");
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}

