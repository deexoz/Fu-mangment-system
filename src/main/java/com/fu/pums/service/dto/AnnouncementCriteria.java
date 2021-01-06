package com.fu.pums.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fu.pums.domain.enumeration.AnnouncementType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fu.pums.domain.Announcement} entity. This class is used
 * in {@link com.fu.pums.web.rest.AnnouncementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /announcements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnnouncementCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AnnouncementType
     */
    public static class AnnouncementTypeFilter extends Filter<AnnouncementType> {

        public AnnouncementTypeFilter() {
        }

        public AnnouncementTypeFilter(AnnouncementTypeFilter filter) {
            super(filter);
        }

        @Override
        public AnnouncementTypeFilter copy() {
            return new AnnouncementTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private AnnouncementTypeFilter announcementType;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private BooleanFilter open;

    private LongFilter facultyId;

    public AnnouncementCriteria() {
    }

    public AnnouncementCriteria(AnnouncementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.announcementType = other.announcementType == null ? null : other.announcementType.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.open = other.open == null ? null : other.open.copy();
        this.facultyId = other.facultyId == null ? null : other.facultyId.copy();
    }

    @Override
    public AnnouncementCriteria copy() {
        return new AnnouncementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public AnnouncementTypeFilter getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(AnnouncementTypeFilter announcementType) {
        this.announcementType = announcementType;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public BooleanFilter getOpen() {
        return open;
    }

    public void setOpen(BooleanFilter open) {
        this.open = open;
    }

    public LongFilter getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(LongFilter facultyId) {
        this.facultyId = facultyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnnouncementCriteria that = (AnnouncementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(announcementType, that.announcementType) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(open, that.open) &&
            Objects.equals(facultyId, that.facultyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        announcementType,
        startDate,
        endDate,
        open,
        facultyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnnouncementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (announcementType != null ? "announcementType=" + announcementType + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (open != null ? "open=" + open + ", " : "") +
                (facultyId != null ? "facultyId=" + facultyId + ", " : "") +
            "}";
    }

}
