package com.finals.handy;

import com.finals.handy.bean.Task;
import com.finals.handy.config.ServerConfig;
import com.finals.handy.mapper.ImgMapper;
import com.finals.handy.mapper.TaskMapper;
import com.finals.handy.service.MessageService;
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

    @Autowired
    MessageService messageService;

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ImgMapper imgMapper;

    @Autowired
    private ServerConfig serverConfig;


    @Test

    public void contextLoads() {
      /*  for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setName("名称");
            task.setContent("内容");
            task.setPublishId(1);
            System.out.println(taskMapper.addTask(task));
        }*/

//        System.out.println(taskMapper.findCounts());
//        System.out.println(taskMapper.deleteTask(1));
//        System.out.println(taskMapper.findTaskById(32));
//        System.out.println(taskMapper.acceptTask(27,3));
//        System.out.println(taskMapper.findTasks(5));
       /* Task task = new Task();
        task.setId(27);
        task.setContent("我就改一改");
        task.setName("Name");
        System.out.println(taskMapper.updateTask(task));*/
//        System.out.println(taskMapper.reportTask(27));
//        System.out.println(taskMapper.deleteTask(27));
        System.out.println(taskMapper.finishTask(28));

    }

    @Test
    public void testImg() {
//        imgMapper.addImgPath(1, "123.jpg");
//        System.out.println(serverConfig.getUrl());
        taskMapper.reportTask(41, 4);

    }


}

