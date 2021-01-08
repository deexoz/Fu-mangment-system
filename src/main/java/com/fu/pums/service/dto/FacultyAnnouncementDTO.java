package com.fu.pums.service.dto;

import com.fu.pums.domain.Announcement;
import com.fu.pums.domain.Batch;
import com.fu.pums.domain.Faculty;

import javax.validation.constraints.NotNull;


public class FacultyAnnouncementDTO {
    @NotNull
    private Faculty faculty;

    @NotNull
    private Announcement announcement;

    @NotNull
    private Batch batch;

    public Batch getBatch() {
        return batch;
    }
    public void setBatch(Batch batch) {
        this.batch = batch;
    }
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
