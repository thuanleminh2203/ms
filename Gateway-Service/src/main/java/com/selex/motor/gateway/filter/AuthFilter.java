package com.selex.motor.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selex.motor.comon.constant.AppConstants;
import com.selex.motor.comon.exception.BadRequestException;
import com.selex.motor.comon.exception.ExceptionMessage;
import com.selex.motor.gateway.DTO.AuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Value("${excluded-urls}")
    List<String> excludedUrls;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper mapper;

    public AuthFilter(WebClient.Builder webClientBuilder, ObjectMapper mapper) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.mapper = mapper;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("**************************************************************************");
            log.info("URL is - " + request.getURI().getPath());
            String bearerToken = request.getHeaders().getFirst(AppConstants.AUTHORIZATION);
            log.info("Bearer Token: " + bearerToken);

            if (isSecured.test(request)) {
                return webClientBuilder.build().get()
                        .uri("lb://auth/api/login")
                        .header(AppConstants.AUTHORIZATION, bearerToken)
                        .retrieve().bodyToMono(AuthDTO.class)
                        .map(response -> {
                            exchange.getRequest().mutate().header("username", response.getUsername());
                            exchange.getRequest().mutate().header("fullName", response.getFullName());
                            exchange.getRequest().mutate().header("authorities", response.getAuthorities().stream().map(String::valueOf).reduce("", (a, b) -> a + "," + b));
                            return exchange;
                        }).flatMap(chain::filter).onErrorResume(error -> {
                            log.info("Error Happened");
                            HttpStatus errorCode = null;
                            String errorMsg = "";
                            if (error instanceof WebClientResponseException) {
                                WebClientResponseException webCLientException = (WebClientResponseException) error;
                                errorCode = webCLientException.getStatusCode();
                                errorMsg = webCLientException.getStatusText();

                            } else {
                                errorCode = HttpStatus.BAD_GATEWAY;
                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
                            return onError(exchange, String.valueOf(errorCode.value()), errorMsg, "JWT Authentication Failed", errorCode);
                        });
            }

            return chain.filter(exchange);
        };
    }

    public Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

    private Mono<Void> onError(ServerWebExchange exchange, String errCode, String err, String errDetails, HttpStatus httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
//        ObjectMapper objMapper = new ObjectMapper();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders().add("Content-Type", "application/json");
            ExceptionMessage data = new ExceptionMessage(new Date(), Integer.valueOf(errCode), err, errDetails);
            byte[] byteData = mapper.writeValueAsBytes(data);
            return response.writeWith(Mono.just(byteData).map(t -> dataBufferFactory.wrap(t)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return response.setComplete();
    }

    public static class Config {

    }
}