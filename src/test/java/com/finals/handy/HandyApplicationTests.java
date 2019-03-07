package com.finals.handy;

import com.finals.handy.util.GenerateNumUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandyApplicationTests {

    @Autowired
    GenerateNumUtil generateNumUtil;

    @Test
    public void contextLoads() {
    }

}

