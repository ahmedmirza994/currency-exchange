package com.ahmedharis.currencyexchange.dto;

public class ApiResponse<T> {
    private String errorMessage;
    private Boolean success;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String errorMessage, Boolean success, T data) {
        this.errorMessage = errorMessage;
        this.success = success;
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, true, data);
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(errorMessage, false, null);
    }
}
