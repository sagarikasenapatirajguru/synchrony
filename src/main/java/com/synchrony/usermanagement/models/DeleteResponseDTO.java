package com.synchrony.usermanagement.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteResponseDTO {

    public String error;
    public String request;
    public String method;
}
