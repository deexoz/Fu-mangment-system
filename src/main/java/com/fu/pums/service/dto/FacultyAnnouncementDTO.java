package com.fu.pums.service.dto;

import com.fu.pums.domain.Announcement;
import com.fu.pums.domain.Faculty;

import javax.validation.constraints.NotNull;


public class FacultyAnnouncementDTO {
    @NotNull
    private Faculty faculty;

    @NotNull
    private Announcement announcement;

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
