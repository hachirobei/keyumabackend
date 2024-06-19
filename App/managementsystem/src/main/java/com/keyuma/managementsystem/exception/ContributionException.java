package com.keyuma.managementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContributionException extends RuntimeException {
    public ContributionException(String message){super(message);}
}
