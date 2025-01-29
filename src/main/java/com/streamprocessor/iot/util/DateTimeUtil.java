package com.streamprocessor.iot.util;

import com.streamprocessor.iot.exceptions.InconsistentPassedInterval;
import com.streamprocessor.iot.exceptions.InvalidPassedIntervalParameters;
import com.streamprocessor.iot.model.APIRequestInterval;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static LocalDateTime getLocalDateTimeFromString(String timestamp){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return LocalDateTime.parse(timestamp, format);
    }

    public static APIRequestInterval getRequestInterval(String start, String end) throws InconsistentPassedInterval {
        APIRequestInterval requestInterval;
        if(start != null && end != null){
            LocalDateTime startDate = DateTimeUtil.getLocalDateTimeFromString(start);
            LocalDateTime endDate = DateTimeUtil.getLocalDateTimeFromString(end);

            if(!startDate.isBefore(endDate)){
                throw new InconsistentPassedInterval();
            }

            requestInterval = new APIRequestInterval(start, end);
        } else {
            requestInterval = new APIRequestInterval(LocalDateTime.now().plusDays(-7L).toString(), LocalDateTime.now().toString());
        }

        return requestInterval;
    }
}
