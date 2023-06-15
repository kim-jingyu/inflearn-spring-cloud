package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {
    // 사용자 요청이 들어올 때마다 먼저 실행되는 메서드
    @Override
    public Object run() throws ZuulException {
        log.info("************* printing logs: ");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("************* " + request.getRequestURI());
        return null;
    }

    // 사전 필터인지, 사후 필터인지
    @Override
    public String filterType() {
        return "pre";
    }

    // 여러 개 필터가 있을때의 순서
    @Override
    public int filterOrder() {
        return 1;
    }

    // 지금 이 필터를 사용할지 말지
    @Override
    public boolean shouldFilter() {
        return true;
    }
}
