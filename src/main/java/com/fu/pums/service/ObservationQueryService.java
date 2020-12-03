package com.fu.pums.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fu.pums.domain.Observation;
import com.fu.pums.domain.*; // for static metamodels
import com.fu.pums.repository.ObservationRepository;
import com.fu.pums.service.dto.ObservationCriteria;

/**
 * Service for executing complex queries for {@link Observation} entities in the database.
 * The main input is a {@link ObservationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Observation} or a {@link Page} of {@link Observation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObservationQueryService extends QueryService<Observation> {

    private final Logger log = LoggerFactory.getLogger(ObservationQueryService.class);

    private final ObservationRepository observationRepository;

    public ObservationQueryService(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    /**
     * Return a {@link List} of {@link Observation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Observation> findByCriteria(ObservationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Observation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Observation> findByCriteria(ObservationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObservationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationRepository.count(specification);
    }

    /**
     * Function to convert {@link ObservationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Observation> createSpecification(ObservationCriteria criteria) {
        Specification<Observation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Observation_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Observation_.title));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Observation_.creationDate));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFileId(),
                    root -> root.join(Observation_.file, JoinType.LEFT).get(File_.id)));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(Observation_.project, JoinType.LEFT).get(Project_.id)));
            }
        }
        return specification;
    }
}
