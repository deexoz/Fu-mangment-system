import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProject, Project } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from 'app/entities/faculty/faculty.service';
import { ISupervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from 'app/entities/supervisor/supervisor.service';
import { IBatch } from 'app/shared/model/batch.model';
import { BatchService } from 'app/entities/batch/batch.service';
import { IStudent } from 'app/shared/model/student.model';
import { ProjectDTO } from 'app/shared/model/projectDTO';

type SelectableEntity = IFaculty | ISupervisor | IBatch;

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  faculties: IFaculty[] = [];
  supervisors: ISupervisor[] = [];
  batches: IBatch[] = [];
  students: IStudent[] = [];

  firstStudent = this.fb.group({
    index: [],
    fullNameArabic: [],
    phone: [],
  });
  secondStudent = this.fb.group({
    index: [],
    fullNameArabic: [],
    phone: [],
  });
  editForm = this.fb.group({
    name: [],
    faculty: [],
    supervisor: [],
    batch: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected projectService: ProjectService,
    protected facultyService: FacultyService,
    protected supervisorService: SupervisorService,
    protected batchService: BatchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.facultyService.query().subscribe((res: HttpResponse<IFaculty[]>) => (this.faculties = res.body || []));

      this.supervisorService.query().subscribe((res: HttpResponse<ISupervisor[]>) => (this.supervisors = res.body || []));

      this.batchService.query().subscribe((res: HttpResponse<IBatch[]>) => (this.batches = res.body || []));
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('projecunitmangmentApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectDTO = new ProjectDTO();
    projectDTO.project = this.createFromForm();
    this.students.push(this.secondStudent.value);
    this.students.push(this.firstStudent.value);
    projectDTO.students = this.students;

    console.error(projectDTO);

    this.subscribeToSaveResponse(this.projectService.studentProjectCreation(projectDTO));
  }

  private createFromForm(): IProject {
    return {
      ...new Project(),
      name: this.editForm.get(['name'])!.value,
      supervisor: this.editForm.get(['supervisor'])!.value,
      faculty: this.editForm.get(['faculty'])!.value,
      batch: this.editForm.get(['batch'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
