import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISupervisor, Supervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from './supervisor.service';

@Component({
  selector: 'jhi-supervisor-update',
  templateUrl: './supervisor-update.component.html',
})
export class SupervisorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fullName: [],
    role: [],
    gender: [],
    email: [],
    phoneNumber: [],
  });

  constructor(protected supervisorService: SupervisorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supervisor }) => {
      this.updateForm(supervisor);
    });
  }

  updateForm(supervisor: ISupervisor): void {
    this.editForm.patchValue({
      id: supervisor.id,
      fullName: supervisor.fullName,
      role: supervisor.role,
      gender: supervisor.gender,
      email: supervisor.email,
      phoneNumber: supervisor.phoneNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supervisor = this.createFromForm();
    if (supervisor.id !== undefined) {
      this.subscribeToSaveResponse(this.supervisorService.update(supervisor));
    } else {
      this.subscribeToSaveResponse(this.supervisorService.create(supervisor));
    }
  }

  private createFromForm(): ISupervisor {
    return {
      ...new Supervisor(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      role: this.editForm.get(['role'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupervisor>>): void {
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
