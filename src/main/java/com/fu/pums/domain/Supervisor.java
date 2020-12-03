package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fu.pums.domain.enumeration.Gender;

/**
 * A Supervisor.
 */
@Entity
@Table(name = "supervisor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supervisor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "supervisor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "supervisors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Faculty> faculties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Supervisor fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public Supervisor role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public Supervisor gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public Supervisor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Supervisor phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Supervisor projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Supervisor addProjects(Project project) {
        this.projects.add(project);
        project.setSupervisor(this);
        return this;
    }

    public Supervisor removeProjects(Project project) {
        this.projects.remove(project);
        project.setSupervisor(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public Supervisor faculties(Set<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }

    public Supervisor addFaculties(Faculty faculty) {
        this.faculties.add(faculty);
        faculty.getSupervisors().add(this);
        return this;
    }

    public Supervisor removeFaculties(Faculty faculty) {
        this.faculties.remove(faculty);
        faculty.getSupervisors().remove(this);
        return this;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supervisor)) {
            return false;
        }
        return id != null && id.equals(((Supervisor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supervisor{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", role='" + getRole() + "'" +
            ", gender='" + getGender() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
