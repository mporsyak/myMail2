package com.exampel.myMail.model;

/**
 * Created by Михаил on 06.09.2019.
 */
public class SendMessage {
    private String myContent;
    private String myRecipLogin;

    public String getMyContent() {
        return myContent;
    }

    public void setMyContent(String myContent) {
        this.myContent = myContent;
    }

    public String getMyRecipLogin() {
        return myRecipLogin;
    }

    public void setMyRecipLogin(String myRecipLogin) {
        this.myRecipLogin = myRecipLogin;
    }
}
