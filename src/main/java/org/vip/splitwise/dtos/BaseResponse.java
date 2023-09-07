package org.vip.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponse {
    HttpStatus responseCode;

    @Value("")
    String responseMsg;
}
