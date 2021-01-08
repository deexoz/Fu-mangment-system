import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjecunitmangmentSharedModule } from 'app/shared/shared.module';
import { BatchComponent } from './batch.component';
import { BatchDetailComponent } from './batch-detail.component';
import { BatchUpdateComponent } from './batch-update.component';
import { BatchDeleteDialogComponent } from './batch-delete-dialog.component';
import { batchRoute } from './batch.route';

@NgModule({
  imports: [ProjecunitmangmentSharedModule, RouterModule.forChild(batchRoute)],
  declarations: [BatchComponent, BatchDetailComponent, BatchUpdateComponent, BatchDeleteDialogComponent],
  entryComponents: [BatchDeleteDialogComponent],
})
export class ProjecunitmangmentBatchModule {}
