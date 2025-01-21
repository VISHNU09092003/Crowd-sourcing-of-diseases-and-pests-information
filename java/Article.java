package com.example.croudsourcing;

public class Article {

    String email, title, intro, body, name;
    byte[] img;
    int sno;
    boolean isApproved;

    public Article(int sno, String name, String email, String title, String intro, String body, byte[] img, boolean isApproved) {
        this.email = email;
        this.title = title;
        this.intro = intro;
        this.body = body;
        this.img = img;
        this.isApproved = isApproved;
        this.name = name;
        this.sno = sno;
    }

    public int getSno(){
        return sno;
    }

    public void setSno(int sno){
        this.sno = sno;
    }

    public String getName(){
            return name;
    }
    public void setName(String name){
            this.name = name;
    }
    public Article() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
