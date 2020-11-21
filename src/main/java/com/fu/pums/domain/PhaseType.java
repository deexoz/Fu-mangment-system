package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.fu.pums.domain.enumeration.AnnouncementType;

/**
 * A PhaseType.
 */
@Entity
@Table(name = "phase_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PhaseType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase_type")
    private AnnouncementType phaseType;

    @ManyToOne
    @JsonIgnoreProperties(value = "phaseTypes", allowSetters = true)
    private Phase phase;

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

    public PhaseType title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public PhaseType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnnouncementType getPhaseType() {
        return phaseType;
    }

    public PhaseType phaseType(AnnouncementType phaseType) {
        this.phaseType = phaseType;
        return this;
    }

    public void setPhaseType(AnnouncementType phaseType) {
        this.phaseType = phaseType;
    }

    public Phase getPhase() {
        return phase;
    }

    public PhaseType phase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhaseType)) {
            return false;
        }
        return id != null && id.equals(((PhaseType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhaseType{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", phaseType='" + getPhaseType() + "'" +
            "}";
    }
}
