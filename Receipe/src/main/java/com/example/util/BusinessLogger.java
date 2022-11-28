package com.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class BusinessLogger {
    public static void printInfoLogs(final String message) {
            log.info(message);

    }

    public static void printErrorLogs(final String message) {
        log.error(message);

    }

}
