package com.finals.handy;

import com.finals.handy.mapper.ExpressOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandyApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ExpressOrderMapper expressOrderMapper;



    @Test
    public void contextLoads() {


    }

}

