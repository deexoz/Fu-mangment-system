package com.fu.pums.service.dto;

import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Student;
import com.fu.pums.domain.Supervisor;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class StudentProjectDTO {

    @NotNull
    private String projectName;

    @NotNull
    private Set<Student> students;

    @NotNull
    private Faculty faculty;

    private Supervisor supervisor;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
