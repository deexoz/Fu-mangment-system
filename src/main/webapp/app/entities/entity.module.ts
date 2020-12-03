import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.PumsStudentModule),
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.PumsProjectModule),
      },
      {
        path: 'supervisor',
        loadChildren: () => import('./supervisor/supervisor.module').then(m => m.PumsSupervisorModule),
      },
      {
        path: 'faculty',
        loadChildren: () => import('./faculty/faculty.module').then(m => m.PumsFacultyModule),
      },
      {
<<<<<<< HEAD
        path: 'phase-type',
        loadChildren: () => import('./phase-type/phase-type.module').then(m => m.PumsPhaseTypeModule),
      },
      {
        path: 'phase',
        loadChildren: () => import('./phase/phase.module').then(m => m.PumsPhaseModule),
      },
      {
=======
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
        path: 'announcement',
        loadChildren: () => import('./announcement/announcement.module').then(m => m.PumsAnnouncementModule),
      },
      {
<<<<<<< HEAD
        path: 'result-data',
        loadChildren: () => import('./result-data/result-data.module').then(m => m.PumsResultDataModule),
=======
        path: 'observation',
        loadChildren: () => import('./observation/observation.module').then(m => m.PumsObservationModule),
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
      },
      {
        path: 'file',
        loadChildren: () => import('./file/file.module').then(m => m.PumsFileModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PumsEntityModule {}
