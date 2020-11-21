import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISupervisor, Supervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from './supervisor.service';
import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from 'app/entities/faculty/faculty.service';

@Component({
  selector: 'jhi-supervisor-update',
  templateUrl: './supervisor-update.component.html',
})
export class SupervisorUpdateComponent implements OnInit {
  isSaving = false;
  faculties: IFaculty[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    faculties: [],
  });

  constructor(
    protected supervisorService: SupervisorService,
    protected facultyService: FacultyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supervisor }) => {
      this.updateForm(supervisor);

      this.facultyService.query().subscribe((res: HttpResponse<IFaculty[]>) => (this.faculties = res.body || []));
    });
  }

  updateForm(supervisor: ISupervisor): void {
    this.editForm.patchValue({
      id: supervisor.id,
      phoneNumber: supervisor.phoneNumber,
      faculties: supervisor.faculties,
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
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      faculties: this.editForm.get(['faculties'])!.value,
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

  trackById(index: number, item: IFaculty): any {
    return item.id;
  }

  getSelected(selectedVals: IFaculty[], option: IFaculty): IFaculty {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
