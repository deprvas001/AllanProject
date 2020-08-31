package com.development.allanproject.model.apiResponse;

public class ApiResponse {
    private String message;
    private int status;
    private SignUpResponse signUpResponse;


    public ApiResponse(int status, SignUpResponse signUpResponse){
        this.status = status;
        this.signUpResponse = signUpResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SignUpResponse getSignUpResponse() {
        return signUpResponse;
    }

    public void setSignUpResponse(SignUpResponse signUpResponse) {
        this.signUpResponse = signUpResponse;
    }
}
