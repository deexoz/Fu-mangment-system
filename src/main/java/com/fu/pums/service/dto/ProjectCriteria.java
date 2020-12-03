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

/**
 * Criteria class for the {@link com.fu.pums.domain.Project} entity. This class is used
 * in {@link com.fu.pums.web.rest.ProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter studentsId;

    private LongFilter filesId;

    private LongFilter observationsId;

    private LongFilter facultyId;

    private LongFilter supervisorId;

    public ProjectCriteria() {
    }

    public ProjectCriteria(ProjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.studentsId = other.studentsId == null ? null : other.studentsId.copy();
        this.filesId = other.filesId == null ? null : other.filesId.copy();
        this.observationsId = other.observationsId == null ? null : other.observationsId.copy();
        this.facultyId = other.facultyId == null ? null : other.facultyId.copy();
        this.supervisorId = other.supervisorId == null ? null : other.supervisorId.copy();
    }

    @Override
    public ProjectCriteria copy() {
        return new ProjectCriteria(this);
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

    public LongFilter getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(LongFilter studentsId) {
        this.studentsId = studentsId;
    }

    public LongFilter getFilesId() {
        return filesId;
    }

    public void setFilesId(LongFilter filesId) {
        this.filesId = filesId;
    }

    public LongFilter getObservationsId() {
        return observationsId;
    }

    public void setObservationsId(LongFilter observationsId) {
        this.observationsId = observationsId;
    }

    public LongFilter getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(LongFilter facultyId) {
        this.facultyId = facultyId;
    }

    public LongFilter getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(LongFilter supervisorId) {
        this.supervisorId = supervisorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(studentsId, that.studentsId) &&
            Objects.equals(filesId, that.filesId) &&
            Objects.equals(observationsId, that.observationsId) &&
            Objects.equals(facultyId, that.facultyId) &&
            Objects.equals(supervisorId, that.supervisorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        studentsId,
        filesId,
        observationsId,
        facultyId,
        supervisorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (studentsId != null ? "studentsId=" + studentsId + ", " : "") +
                (filesId != null ? "filesId=" + filesId + ", " : "") +
                (observationsId != null ? "observationsId=" + observationsId + ", " : "") +
                (facultyId != null ? "facultyId=" + facultyId + ", " : "") +
                (supervisorId != null ? "supervisorId=" + supervisorId + ", " : "") +
            "}";
    }

}
