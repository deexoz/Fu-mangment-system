package com.fu.pums.domain;

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
    @Column(name = "detials")
    private String detials;

    @Lob
    @Column(name = "objective")
    private String objective;

    @Lob
    @Column(name = "problem")
    private String problem;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Phase> phases = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<File> files = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

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

    public String getDetials() {
        return detials;
    }

    public Project detials(String detials) {
        this.detials = detials;
        return this;
    }

    public void setDetials(String detials) {
        this.detials = detials;
    }

    public String getObjective() {
        return objective;
    }

    public Project objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getProblem() {
        return problem;
    }

    public Project problem(String problem) {
        this.problem = problem;
        return this;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Set<Phase> getPhases() {
        return phases;
    }

    public Project phases(Set<Phase> phases) {
        this.phases = phases;
        return this;
    }

    public Project addPhase(Phase phase) {
        this.phases.add(phase);
        phase.setProject(this);
        return this;
    }

    public Project removePhase(Phase phase) {
        this.phases.remove(phase);
        phase.setProject(null);
        return this;
    }

    public void setPhases(Set<Phase> phases) {
        this.phases = phases;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Project files(Set<File> files) {
        this.files = files;
        return this;
    }

    public Project addFile(File file) {
        this.files.add(file);
        file.setProject(this);
        return this;
    }

    public Project removeFile(File file) {
        this.files.remove(file);
        file.setProject(null);
        return this;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Project students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Project addStudent(Student student) {
        this.students.add(student);
        student.setProject(this);
        return this;
    }

    public Project removeStudent(Student student) {
        this.students.remove(student);
        student.setProject(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
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
            ", detials='" + getDetials() + "'" +
            ", objective='" + getObjective() + "'" +
            ", problem='" + getProblem() + "'" +
            "}";
    }
}
