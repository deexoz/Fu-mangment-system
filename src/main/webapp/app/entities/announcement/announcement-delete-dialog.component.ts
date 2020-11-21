import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnnouncement } from 'app/shared/model/announcement.model';
import { AnnouncementService } from './announcement.service';

@Component({
  templateUrl: './announcement-delete-dialog.component.html',
})
export class AnnouncementDeleteDialogComponent {
  announcement?: IAnnouncement;

  constructor(
    protected announcementService: AnnouncementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.announcementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('announcementListModification');
      this.activeModal.close();
    });
  }
}
