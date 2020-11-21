package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ResultData.
 */
@Entity
@Table(name = "result_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "ovservation")
    private String ovservation;

    @Column(name = "date")
    private Instant date;

    @OneToOne
    @JoinColumn(unique = true)
    private File file;

    @OneToOne(mappedBy = "resultData")
    @JsonIgnore
    private Phase phase;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOvservation() {
        return ovservation;
    }

    public ResultData ovservation(String ovservation) {
        this.ovservation = ovservation;
        return this;
    }

    public void setOvservation(String ovservation) {
        this.ovservation = ovservation;
    }

    public Instant getDate() {
        return date;
    }

    public ResultData date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public ResultData file(File file) {
        this.file = file;
        return this;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Phase getPhase() {
        return phase;
    }

    public ResultData phase(Phase phase) {
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
        if (!(o instanceof ResultData)) {
            return false;
        }
        return id != null && id.equals(((ResultData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultData{" +
            "id=" + getId() +
            ", ovservation='" + getOvservation() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
