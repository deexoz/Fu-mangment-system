package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "jhi_index")
    private String index;

    @Column(name = "full_name_arabic")
    private String fullNameArabic;

    @Column(name = "phone")
    private String phone;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Faculty faculty;

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Batch batch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public Student index(String index) {
        this.index = index;
        return this;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFullNameArabic() {
        return fullNameArabic;
    }

    public Student fullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
        return this;
    }

    public void setFullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
    }

    public String getPhone() {
        return phone;
    }

    public Student phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Student user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Student faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Project getProject() {
        return project;
    }

    public Student project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Batch getBatch() {
        return batch;
    }

    public Student batch(Batch batch) {
        this.batch = batch;
        return this;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", index='" + getIndex() + "'" +
            ", fullNameArabic='" + getFullNameArabic() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
