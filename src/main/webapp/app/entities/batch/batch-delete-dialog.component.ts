import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBatch } from 'app/shared/model/batch.model';
import { BatchService } from './batch.service';

@Component({
  templateUrl: './batch-delete-dialog.component.html',
})
export class BatchDeleteDialogComponent {
  batch?: IBatch;

  constructor(protected batchService: BatchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.batchService.delete(id).subscribe(() => {
      this.eventManager.broadcast('batchListModification');
      this.activeModal.close();
    });
  }
}
