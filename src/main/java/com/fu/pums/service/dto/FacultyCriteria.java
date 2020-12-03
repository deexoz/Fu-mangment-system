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
 * Criteria class for the {@link com.fu.pums.domain.Faculty} entity. This class is used
 * in {@link com.fu.pums.web.rest.FacultyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /faculties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FacultyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private LongFilter studentsId;

    private LongFilter projectsId;

    private LongFilter announcmentsId;

    private LongFilter supervisorsId;

    public FacultyCriteria() {
    }

    public FacultyCriteria(FacultyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.studentsId = other.studentsId == null ? null : other.studentsId.copy();
        this.projectsId = other.projectsId == null ? null : other.projectsId.copy();
        this.announcmentsId = other.announcmentsId == null ? null : other.announcmentsId.copy();
        this.supervisorsId = other.supervisorsId == null ? null : other.supervisorsId.copy();
    }

    @Override
    public FacultyCriteria copy() {
        return new FacultyCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(LongFilter studentsId) {
        this.studentsId = studentsId;
    }

    public LongFilter getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(LongFilter projectsId) {
        this.projectsId = projectsId;
    }

    public LongFilter getAnnouncmentsId() {
        return announcmentsId;
    }

    public void setAnnouncmentsId(LongFilter announcmentsId) {
        this.announcmentsId = announcmentsId;
    }

    public LongFilter getSupervisorsId() {
        return supervisorsId;
    }

    public void setSupervisorsId(LongFilter supervisorsId) {
        this.supervisorsId = supervisorsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FacultyCriteria that = (FacultyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(studentsId, that.studentsId) &&
            Objects.equals(projectsId, that.projectsId) &&
            Objects.equals(announcmentsId, that.announcmentsId) &&
            Objects.equals(supervisorsId, that.supervisorsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        studentsId,
        projectsId,
        announcmentsId,
        supervisorsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacultyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (studentsId != null ? "studentsId=" + studentsId + ", " : "") +
                (projectsId != null ? "projectsId=" + projectsId + ", " : "") +
                (announcmentsId != null ? "announcmentsId=" + announcmentsId + ", " : "") +
                (supervisorsId != null ? "supervisorsId=" + supervisorsId + ", " : "") +
            "}";
    }

}
