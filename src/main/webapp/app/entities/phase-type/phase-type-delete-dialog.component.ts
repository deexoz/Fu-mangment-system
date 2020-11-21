import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhaseType } from 'app/shared/model/phase-type.model';
import { PhaseTypeService } from './phase-type.service';

@Component({
  templateUrl: './phase-type-delete-dialog.component.html',
})
export class PhaseTypeDeleteDialogComponent {
  phaseType?: IPhaseType;

  constructor(protected phaseTypeService: PhaseTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phaseTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('phaseTypeListModification');
      this.activeModal.close();
    });
  }
}
