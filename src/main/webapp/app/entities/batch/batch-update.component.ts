import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBatch, Batch } from 'app/shared/model/batch.model';
import { BatchService } from './batch.service';

@Component({
  selector: 'jhi-batch-update',
  templateUrl: './batch-update.component.html',
})
export class BatchUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    year: [],
  });

  constructor(protected batchService: BatchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batch }) => {
      this.updateForm(batch);
    });
  }

  updateForm(batch: IBatch): void {
    this.editForm.patchValue({
      id: batch.id,
      year: batch.year,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const batch = this.createFromForm();
    if (batch.id !== undefined) {
      this.subscribeToSaveResponse(this.batchService.update(batch));
    } else {
      this.subscribeToSaveResponse(this.batchService.create(batch));
    }
  }

  private createFromForm(): IBatch {
    return {
      ...new Batch(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBatch>>): void {
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
}
