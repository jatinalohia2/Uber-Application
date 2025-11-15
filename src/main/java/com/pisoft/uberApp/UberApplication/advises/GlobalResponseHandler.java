package com.pisoft.uberApp.UberApplication.advises;

import com.pisoft.uberApp.UberApplication.annotations.Skip;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        if (returnType.getDeclaringClass().isAnnotationPresent(Skip.class)){
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 🚫 Skip Swagger and OpenAPI endpoints
        String path = request.getURI().getPath();

        System.out.println("path : "+path);

        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/actuator")) {
            System.out.println("entering in this ");
            return body; // don’t wrap
        }

        if (body instanceof ApiResponse<?>){
            return body;
        }
        return new ApiResponse<>(body);
    }
}
