package com.streamprocessor.iot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class APIRequestInterval {
    private String start;
    private String end;
}
