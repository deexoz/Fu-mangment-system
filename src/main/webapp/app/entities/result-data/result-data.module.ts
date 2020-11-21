import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PumsSharedModule } from 'app/shared/shared.module';
import { ResultDataComponent } from './result-data.component';
import { ResultDataDetailComponent } from './result-data-detail.component';
import { ResultDataUpdateComponent } from './result-data-update.component';
import { ResultDataDeleteDialogComponent } from './result-data-delete-dialog.component';
import { resultDataRoute } from './result-data.route';

@NgModule({
  imports: [PumsSharedModule, RouterModule.forChild(resultDataRoute)],
  declarations: [ResultDataComponent, ResultDataDetailComponent, ResultDataUpdateComponent, ResultDataDeleteDialogComponent],
  entryComponents: [ResultDataDeleteDialogComponent],
})
export class PumsResultDataModule {}
