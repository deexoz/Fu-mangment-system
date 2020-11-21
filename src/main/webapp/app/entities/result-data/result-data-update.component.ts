import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IResultData, ResultData } from 'app/shared/model/result-data.model';
import { ResultDataService } from './result-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFile } from 'app/shared/model/file.model';
import { FileService } from 'app/entities/file/file.service';

@Component({
  selector: 'jhi-result-data-update',
  templateUrl: './result-data-update.component.html',
})
export class ResultDataUpdateComponent implements OnInit {
  isSaving = false;
  files: IFile[] = [];

  editForm = this.fb.group({
    id: [],
    ovservation: [],
    date: [],
    file: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected resultDataService: ResultDataService,
    protected fileService: FileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultData }) => {
      if (!resultData.id) {
        const today = moment().startOf('day');
        resultData.date = today;
      }

      this.updateForm(resultData);

      this.fileService
        .query({ filter: 'resultdata-is-null' })
        .pipe(
          map((res: HttpResponse<IFile[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFile[]) => {
          if (!resultData.file || !resultData.file.id) {
            this.files = resBody;
          } else {
            this.fileService
              .find(resultData.file.id)
              .pipe(
                map((subRes: HttpResponse<IFile>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFile[]) => (this.files = concatRes));
          }
        });
    });
  }

  updateForm(resultData: IResultData): void {
    this.editForm.patchValue({
      id: resultData.id,
      ovservation: resultData.ovservation,
      date: resultData.date ? resultData.date.format(DATE_TIME_FORMAT) : null,
      file: resultData.file,
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
    const resultData = this.createFromForm();
    if (resultData.id !== undefined) {
      this.subscribeToSaveResponse(this.resultDataService.update(resultData));
    } else {
      this.subscribeToSaveResponse(this.resultDataService.create(resultData));
    }
  }

  private createFromForm(): IResultData {
    return {
      ...new ResultData(),
      id: this.editForm.get(['id'])!.value,
      ovservation: this.editForm.get(['ovservation'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      file: this.editForm.get(['file'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultData>>): void {
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

  trackById(index: number, item: IFile): any {
    return item.id;
  }
}
