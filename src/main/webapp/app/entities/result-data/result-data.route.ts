import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultData, ResultData } from 'app/shared/model/result-data.model';
import { ResultDataService } from './result-data.service';
import { ResultDataComponent } from './result-data.component';
import { ResultDataDetailComponent } from './result-data-detail.component';
import { ResultDataUpdateComponent } from './result-data-update.component';

@Injectable({ providedIn: 'root' })
export class ResultDataResolve implements Resolve<IResultData> {
  constructor(private service: ResultDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultData: HttpResponse<ResultData>) => {
          if (resultData.body) {
            return of(resultData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultData());
  }
}

export const resultDataRoute: Routes = [
  {
    path: '',
    component: ResultDataComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pumsApp.resultData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultDataDetailComponent,
    resolve: {
      resultData: ResultDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.resultData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultDataUpdateComponent,
    resolve: {
      resultData: ResultDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.resultData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultDataUpdateComponent,
    resolve: {
      resultData: ResultDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pumsApp.resultData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
