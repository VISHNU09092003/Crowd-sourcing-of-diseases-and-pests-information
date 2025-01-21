package com.example.croudsourcing;

public class Report {

    String title, report, name, solution, email;
    int sno;
    byte[] img;

    public Report(int sno, String name, String email,  String title, String report, String solution, byte[] img) {
        this.title = title;
        this.report = report;
        this.name = name;
        this.solution = solution;
        this.email = email;
        this.sno = sno;
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Report(){

    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReport() {
        return report;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
