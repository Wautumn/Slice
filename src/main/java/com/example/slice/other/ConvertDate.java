package com.example.slice.other;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: WHQ
 * @Date: 2019/4/22 0:32
 */
@Service
public class ConvertDate {

    public LocalDate StringToLocalDate(String date) {
        LocalDate beginDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return beginDateTime;
    }

    public String LocalDateToString(LocalDate localDate) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localTime = df.format(localDate);
        return localTime;
    }
}
