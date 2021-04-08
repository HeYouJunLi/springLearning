package com.yc.dao;

import org.springframework.stereotype.Component;


public interface StudentDao {
    public int add(String name);

    public void update(String name);
}
