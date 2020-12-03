package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Observation.
 */
@Entity
@Table(name = "observation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "detail")
    private String detail;

    @Column(name = "creation_date")
    private Instant creationDate;

    @OneToOne
    @JoinColumn(unique = true)
    private File file;

    @ManyToOne
    @JsonIgnoreProperties(value = "observations", allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Observation title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public Observation detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Observation creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public File getFile() {
        return file;
    }

    public Observation file(File file) {
        this.file = file;
        return this;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Project getProject() {
        return project;
    }

    public Observation project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Observation)) {
            return false;
        }
        return id != null && id.equals(((Observation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Observation{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", detail='" + getDetail() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
