import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupervisor, Supervisor } from 'app/shared/model/supervisor.model';
import { SupervisorService } from './supervisor.service';
import { SupervisorComponent } from './supervisor.component';
import { SupervisorDetailComponent } from './supervisor-detail.component';
import { SupervisorUpdateComponent } from './supervisor-update.component';

@Injectable({ providedIn: 'root' })
export class SupervisorResolve implements Resolve<ISupervisor> {
  constructor(private service: SupervisorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupervisor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supervisor: HttpResponse<Supervisor>) => {
          if (supervisor.body) {
            return of(supervisor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Supervisor());
  }
}

export const supervisorRoute: Routes = [
  {
    path: '',
    component: SupervisorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pumsApp.supervisor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SupervisorDetailComponent,
    resolve: {
      supervisor: SupervisorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.supervisor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SupervisorUpdateComponent,
    resolve: {
      supervisor: SupervisorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.supervisor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SupervisorUpdateComponent,
    resolve: {
      supervisor: SupervisorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.supervisor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
