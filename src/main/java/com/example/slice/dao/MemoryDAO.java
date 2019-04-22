package com.example.slice.dao;

import com.example.slice.entity.Memory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/22 0:18
 */
@Mapper
@Component
public interface MemoryDAO {
    List<Memory> findMemoryByUser(int userid);

    void addMemory(Memory memory);

    void deleteMemory(int id);

    void updateMemory(Memory memory);


}
