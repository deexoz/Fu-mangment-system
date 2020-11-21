import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhase, Phase } from 'app/shared/model/phase.model';
import { PhaseService } from './phase.service';
import { PhaseComponent } from './phase.component';
import { PhaseDetailComponent } from './phase-detail.component';
import { PhaseUpdateComponent } from './phase-update.component';

@Injectable({ providedIn: 'root' })
export class PhaseResolve implements Resolve<IPhase> {
  constructor(private service: PhaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((phase: HttpResponse<Phase>) => {
          if (phase.body) {
            return of(phase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Phase());
  }
}

export const phaseRoute: Routes = [
  {
    path: '',
    component: PhaseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pumsApp.phase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhaseDetailComponent,
    resolve: {
      phase: PhaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhaseUpdateComponent,
    resolve: {
      phase: PhaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhaseUpdateComponent,
    resolve: {
      phase: PhaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
