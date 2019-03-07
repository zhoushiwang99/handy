package com.finals.handy.bean;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
public class Message {
    /*
    * 消息的id主键
    * */
    private int id;
    /*
    * 发送者的id
    * */
    private int fromId;
    /*
    * 接受者的id
    * */
    private int toId;
    /*
    * 确定是谁发给谁
    * */
    private String fromToId;
    /*
    * 发送的内容
    * */
    private String content;

    /*
    * 发送的状态 1已读   0未读
    * */
    private int state;

    /*
    * 发送的时间
    * */
    private String time;

    public Message() {
    }



    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", from_toId='" + fromToId + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", time='" + time + '\'' +
                '}';
    }



    public Message(Integer fromId, Integer toId, String Content, int State, String time) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = Content;
        this.state = State;
        this.time = time;
        if (fromId > toId) {
            Integer t = toId;
            toId = fromId;
            fromId = t;
        }
        this.fromToId = fromId.toString() + toId.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getFrom_toId() {
        return fromToId;
    }

    public void setFrom_toId(String from_toId) {
        this.fromToId = from_toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
