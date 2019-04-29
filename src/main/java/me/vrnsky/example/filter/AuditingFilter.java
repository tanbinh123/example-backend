package me.vrnsky.example.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuditingFilter extends GenericFilterBean {

    Logger logger = LoggerFactory.getLogger(AuditingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = LocalDateTime.now().getNano();
        chain.doFilter(request, response);
        long elapsed = LocalDateTime.now().getNano() - start;
        HttpServletRequest req = (HttpServletRequest)request;
        logger.debug("Request[uri=" + req.getRequestURI() + ", method=" + req.getMethod() + "] completed in " + elapsed + " nanos");
    }
}
