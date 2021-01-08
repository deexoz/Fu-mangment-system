import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.ProjecunitmangmentStudentModule),
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.ProjecunitmangmentProjectModule),
      },
      {
        path: 'supervisor',
        loadChildren: () => import('./supervisor/supervisor.module').then(m => m.ProjecunitmangmentSupervisorModule),
      },
      {
        path: 'faculty',
        loadChildren: () => import('./faculty/faculty.module').then(m => m.ProjecunitmangmentFacultyModule),
      },
      {
        path: 'announcement',
        loadChildren: () => import('./announcement/announcement.module').then(m => m.ProjecunitmangmentAnnouncementModule),
      },
      {
        path: 'observation',
        loadChildren: () => import('./observation/observation.module').then(m => m.ProjecunitmangmentObservationModule),
      },
      {
        path: 'file',
        loadChildren: () => import('./file/file.module').then(m => m.ProjecunitmangmentFileModule),
      },
      {
        path: 'batch',
        loadChildren: () => import('./batch/batch.module').then(m => m.ProjecunitmangmentBatchModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ProjecunitmangmentEntityModule {}
