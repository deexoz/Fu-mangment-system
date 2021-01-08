import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IFaculty } from 'app/shared/model/faculty.model';
import { FacultyService } from 'app/entities/faculty/faculty.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';
import { IBatch } from 'app/shared/model/batch.model';
import { BatchService } from 'app/entities/batch/batch.service';

type SelectableEntity = IUser | IFaculty | IProject | IBatch;

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  faculties: IFaculty[] = [];
  projects: IProject[] = [];
  batches: IBatch[] = [];

  editForm = this.fb.group({
    id: [],
    index: [],
    fullNameArabic: [],
    phone: [],
    user: [],
    faculty: [],
    project: [],
    batch: [],
  });

  constructor(
    protected studentService: StudentService,
    protected userService: UserService,
    protected facultyService: FacultyService,
    protected projectService: ProjectService,
    protected batchService: BatchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.facultyService.query().subscribe((res: HttpResponse<IFaculty[]>) => (this.faculties = res.body || []));

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));

      this.batchService.query().subscribe((res: HttpResponse<IBatch[]>) => (this.batches = res.body || []));
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      index: student.index,
      fullNameArabic: student.fullNameArabic,
      phone: student.phone,
      user: student.user,
      faculty: student.faculty,
      project: student.project,
      batch: student.batch,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      index: this.editForm.get(['index'])!.value,
      fullNameArabic: this.editForm.get(['fullNameArabic'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      user: this.editForm.get(['user'])!.value,
      faculty: this.editForm.get(['faculty'])!.value,
      project: this.editForm.get(['project'])!.value,
      batch: this.editForm.get(['batch'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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
