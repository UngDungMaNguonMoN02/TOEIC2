package com.example.lengochoa.toeic;


import java.io.Serializable;

/**
 * Created by LeNgocHoa on 2017-03-20.
 */

public class Question implements Serializable{
    private String content;
    private String[] ans ;
    private String key;

    public Question(){
        this.content="";
        this.key="";
    }

    public void setQuestion(String content, String[] ans, String key, int n){
        this.content = content;
        this.ans = new String[n];
        for(int i = 0; i < n; i++){
            this.ans[i] = ans[i];
        }
        this.key = key;
    }

    public String getContent(){return this.content;}
    public String getKey(){return this.key;}
    public String[] getAnswer() {return this.ans;}
}
