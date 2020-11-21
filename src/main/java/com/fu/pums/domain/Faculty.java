package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "faculty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "faculties")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Supervisor> supservisors = new HashSet<>();

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

    public Set<Student> getStudents() {
        return students;
    }

    public Faculty students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Faculty addStudent(Student student) {
        this.students.add(student);
        student.setFaculty(this);
        return this;
    }

    public Faculty removeStudent(Student student) {
        this.students.remove(student);
        student.setFaculty(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Supervisor> getSupservisors() {
        return supservisors;
    }

    public Faculty supservisors(Set<Supervisor> supervisors) {
        this.supservisors = supervisors;
        return this;
    }

    public Faculty addSupservisors(Supervisor supervisor) {
        this.supservisors.add(supervisor);
        supervisor.getFaculties().add(this);
        return this;
    }

    public Faculty removeSupservisors(Supervisor supervisor) {
        this.supservisors.remove(supervisor);
        supervisor.getFaculties().remove(this);
        return this;
    }

    public void setSupservisors(Set<Supervisor> supervisors) {
        this.supservisors = supervisors;
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
            "}";
    }
}
