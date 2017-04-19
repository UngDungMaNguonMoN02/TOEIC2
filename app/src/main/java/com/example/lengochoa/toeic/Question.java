package com.example.lengochoa.toeic;

import java.io.Serializable;

/**
 * Created by LeNgocHoa on 2017-03-20.
 */

public class Question implements Serializable {
    private String content;
    private String[] ans ;
    private String key;

    public Question(){
        this.content="";
        this.key="";
        this.ans = new String[4];
    }

    public void setQuestion(String content, String[] ans){
        this.content = content;
        this.ans[0] = ans[0];
        this.ans[1] = ans[1];
        this.ans[2] = ans[2];
        this.ans[3] = ans[3];
    }

    public String getContent(){return this.content;}
    public String[] getAnswer() {return this.ans;}
}
