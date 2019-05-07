package com.example.slice.service.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: WHQ
 * @Date: 2019/5/6 23:32
 */
@Service
public class DateConvert {

    public LocalDate StringToLD(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate ld = LocalDate.parse(time, df);
        return ld;
    }
    public LocalDateTime StringToLDT(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(time, df);
        return ldt;
    }
}
