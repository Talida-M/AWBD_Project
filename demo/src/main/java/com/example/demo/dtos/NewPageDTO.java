package com.example.demo.dtos;

import java.time.LocalDateTime;

public class NewPageDTO {
    private LocalDateTime pageDate;
    private  String pacientEmail;
    private String content;
    private boolean isPublic ;

    public LocalDateTime getPageDate() {
        return pageDate;
    }

    public void setPageDate(LocalDateTime pageDate) {
        this.pageDate = pageDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getPacientEmail() {
        return pacientEmail;
    }

    public void setPacientEmail(String pacientEmail) {
        this.pacientEmail = pacientEmail;
    }

    public NewPageDTO(LocalDateTime pageDate, String pacientEmail, String content, boolean isPublic) {
        this.pageDate = pageDate;
        this.pacientEmail = pacientEmail;
        this.content = content;
        this.isPublic = isPublic;
    }
}
