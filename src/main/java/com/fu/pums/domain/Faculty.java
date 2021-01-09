package com.fu.pums.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Faculty.
 */
@Entity
@Table(name = "faculty")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "faculty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "faculty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "faculty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Announcement> announcements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "faculty_supervisors",
               joinColumns = @JoinColumn(name = "faculty_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "supervisors_id", referencedColumnName = "id"))
    private Set<Supervisor> supervisors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Faculty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Faculty code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Faculty students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Faculty addStudents(Student student) {
        this.students.add(student);
        student.setFaculty(this);
        return this;
    }

    public Faculty removeStudents(Student student) {
        this.students.remove(student);
        student.setFaculty(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Faculty projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Faculty addProjects(Project project) {
        this.projects.add(project);
        project.setFaculty(this);
        return this;
    }

    public Faculty removeProjects(Project project) {
        this.projects.remove(project);
        project.setFaculty(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public Faculty announcements(Set<Announcement> announcements) {
        this.announcements = announcements;
        return this;
    }

    public Faculty addAnnouncements(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setFaculty(this);
        return this;
    }

    public Faculty removeAnnouncements(Announcement announcement) {
        this.announcements.remove(announcement);
        announcement.setFaculty(null);
        return this;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Set<Supervisor> getSupervisors() {
        return supervisors;
    }

    public Faculty supervisors(Set<Supervisor> supervisors) {
        this.supervisors = supervisors;
        return this;
    }

    public Faculty addSupervisors(Supervisor supervisor) {
        this.supervisors.add(supervisor);
        supervisor.getFaculties().add(this);
        return this;
    }

    public Faculty removeSupervisors(Supervisor supervisor) {
        this.supervisors.remove(supervisor);
        supervisor.getFaculties().remove(this);
        return this;
    }

    public void setSupervisors(Set<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Faculty)) {
            return false;
        }
        return id != null && id.equals(((Faculty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Faculty{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
