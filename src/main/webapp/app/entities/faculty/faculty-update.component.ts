import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFaculty, Faculty } from 'app/shared/model/faculty.model';
import { FacultyService } from './faculty.service';
import { ISupervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from 'app/entities/supervisor/supervisor.service';

@Component({
  selector: 'jhi-faculty-update',
  templateUrl: './faculty-update.component.html',
})
export class FacultyUpdateComponent implements OnInit {
  isSaving = false;
  supervisors: ISupervisor[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    code: [],
    supervisors: [],
  });

  constructor(
    protected facultyService: FacultyService,
    protected supervisorService: SupervisorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ faculty }) => {
      this.updateForm(faculty);

      this.supervisorService.query().subscribe((res: HttpResponse<ISupervisor[]>) => (this.supervisors = res.body || []));
    });
  }

  updateForm(faculty: IFaculty): void {
    this.editForm.patchValue({
      id: faculty.id,
      name: faculty.name,
      code: faculty.code,
      supervisors: faculty.supervisors,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const faculty = this.createFromForm();
    if (faculty.id !== undefined) {
      this.subscribeToSaveResponse(this.facultyService.update(faculty));
    } else {
      this.subscribeToSaveResponse(this.facultyService.create(faculty));
    }
  }

  private createFromForm(): IFaculty {
    return {
      ...new Faculty(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      supervisors: this.editForm.get(['supervisors'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaculty>>): void {
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

  trackById(index: number, item: ISupervisor): any {
    return item.id;
  }

  getSelected(selectedVals: ISupervisor[], option: ISupervisor): ISupervisor {
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
