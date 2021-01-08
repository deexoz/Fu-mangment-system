package com.fu.pums.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Batch.
 */
@Entity
@Table(name = "batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private String year;

    @OneToMany(mappedBy = "batch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "batch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public Batch year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Batch projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Batch addProjects(Project project) {
        this.projects.add(project);
        project.setBatch(this);
        return this;
    }

    public Batch removeProjects(Project project) {
        this.projects.remove(project);
        project.setBatch(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Batch students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Batch addStudents(Student student) {
        this.students.add(student);
        student.setBatch(this);
        return this;
    }

    public Batch removeStudents(Student student) {
        this.students.remove(student);
        student.setBatch(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batch)) {
            return false;
        }
        return id != null && id.equals(((Batch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batch{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            "}";
    }
}
