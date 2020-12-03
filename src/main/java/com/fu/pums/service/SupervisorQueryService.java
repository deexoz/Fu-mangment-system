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

import com.fu.pums.domain.Supervisor;
import com.fu.pums.domain.*; // for static metamodels
import com.fu.pums.repository.SupervisorRepository;
import com.fu.pums.service.dto.SupervisorCriteria;

/**
 * Service for executing complex queries for {@link Supervisor} entities in the database.
 * The main input is a {@link SupervisorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Supervisor} or a {@link Page} of {@link Supervisor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupervisorQueryService extends QueryService<Supervisor> {

    private final Logger log = LoggerFactory.getLogger(SupervisorQueryService.class);

    private final SupervisorRepository supervisorRepository;

    public SupervisorQueryService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    /**
     * Return a {@link List} of {@link Supervisor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Supervisor> findByCriteria(SupervisorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Supervisor> specification = createSpecification(criteria);
        return supervisorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Supervisor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Supervisor> findByCriteria(SupervisorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Supervisor> specification = createSpecification(criteria);
        return supervisorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupervisorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Supervisor> specification = createSpecification(criteria);
        return supervisorRepository.count(specification);
    }

    /**
     * Function to convert {@link SupervisorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Supervisor> createSpecification(SupervisorCriteria criteria) {
        Specification<Supervisor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Supervisor_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Supervisor_.fullName));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Supervisor_.role));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Supervisor_.gender));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Supervisor_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Supervisor_.phoneNumber));
            }
            if (criteria.getProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectsId(),
                    root -> root.join(Supervisor_.projects, JoinType.LEFT).get(Project_.id)));
            }
            if (criteria.getFacultiesId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacultiesId(),
                    root -> root.join(Supervisor_.faculties, JoinType.LEFT).get(Faculty_.id)));
            }
        }
        return specification;
    }
}
