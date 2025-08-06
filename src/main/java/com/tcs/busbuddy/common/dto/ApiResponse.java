package com.tcs.busbuddy.common.dto;

import java.util.List;

public class ApiResponse<T> {
    private String responseCode;
    private ResponseBody<T> responseBody;

    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    public ResponseBody<T> getResponseBody() { return responseBody; }
    public void setResponseBody(ResponseBody<T> responseBody) { this.responseBody = responseBody; }

    public static class ResponseBody<T> {
        private T payload;
        private List<ErrorDetail> errors;

        public T getPayload() { return payload; }
        public void setPayload(T payload) { this.payload = payload; }
        public List<ErrorDetail> getErrors() { return errors; }
        public void setErrors(List<ErrorDetail> errors) { this.errors = errors; }
    }

    public static class ErrorDetail {
        private String code;
        private String description;

        public ErrorDetail(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() { return code; }
        public String getDescription() { return description; }
    }
}
