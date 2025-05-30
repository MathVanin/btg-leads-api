package com.btg.leadsapi.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimiter {

    // Armazena o contador de requisições por IP
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    // Limite de requisições por período
    public static final int REQUEST_LIMIT = 30;

    // Período de tempo em minutos
    public static final long TIME_LIMIT = 1;

    public boolean allowRequest(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        RequestCounter counter =
                requestCounts.computeIfAbsent(clientIp,
                        k -> new RequestCounter());

        synchronized (counter) {
            long currentTime = System.currentTimeMillis();

            // Se o período expirou, reinicia o contador
            if (currentTime - counter.getFirstRequestTime() > TimeUnit.MINUTES.toMillis(TIME_LIMIT))
                counter.reset(currentTime);

            // Verifica se excedeu o limite
            if (counter.getCount() >= REQUEST_LIMIT)
                return false;

            // Incrementa o contador
            counter.increment();
            return true;
        }
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

    public RequestCounter getRequestCounter(String clientIp) {
        return requestCounts.computeIfAbsent(clientIp, k -> new RequestCounter());
    }

    // Classe interna para armazenar o contador de
    // requisições
    public static class RequestCounter {
        private int count;
        private long firstRequestTime;

        public RequestCounter() {
            this.firstRequestTime =
                    System.currentTimeMillis();
            this.count = 1;
        }

        public int getCount() {
            return count;
        }

        public long getFirstRequestTime() {
            return firstRequestTime;
        }

        public void increment() {
            count++;
        }

        public void reset(long newTime) {
            count = 1;
            firstRequestTime = newTime;
        }
    }
}