package br.org.enascimento.assembleiacooperados.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated(since="5.3.2")
public class HttpLoggerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @deprecated
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (logger.isInfoEnabled()) {
            logger.info("REQUEST::{}:{}",
                    request.getMethod(),
                    request.getRequestURI());
        }
        return true;
    }
}