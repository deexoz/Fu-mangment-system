import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhaseType, PhaseType } from 'app/shared/model/phase-type.model';
import { PhaseTypeService } from './phase-type.service';
import { PhaseTypeComponent } from './phase-type.component';
import { PhaseTypeDetailComponent } from './phase-type-detail.component';
import { PhaseTypeUpdateComponent } from './phase-type-update.component';

@Injectable({ providedIn: 'root' })
export class PhaseTypeResolve implements Resolve<IPhaseType> {
  constructor(private service: PhaseTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhaseType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((phaseType: HttpResponse<PhaseType>) => {
          if (phaseType.body) {
            return of(phaseType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhaseType());
  }
}

export const phaseTypeRoute: Routes = [
  {
    path: '',
    component: PhaseTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pumsApp.phaseType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhaseTypeDetailComponent,
    resolve: {
      phaseType: PhaseTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phaseType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhaseTypeUpdateComponent,
    resolve: {
      phaseType: PhaseTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phaseType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhaseTypeUpdateComponent,
    resolve: {
      phaseType: PhaseTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.phaseType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
