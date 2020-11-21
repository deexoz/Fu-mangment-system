package com.fu.pums.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "supervisor_faculties",
               joinColumns = @JoinColumn(name = "supervisor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "faculties_id", referencedColumnName = "id"))
    private Set<Faculty> faculties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public Supervisor faculties(Set<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }

    public Supervisor addFaculties(Faculty faculty) {
        this.faculties.add(faculty);
        faculty.getSupservisors().add(this);
        return this;
    }

    public Supervisor removeFaculties(Faculty faculty) {
        this.faculties.remove(faculty);
        faculty.getSupservisors().remove(this);
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
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
