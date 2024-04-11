package com.example.demo.dtos;


import java.time.LocalDateTime;

public class JournalPagesDTO {
    private Integer pageId;
    private LocalDateTime pageDate;
    private  String name;
    private String content;
    private boolean isPublic ;

    public JournalPagesDTO() {
    }

    public JournalPagesDTO( Integer pageId, LocalDateTime pageDate, String name, String content, boolean isPublic) {
        this.pageId = pageId;
        this.pageDate = pageDate;
        this.name = name;
        this.content = content;
        this.isPublic = isPublic;
    }


    public Integer getPageId() {
        return pageId;
    }



    public LocalDateTime getPageDate() {
        return pageDate;
    }

    public void setPageDate(LocalDateTime pageDate) {
        this.pageDate = pageDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
