import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPhaseType, PhaseType } from 'app/shared/model/phase-type.model';
import { PhaseTypeService } from './phase-type.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from 'app/entities/phase/phase.service';

@Component({
  selector: 'jhi-phase-type-update',
  templateUrl: './phase-type-update.component.html',
})
export class PhaseTypeUpdateComponent implements OnInit {
  isSaving = false;
  phases: IPhase[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    phaseType: [],
    phase: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected phaseTypeService: PhaseTypeService,
    protected phaseService: PhaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phaseType }) => {
      this.updateForm(phaseType);

      this.phaseService.query().subscribe((res: HttpResponse<IPhase[]>) => (this.phases = res.body || []));
    });
  }

  updateForm(phaseType: IPhaseType): void {
    this.editForm.patchValue({
      id: phaseType.id,
      title: phaseType.title,
      description: phaseType.description,
      phaseType: phaseType.phaseType,
      phase: phaseType.phase,
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
        new JhiEventWithContent<AlertError>('pumsApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phaseType = this.createFromForm();
    if (phaseType.id !== undefined) {
      this.subscribeToSaveResponse(this.phaseTypeService.update(phaseType));
    } else {
      this.subscribeToSaveResponse(this.phaseTypeService.create(phaseType));
    }
  }

  private createFromForm(): IPhaseType {
    return {
      ...new PhaseType(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      phaseType: this.editForm.get(['phaseType'])!.value,
      phase: this.editForm.get(['phase'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhaseType>>): void {
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

  trackById(index: number, item: IPhase): any {
    return item.id;
  }
}
