import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IObservation, Observation } from 'app/shared/model/observation.model';
import { ObservationService } from './observation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFile } from 'app/shared/model/file.model';
import { FileService } from 'app/entities/file/file.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

type SelectableEntity = IFile | IProject;

@Component({
  selector: 'jhi-observation-update',
  templateUrl: './observation-update.component.html',
})
export class ObservationUpdateComponent implements OnInit {
  isSaving = false;
  files: IFile[] = [];
  projects: IProject[] = [];

  fileForm = this.fb.group({
    name: [],
    file: [],
    fileContentType: [],
    uploadDate: [],
    type: [],
  });

  editForm = this.fb.group({
    id: [],
    title: [],
    detail: [],
    creationDate: [],
    project: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected observationService: ObservationService,
    protected fileService: FileService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observation }) => {
      if (!observation.id) {
        const today = moment().startOf('day');
        observation.creationDate = today;
      }

      this.updateForm(observation);

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(observation: IObservation): void {
    this.editForm.patchValue({
      id: observation.id,
      title: observation.title,
      detail: observation.detail,
      creationDate: observation.creationDate ? observation.creationDate.format(DATE_TIME_FORMAT) : null,
      project: observation.project,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.fileForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
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
    const observation = this.createFromForm();
    console.warn(observation);

    if (observation.id !== undefined) {
      this.subscribeToSaveResponse(this.observationService.update(observation));
    } else {
      this.subscribeToSaveResponse(this.observationService.create(observation));
    }
  }

  private createFromForm(): IObservation {
    return {
      ...new Observation(),
      title: this.editForm.get(['title'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      file: this.fileForm.value,
      project: this.editForm.get(['project'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservation>>): void {
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
