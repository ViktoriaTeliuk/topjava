package ru.javawebinar.topjava.util.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public ErrorType getType() {
        return type;
    }

    @JsonCreator
    public ErrorInfo(@JsonProperty("url") String url, @JsonProperty("type") String type, @JsonProperty("detail")String detail) {
        this.url = url;
        this.type = ErrorType.valueOf(type);
        this.detail = detail;
    }
}