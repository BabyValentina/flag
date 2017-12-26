package com.longke.flag.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：$ longke on 2017/12/26 13:56
 * 邮箱：373497847@qq.com
 */

public class User implements Serializable{

    /**
     * Id : 5
     * UserName : 丸子君君
     * Sex : false
     * PhotoUrl : /UpFiles/files/Common/20171226141601554.jpg
     * Comments : 有
     * AttentionCount : 0
     * BeAttentionCount : 0
     * CollectCount : 0
     * FlagList : []
     * CircleList : []
     */

    private int Id;
    private String UserName;
    private boolean Sex;
    private String PhotoUrl;
    private String Comments;
    private int AttentionCount;
    private int BeAttentionCount;
    private int CollectCount;
    private List<?> FlagList;
    private List<?> CircleList;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean Sex) {
        this.Sex = Sex;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String PhotoUrl) {
        this.PhotoUrl = PhotoUrl;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public int getAttentionCount() {
        return AttentionCount;
    }

    public void setAttentionCount(int AttentionCount) {
        this.AttentionCount = AttentionCount;
    }

    public int getBeAttentionCount() {
        return BeAttentionCount;
    }

    public void setBeAttentionCount(int BeAttentionCount) {
        this.BeAttentionCount = BeAttentionCount;
    }

    public int getCollectCount() {
        return CollectCount;
    }

    public void setCollectCount(int CollectCount) {
        this.CollectCount = CollectCount;
    }

    public List<?> getFlagList() {
        return FlagList;
    }

    public void setFlagList(List<?> FlagList) {
        this.FlagList = FlagList;
    }

    public List<?> getCircleList() {
        return CircleList;
    }

    public void setCircleList(List<?> CircleList) {
        this.CircleList = CircleList;
    }
}
