package com.anybodyherechat.utils;

import com.anybodyherechat.model.GenericResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ResponseUtils}
 * Purpose: Response util class with static methods
 */
@Slf4j
public class ResponseUtils {

    private ResponseUtils() {
    }

    public static <T> GenericResponse buildGenericResponse(String summary, T data) {
        log.info("Entry method buildGenericResponse(String summary, Object data)");
        GenericResponse response = new GenericResponse();
        response.setSummary(summary);
        response.setData(data);
        log.debug("response: {}", response);
        log.info("Entry method buildGenericResponse(String summary, Object data)");
        return response;
    }
}
