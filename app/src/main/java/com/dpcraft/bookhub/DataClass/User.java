package com.dpcraft.bookhub.DataClass;

/**
 * Created by DPC on 2017/2/23.
 */
public class User {
    private int userId;
    private String userName = "";
    private String passWord = "";
    private String imgPath;
    private String nickName = "";
    private String phoneNum = "";
    private String weChat;
    private boolean flat;

    public  int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public  String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getPassWord(){
        return passWord;
    }
    public void setPassWord(String passWord){
        this.passWord = passWord;
    }
    public String getImgPath(){
        return imgPath;
    }
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }
    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
    public String getWeChat(){
        return weChat;
    }
    public void setWeChat(String weChat){
        this.weChat = weChat;
    }
    public boolean getFlat(){
        return flat;
    }
    public void setFlat(boolean flat){
        this.flat = flat;
    }

}
