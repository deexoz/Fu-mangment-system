package com.fu.pums.service.dto;

import com.fu.pums.domain.Faculty;
import com.fu.pums.domain.Project;
import com.fu.pums.domain.Student;
import com.fu.pums.domain.Supervisor;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class StudentProjectDTO {
  @NotNull
  private Project project;


    @NotNull
  private Set<Student> students;



    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

  public Set<Student> getStudents() {
    return students;
  }

  public void setStudents(Set<Student> students) {
    this.students = students;
  }
}
