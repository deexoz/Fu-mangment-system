import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnnouncement } from 'app/shared/model/announcement.model';

@Component({
  selector: 'jhi-announcement-detail',
  templateUrl: './announcement-detail.component.html',
})
export class AnnouncementDetailComponent implements OnInit {
  announcement: IAnnouncement | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcement }) => (this.announcement = announcement));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
