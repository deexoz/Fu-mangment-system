package com.fu.pums.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.fu.pums.domain.File} entity. This class is used
 * in {@link com.fu.pums.web.rest.FileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter uploadDate;

    private StringFilter type;

    private LongFilter observationId;

    private LongFilter projectId;

    public FileCriteria() {
    }

    public FileCriteria(FileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.uploadDate = other.uploadDate == null ? null : other.uploadDate.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.observationId = other.observationId == null ? null : other.observationId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
    }

    @Override
    public FileCriteria copy() {
        return new FileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(InstantFilter uploadDate) {
        this.uploadDate = uploadDate;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LongFilter getObservationId() {
        return observationId;
    }

    public void setObservationId(LongFilter observationId) {
        this.observationId = observationId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileCriteria that = (FileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(uploadDate, that.uploadDate) &&
            Objects.equals(type, that.type) &&
            Objects.equals(observationId, that.observationId) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        uploadDate,
        type,
        observationId,
        projectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (uploadDate != null ? "uploadDate=" + uploadDate + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (observationId != null ? "observationId=" + observationId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
