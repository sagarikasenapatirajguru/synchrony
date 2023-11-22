package com.synchrony.usermanagement.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgurResponse {
    public ImgurResponseData data;
    public boolean success;
    public int status;
}
