import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PumsSharedModule } from 'app/shared/shared.module';
import { AnnouncementComponent } from './announcement.component';
import { AnnouncementDetailComponent } from './announcement-detail.component';
import { AnnouncementUpdateComponent } from './announcement-update.component';
import { AnnouncementDeleteDialogComponent } from './announcement-delete-dialog.component';
import { announcementRoute } from './announcement.route';

@NgModule({
  imports: [PumsSharedModule, RouterModule.forChild(announcementRoute)],
  declarations: [AnnouncementComponent, AnnouncementDetailComponent, AnnouncementUpdateComponent, AnnouncementDeleteDialogComponent],
  entryComponents: [AnnouncementDeleteDialogComponent],
})
export class PumsAnnouncementModule {}
