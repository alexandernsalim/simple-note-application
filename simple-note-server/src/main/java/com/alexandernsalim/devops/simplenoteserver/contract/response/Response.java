package com.alexandernsalim.devops.simplenoteserver.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;
}
