//package com.ylqm.codeshelf.gateway.exception;
//
//import com.ylqm.codeshelf.gateway.enums.exception.SysMsgEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import javax.xml.bind.annotation.XmlRootElement;
//import java.io.Serializable;
//
///*@Slf4j
//@RestControllerAdvice*/
//public class GlobalExceptionHandler  {
//
//    @ExceptionHandler(value = {CustomException.class})
//    public Mono<ResponseEntity<?>> handleCustomException(ServerWebExchange exchange) {
//        return createErrorResponse(SysMsgEnum.SYS_ERROR.getCode(), SysMsgEnum.SYS_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR, exchange);
//
//    }
//
//    @ExceptionHandler(value = {NullPointerException.class})
//    public Mono<ResponseEntity<?>> handleNullPointerException(ServerWebExchange exchange) {
//        return createErrorResponse(SysMsgEnum.SYS_IO_ERROR.getCode(), SysMsgEnum.SYS_IO_ERROR.getMsg(), HttpStatus.BAD_REQUEST, exchange);
//    }
//
//    @ExceptionHandler(value = {Exception.class})
//    public Mono<ResponseEntity<?>> handleGeneralException(ServerWebExchange exchange) {
//        return createErrorResponse(SysMsgEnum.SYS_ERROR.getCode(), SysMsgEnum.SYS_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR, exchange);
//    }
//
//    private Mono<ResponseEntity<?>> createErrorResponse(String code, String errorMessage, HttpStatus status, ServerWebExchange exchange) {
//        String acceptHeader = exchange.getRequest().getHeaders().getFirst("Accept");
//
//        if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
//            // JSON响应
//            return Mono.just(ResponseEntity.status(status)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(new ErrorResponse(code, errorMessage)));
//        } else {
//            // 默认为XML响应
//            return Mono.just(ResponseEntity.status(status)
//                    .contentType(MediaType.APPLICATION_XML)
//                    .body(new ErrorResponseXml(code, errorMessage)));
//        }
//    }
//
//    // JSON格式的错误响应实体
//    public static class ErrorResponse {
//        private String code;
//        private String errorMessage;
//
//        public ErrorResponse(String code, String errorMessage) {
//            this.code = code;
//            this.errorMessage = errorMessage;
//        }
//
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getErrorMessage() {
//            return errorMessage;
//        }
//
//        public void setErrorMessage(String errorMessage) {
//            this.errorMessage = errorMessage;
//        }
//
//    }
//
//    // XML格式的错误响应实体
//    @XmlRootElement(name = "error")
//    public static class ErrorResponseXml extends ErrorResponse implements Serializable {
//        public ErrorResponseXml(String code, String errorMessage) {
//            super(code, errorMessage);
//        }
//
//        // 必须实现Serializable接口以支持XML序列化
//        private static final long serialVersionUID = 1L;
//    }
//
//}
