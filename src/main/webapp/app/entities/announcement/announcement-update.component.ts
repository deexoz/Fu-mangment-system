import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnnouncement, Announcement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPhase } from 'app/shared/model/phase.model';
import { PhaseService } from 'app/entities/phase/phase.service';

@Component({
  selector: 'jhi-announcement-update',
  templateUrl: './announcement-update.component.html',
})
export class AnnouncementUpdateComponent implements OnInit {
  isSaving = false;
  phases: IPhase[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    content: [],
    announcmentType: [],
    startDate: [],
    endDate: [],
    phase: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected announcementService: AnnouncementService,
    protected phaseService: PhaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcement }) => {
      this.updateForm(announcement);

      this.phaseService.query().subscribe((res: HttpResponse<IPhase[]>) => (this.phases = res.body || []));
    });
  }

  updateForm(announcement: IAnnouncement): void {
    this.editForm.patchValue({
      id: announcement.id,
      content: announcement.content,
      announcmentType: announcement.announcmentType,
      startDate: announcement.startDate,
      endDate: announcement.endDate,
      phase: announcement.phase,
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
    const announcement = this.createFromForm();
    if (announcement.id !== undefined) {
      this.subscribeToSaveResponse(this.announcementService.update(announcement));
    } else {
      this.subscribeToSaveResponse(this.announcementService.create(announcement));
    }
  }

  private createFromForm(): IAnnouncement {
    return {
      ...new Announcement(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      announcmentType: this.editForm.get(['announcmentType'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      phase: this.editForm.get(['phase'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnouncement>>): void {
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
