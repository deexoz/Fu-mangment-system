package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Phase.
 */
@Entity
@Table(name = "phase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "discution_time")
    private String discutionTime;

    @OneToOne
    @JoinColumn(unique = true)
    private ResultData resultData;

    @OneToMany(mappedBy = "phase")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PhaseType> phaseTypes = new HashSet<>();

    @OneToMany(mappedBy = "phase")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Announcement> announcements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "phases", allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Phase startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Phase endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDiscutionTime() {
        return discutionTime;
    }

    public Phase discutionTime(String discutionTime) {
        this.discutionTime = discutionTime;
        return this;
    }

    public void setDiscutionTime(String discutionTime) {
        this.discutionTime = discutionTime;
    }

    public ResultData getResultData() {
        return resultData;
    }

    public Phase resultData(ResultData resultData) {
        this.resultData = resultData;
        return this;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }

    public Set<PhaseType> getPhaseTypes() {
        return phaseTypes;
    }

    public Phase phaseTypes(Set<PhaseType> phaseTypes) {
        this.phaseTypes = phaseTypes;
        return this;
    }

    public Phase addPhaseType(PhaseType phaseType) {
        this.phaseTypes.add(phaseType);
        phaseType.setPhase(this);
        return this;
    }

    public Phase removePhaseType(PhaseType phaseType) {
        this.phaseTypes.remove(phaseType);
        phaseType.setPhase(null);
        return this;
    }

    public void setPhaseTypes(Set<PhaseType> phaseTypes) {
        this.phaseTypes = phaseTypes;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public Phase announcements(Set<Announcement> announcements) {
        this.announcements = announcements;
        return this;
    }

    public Phase addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setPhase(this);
        return this;
    }

    public Phase removeAnnouncement(Announcement announcement) {
        this.announcements.remove(announcement);
        announcement.setPhase(null);
        return this;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Project getProject() {
        return project;
    }

    public Phase project(Project project) {
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
        if (!(o instanceof Phase)) {
            return false;
        }
        return id != null && id.equals(((Phase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phase{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", discutionTime='" + getDiscutionTime() + "'" +
            "}";
    }
}
