package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.Comment;
import com.finals.handy.bean.Report;
import com.finals.handy.bean.Task;
import com.finals.handy.config.ServerConfig;
import com.finals.handy.mapper.CommentMapper;
import com.finals.handy.mapper.ImgMapper;
import com.finals.handy.mapper.ReportMapper;
import com.finals.handy.mapper.TaskMapper;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    ImgMapper imgMapper;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    public Map<String, Object> addTask(String AccessToken, String name, String content, MultipartFile[] files) {
        Map<String, Object> map = null;
        Task task = new Task();

        Integer userId = Integer.parseInt(AccessToken);// getId(AccessToken);


        task.setPublishId(userId);
        task.setName(name);
        task.setContent(content);
        task.setAcceptId(0);
        String time = getTime();
        task.setTime(time);
        task.setIsFinish(0);
        task.setIsReport(0);
        if (!taskMapper.addTask(task)) {
            map = new HashMap<>();
            map.put("code", -1);
            throw new RuntimeException();
        }
        map = dealImg(files, task);

        return map;
    }
    @Transactional
    public Map<String, Object> dealImg(MultipartFile[] files, Task task) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList();
        Integer taskId = task.getId();

        String path = null;
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
            path = path + "/resources/img/";

            System.out.println(path);

        } catch (FileNotFoundException e) {
            map.put("code", -1);
            return map;
        }
        for (MultipartFile multipartFile : files) {
            if (!multipartFile.isEmpty()) {
                try {
                    byte[] bytes = multipartFile.getBytes();
                    String fileName = multipartFile.getOriginalFilename();
//                使用UUID给图片重命名
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//                获取文件的扩展名
                    String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());

                    String newName = uuid + ext;
                    System.out.println("fileName:" + fileName);
                    System.out.println(newName);
                    String imgPath = path + newName;
                    File file1 = new File(imgPath);
                    list.add(imgPath);
                    imgMapper.addImgPath(taskId, getURL() + "/img/" + newName);
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file1));
                    outputStream.write(bytes);
                    outputStream.close();

                } catch (IOException e) {
                    map.put("code", -1);
                    return map;
                }


            }
        }
        task.setImgsPath(list);
        map.put("code", 0);
        return map;
    }

    @Transactional
    public String getURL() {
        return serverConfig.getUrl();
    }

    public Map<String, Object> findTaskCounts() {
        Integer num = taskMapper.findCounts();
        Map<String, Object> map = new HashMap<>();

        map.put("num", num);
        map.put("code", 0);
        return map;
    }

    @Transactional
    public Map<String, Object> getTasks(Integer n) {
        Map<String, Object> map = new HashMap<>();
        List<Task> tasks = taskMapper.findTasks(n);
        for (Task task : tasks) {
            List<String> imgPath = imgMapper.getImgPath(task.getId());
            task.setImgsPath(imgPath);
        }
        map.put("tasks", tasks);
        map.put("code", 1);
        return map;
    }

    @Transactional
    public Map<String, Object> updateTask(Integer id, String name, String content) {
        Task task = new Task();
        Map<String, Object> map = new HashMap<>();
        task.setId(id);
        task.setName(name);
        task.setContent(content);
        taskMapper.updateTask(task);

        map.put("code", 0);
        return map;

    }

    @Transactional
    public Map<String, Object> reportTask(String accessToken, String reason, Integer id) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        Report report = new Report();
        report.setReason(reason);
        report.setReportId(userId);
        report.setTime(getTime());
        reportMapper.addReport(report);
        System.out.println(report.getId());  //4

        taskMapper.reportTask(id,report.getId() );
        map.put("code", 0);
        return map;

    }


    private Integer getId(String AccessToken) {
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
        String id = claimMap.get("userId").asString();
        Integer userId = Integer.parseInt(id);
        return userId;
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    public Map<String, Object> acceptTask(String accessToken, Integer id) {
        Map<String, Object> map = new HashMap<>();

        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        if (taskMapper.acceptTask(id, userId)) {

            map.put("code", 0);

        } else {
            map.put("code", 1);
        }
        return map;
    }

    @Transactional
    public Map<String, Object> cancelTask(String accessToken, Integer id) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        Task task = taskMapper.findTaskById(id);
        if (task.getAcceptId() == userId) {
            taskMapper.cancelTask(id);
        } else {
            map.put("code", 1);
            return map;
        }

        map.put("code", 0);
        return map;
    }

    @Transactional
    public Map<String, Object> deleteTask(String accessToken, Integer id) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        Task task = taskMapper.findTaskById(id);
        if (userId == task.getPublishId()) {
            taskMapper.deleteTask(id);
        } else {
            map.put("code", 5);
            return map;
        }
        map.put("code", 0);
        return map;
    }

    @Transactional
    public Map<String, Object> cancelReportTask(Integer id, Integer taskId) {
        Map<String, Object> map = new HashMap<>();
//        取消举报
        if (!taskMapper.cancelReportTask(taskId)) {
            map.put("code", 1);
        }
//        删除举报
        else if (!reportMapper.deleteReport(id)) {
            map.put("code", 1);

        } else {
            map.put("code", 0);
        }
        return map;
    }


    public Map<String, Object> commentTask(String accessToken, String content, Integer taskId) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);

        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setTime(getTime());
        comment.setContent(content);
        comment.setIsReport(0);
        comment.setUserId(userId);
        if (commentMapper.addComment(comment)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }

    @Transactional
    public Map<String, Object> reportComment(String accessToken, Integer id, String reason) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        Report report = new Report();
        report.setTime(getTime());
        report.setReportId(userId);
        report.setReason(reason);
        reportMapper.addReport(report);
        if (commentMapper.reportComment(report.getId(), id)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }

        return map;
    }


    public Map<String, Object> deleteComment(Integer id) {
        Map<String, Object> map = new HashMap<>();

        if (commentMapper.deleteComment(id)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }

        return map;
    }

    public Map<String, Object> getComments(Integer n,Integer taskId) {
        Map<String, Object> map = new HashMap<>();
        List<Comment> lists = commentMapper.getComments(n, taskId);
        map.put("comments", lists);
        map.put("code", 0);
        return map;
    }

    @Transactional
    public Map<String, Object> finishTask(String accessToken, Integer id) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(accessToken);//getId(accessToken);
        Task task = taskMapper.findTaskById(id);
        if (userId == task.getAcceptId()) {
            taskMapper.finishTask(id);
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }
}
