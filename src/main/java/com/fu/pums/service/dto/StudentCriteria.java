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
 * Criteria class for the {@link com.fu.pums.domain.Student} entity. This class is used
 * in {@link com.fu.pums.web.rest.StudentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /students?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter index;

    private StringFilter fullNameArabic;

    private StringFilter phone;

    private LongFilter userId;

    private LongFilter facultyId;

    private LongFilter projectId;

    public StudentCriteria() {
    }

    public StudentCriteria(StudentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.index = other.index == null ? null : other.index.copy();
        this.fullNameArabic = other.fullNameArabic == null ? null : other.fullNameArabic.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.facultyId = other.facultyId == null ? null : other.facultyId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
    }

    @Override
    public StudentCriteria copy() {
        return new StudentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIndex() {
        return index;
    }

    public void setIndex(StringFilter index) {
        this.index = index;
    }

    public StringFilter getFullNameArabic() {
        return fullNameArabic;
    }

    public void setFullNameArabic(StringFilter fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(LongFilter facultyId) {
        this.facultyId = facultyId;
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
        final StudentCriteria that = (StudentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(index, that.index) &&
            Objects.equals(fullNameArabic, that.fullNameArabic) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(facultyId, that.facultyId) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        index,
        fullNameArabic,
        phone,
        userId,
        facultyId,
        projectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (index != null ? "index=" + index + ", " : "") +
                (fullNameArabic != null ? "fullNameArabic=" + fullNameArabic + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (facultyId != null ? "facultyId=" + facultyId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
