package com.timcooki.jnuwiki.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static String format(Instant instant) {
        ZonedDateTime zdtVancouver = instant.atZone(ZoneId.of("Asia/Seoul"));
        return zdtVancouver.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String format(Long time) {
        Instant longToInstant = Instant.ofEpochMilli(time);
        ZonedDateTime zdtVancouver = longToInstant.atZone(ZoneId.of("Asia/Seoul"));
        return zdtVancouver.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
