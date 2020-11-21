import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from './supervisor.service';

@Component({
  templateUrl: './supervisor-delete-dialog.component.html',
})
export class SupervisorDeleteDialogComponent {
  supervisor?: ISupervisor;

  constructor(
    protected supervisorService: SupervisorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supervisorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supervisorListModification');
      this.activeModal.close();
    });
  }
}
