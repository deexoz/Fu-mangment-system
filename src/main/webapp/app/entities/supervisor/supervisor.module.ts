import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PumsSharedModule } from 'app/shared/shared.module';
import { SupervisorComponent } from './supervisor.component';
import { SupervisorDetailComponent } from './supervisor-detail.component';
import { SupervisorUpdateComponent } from './supervisor-update.component';
import { SupervisorDeleteDialogComponent } from './supervisor-delete-dialog.component';
import { supervisorRoute } from './supervisor.route';

@NgModule({
  imports: [PumsSharedModule, RouterModule.forChild(supervisorRoute)],
  declarations: [SupervisorComponent, SupervisorDetailComponent, SupervisorUpdateComponent, SupervisorDeleteDialogComponent],
  entryComponents: [SupervisorDeleteDialogComponent],
})
export class PumsSupervisorModule {}
