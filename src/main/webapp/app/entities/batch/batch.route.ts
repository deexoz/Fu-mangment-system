import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBatch, Batch } from 'app/shared/model/batch.model';
import { BatchService } from './batch.service';
import { BatchComponent } from './batch.component';
import { BatchDetailComponent } from './batch-detail.component';
import { BatchUpdateComponent } from './batch-update.component';

@Injectable({ providedIn: 'root' })
export class BatchResolve implements Resolve<IBatch> {
  constructor(private service: BatchService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBatch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((batch: HttpResponse<Batch>) => {
          if (batch.body) {
            return of(batch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Batch());
  }
}

export const batchRoute: Routes = [
  {
    path: '',
    component: BatchComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'projecunitmangmentApp.batch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BatchDetailComponent,
    resolve: {
      batch: BatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projecunitmangmentApp.batch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BatchUpdateComponent,
    resolve: {
      batch: BatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projecunitmangmentApp.batch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BatchUpdateComponent,
    resolve: {
      batch: BatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projecunitmangmentApp.batch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
