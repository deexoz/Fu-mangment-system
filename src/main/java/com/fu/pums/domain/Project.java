package com.fu.pums.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "details")
    private String details;

    @Lob
    @Column(name = "objectives")
    private String objectives;

    @Lob
    @Column(name = "problems")
    private String problems;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<File> files = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Observation> observations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "projects", allowSetters = true)
    private Faculty faculty;

    @ManyToOne
    @JsonIgnoreProperties(value = "projects", allowSetters = true)
    private Supervisor supervisor;

    @ManyToOne
    @JsonIgnoreProperties(value = "projects", allowSetters = true)
    private Batch batch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public Project details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getObjectives() {
        return objectives;
    }

    public Project objectives(String objectives) {
        this.objectives = objectives;
        return this;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getProblems() {
        return problems;
    }

    public Project problems(String problems) {
        this.problems = problems;
        return this;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Project students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Project addStudents(Student student) {
        this.students.add(student);
        student.setProject(this);
        return this;
    }

    public Project removeStudents(Student student) {
        this.students.remove(student);
        student.setProject(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Project files(Set<File> files) {
        this.files = files;
        return this;
    }

    public Project addFiles(File file) {
        this.files.add(file);
        file.setProject(this);
        return this;
    }

    public Project removeFiles(File file) {
        this.files.remove(file);
        file.setProject(null);
        return this;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Observation> getObservations() {
        return observations;
    }

    public Project observations(Set<Observation> observations) {
        this.observations = observations;
        return this;
    }

    public Project addObservations(Observation observation) {
        this.observations.add(observation);
        observation.setProject(this);
        return this;
    }

    public Project removeObservations(Observation observation) {
        this.observations.remove(observation);
        observation.setProject(null);
        return this;
    }

    public void setObservations(Set<Observation> observations) {
        this.observations = observations;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Project faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public Project supervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Batch getBatch() {
        return batch;
    }

    public Project batch(Batch batch) {
        this.batch = batch;
        return this;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", objectives='" + getObjectives() + "'" +
            ", problems='" + getProblems() + "'" +
            "}";
    }
}
