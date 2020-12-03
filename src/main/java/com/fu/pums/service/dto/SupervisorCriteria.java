package com.fu.pums.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fu.pums.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fu.pums.domain.Supervisor} entity. This class is used
 * in {@link com.fu.pums.web.rest.SupervisorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supervisors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupervisorCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter role;

    private GenderFilter gender;

    private StringFilter email;

    private StringFilter phoneNumber;

    private LongFilter projectsId;

    private LongFilter facultiesId;

    public SupervisorCriteria() {
    }

    public SupervisorCriteria(SupervisorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.projectsId = other.projectsId == null ? null : other.projectsId.copy();
        this.facultiesId = other.facultiesId == null ? null : other.facultiesId.copy();
    }

    @Override
    public SupervisorCriteria copy() {
        return new SupervisorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getRole() {
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LongFilter getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(LongFilter projectsId) {
        this.projectsId = projectsId;
    }

    public LongFilter getFacultiesId() {
        return facultiesId;
    }

    public void setFacultiesId(LongFilter facultiesId) {
        this.facultiesId = facultiesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupervisorCriteria that = (SupervisorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(role, that.role) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(projectsId, that.projectsId) &&
            Objects.equals(facultiesId, that.facultiesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        role,
        gender,
        email,
        phoneNumber,
        projectsId,
        facultiesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupervisorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (projectsId != null ? "projectsId=" + projectsId + ", " : "") +
                (facultiesId != null ? "facultiesId=" + facultiesId + ", " : "") +
            "}";
    }

}
