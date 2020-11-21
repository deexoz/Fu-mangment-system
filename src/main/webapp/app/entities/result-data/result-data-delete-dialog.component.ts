import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultData } from 'app/shared/model/result-data.model';
import { ResultDataService } from './result-data.service';

@Component({
  templateUrl: './result-data-delete-dialog.component.html',
})
export class ResultDataDeleteDialogComponent {
  resultData?: IResultData;

  constructor(
    protected resultDataService: ResultDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultDataListModification');
      this.activeModal.close();
    });
  }
}
