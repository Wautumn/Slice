package com.example.slice.service;

import com.example.slice.dao.MemoryDAO;
import com.example.slice.entity.Memory;
import com.example.slice.other.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/22 0:27
 */
@Service
public class MemoryService {
    @Autowired
    MemoryDAO memoryDAO;

    private ConvertDate convertDate;

    /*
    获取一个人的今天之后的所有备忘录
     */
    public List<Memory> findMemoryByUser(int userid) {
        LocalDate today = LocalDate.now();
        today.minusDays(1);
        List<Memory> usefulmemories = new LinkedList<>();
        List<Memory> memories = memoryDAO.findMemoryByUser(userid);
        for (Memory memory : memories) {
            if (convertDate.StringToLocalDate(memory.getDate()).isAfter(today)) {
                usefulmemories.add(memory);
            }

        }
        return usefulmemories;
    }


    /*
    添加备忘录
     */
    public void addMemory(int userid, String date, String content) {
        Memory memory = new Memory();
        memory.setUserid(userid);
        memory.setDate(date);
        memory.setContent(content);

    }


}
