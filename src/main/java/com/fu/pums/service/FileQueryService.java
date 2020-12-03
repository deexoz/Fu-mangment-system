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

import com.fu.pums.domain.File;
import com.fu.pums.domain.*; // for static metamodels
import com.fu.pums.repository.FileRepository;
import com.fu.pums.service.dto.FileCriteria;

/**
 * Service for executing complex queries for {@link File} entities in the database.
 * The main input is a {@link FileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link File} or a {@link Page} of {@link File} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileQueryService extends QueryService<File> {

    private final Logger log = LoggerFactory.getLogger(FileQueryService.class);

    private final FileRepository fileRepository;

    public FileQueryService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Return a {@link List} of {@link File} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<File> findByCriteria(FileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link File} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<File> findByCriteria(FileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.count(specification);
    }

    /**
     * Function to convert {@link FileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<File> createSpecification(FileCriteria criteria) {
        Specification<File> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), File_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), File_.name));
            }
            if (criteria.getUploadDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadDate(), File_.uploadDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), File_.type));
            }
            if (criteria.getObservationId() != null) {
                specification = specification.and(buildSpecification(criteria.getObservationId(),
                    root -> root.join(File_.observation, JoinType.LEFT).get(Observation_.id)));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(File_.project, JoinType.LEFT).get(Project_.id)));
            }
        }
        return specification;
    }
}
