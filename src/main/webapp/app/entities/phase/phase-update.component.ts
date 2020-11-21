import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPhase, Phase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';
import { IResultData } from 'app/shared/model/result-data.model';
import { ResultDataService } from 'app/entities/result-data/result-data.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

type SelectableEntity = IResultData | IProject;

@Component({
  selector: 'jhi-phase-update',
  templateUrl: './phase-update.component.html',
})
export class PhaseUpdateComponent implements OnInit {
  isSaving = false;
  resultdata: IResultData[] = [];
  projects: IProject[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    discutionTime: [],
    resultData: [],
    project: [],
  });

  constructor(
    protected phaseService: PhaseService,
    protected resultDataService: ResultDataService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phase }) => {
      this.updateForm(phase);

      this.resultDataService
        .query({ filter: 'phase-is-null' })
        .pipe(
          map((res: HttpResponse<IResultData[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IResultData[]) => {
          if (!phase.resultData || !phase.resultData.id) {
            this.resultdata = resBody;
          } else {
            this.resultDataService
              .find(phase.resultData.id)
              .pipe(
                map((subRes: HttpResponse<IResultData>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IResultData[]) => (this.resultdata = concatRes));
          }
        });

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(phase: IPhase): void {
    this.editForm.patchValue({
      id: phase.id,
      startDate: phase.startDate,
      endDate: phase.endDate,
      discutionTime: phase.discutionTime,
      resultData: phase.resultData,
      project: phase.project,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phase = this.createFromForm();
    if (phase.id !== undefined) {
      this.subscribeToSaveResponse(this.phaseService.update(phase));
    } else {
      this.subscribeToSaveResponse(this.phaseService.create(phase));
    }
  }

  private createFromForm(): IPhase {
    return {
      ...new Phase(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      discutionTime: this.editForm.get(['discutionTime'])!.value,
      resultData: this.editForm.get(['resultData'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhase>>): void {
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
