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

type SelectableEntity = IFaculty | ISupervisor;

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  faculties: IFaculty[] = [];
  supervisors: ISupervisor[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    details: [],
    objectives: [],
    problems: [],
    faculty: [],
    supervisor: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected projectService: ProjectService,
    protected facultyService: FacultyService,
    protected supervisorService: SupervisorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.updateForm(project);

      this.facultyService.query().subscribe((res: HttpResponse<IFaculty[]>) => (this.faculties = res.body || []));

      this.supervisorService.query().subscribe((res: HttpResponse<ISupervisor[]>) => (this.supervisors = res.body || []));
    });
  }

  updateForm(project: IProject): void {
    this.editForm.patchValue({
      id: project.id,
      name: project.name,
      details: project.details,
      objectives: project.objectives,
      problems: project.problems,
      faculty: project.faculty,
      supervisor: project.supervisor,
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
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  private createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      details: this.editForm.get(['details'])!.value,
      objectives: this.editForm.get(['objectives'])!.value,
      problems: this.editForm.get(['problems'])!.value,
      faculty: this.editForm.get(['faculty'])!.value,
      supervisor: this.editForm.get(['supervisor'])!.value,
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
