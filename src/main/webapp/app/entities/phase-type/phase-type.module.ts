import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PumsSharedModule } from 'app/shared/shared.module';
import { PhaseTypeComponent } from './phase-type.component';
import { PhaseTypeDetailComponent } from './phase-type-detail.component';
import { PhaseTypeUpdateComponent } from './phase-type-update.component';
import { PhaseTypeDeleteDialogComponent } from './phase-type-delete-dialog.component';
import { phaseTypeRoute } from './phase-type.route';

@NgModule({
  imports: [PumsSharedModule, RouterModule.forChild(phaseTypeRoute)],
  declarations: [PhaseTypeComponent, PhaseTypeDetailComponent, PhaseTypeUpdateComponent, PhaseTypeDeleteDialogComponent],
  entryComponents: [PhaseTypeDeleteDialogComponent],
})
export class PumsPhaseTypeModule {}
