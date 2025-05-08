package umc_8th.spring.apiPayload.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import umc_8th.spring.apiPayload.ApiResponse;
import umc_8th.spring.apiPayload.code.ErrorReasonDTO;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;

import java.net.BindException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    private HttpStatus resolveHttpStatus(Exception e) {
        // 1. Spring에서 공식적으로 지정한 예외 처리 우선
        if (e instanceof ResponseStatusException rse) {
            return (HttpStatus) rse.getStatusCode();
        }

        ResponseStatus ann = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (ann != null) return ann.code();

        // 2. Spring MVC에서 흔히 400으로 처리하는 예외
        if (e instanceof BindException ||
                e instanceof MethodArgumentNotValidException ||
                e instanceof ConstraintViolationException ||
                e instanceof IllegalArgumentException ||
                e instanceof MissingServletRequestParameterException ||
                e instanceof MissingPathVariableException ||
                e instanceof HttpMessageNotReadableException ||
                e instanceof TypeMismatchException) {
            return HttpStatus.BAD_REQUEST;
        }

        // 3. 그 외는 500
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY,request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(e, HttpHeaders.EMPTY,ErrorStatus.valueOf("_BAD_REQUEST"),request,errors);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        HttpStatus status = resolveHttpStatus(e);

        e.printStackTrace();

        // 환경이 local이 아닐 때만 전송
        if (!isLocalProfile() && status.is5xxServerError()) {
            sendToDiscord(e, request);
        }

        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(),request, e.getMessage());
    }

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);
//        e.printStackTrace();

        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorPoint);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

    private void sendToDiscord(Exception e, WebRequest request) {
        try {
            String errorMessage = """
            **500 에러 발생!**
            - 발생 시각: %s
            - 요청 URI: %s
            - 예외: %s
            """.formatted(LocalDateTime.now(), ((ServletWebRequest) request).getRequest().getRequestURI(), e.toString());

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> payload = Map.of("content", errorMessage);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload);
            restTemplate.postForEntity(discordWebhookUrl, entity, String.class);
        } catch (Exception ex) {
            log.error("디스코드 전송 실패: {}", ex.getMessage());
        }
    }


    private boolean isLocalProfile() {
        return "local".equalsIgnoreCase(activeProfile);
    }
}
