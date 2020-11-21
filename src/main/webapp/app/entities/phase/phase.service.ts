import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhase } from 'app/shared/model/phase.model';

type EntityResponseType = HttpResponse<IPhase>;
type EntityArrayResponseType = HttpResponse<IPhase[]>;

@Injectable({ providedIn: 'root' })
export class PhaseService {
  public resourceUrl = SERVER_API_URL + 'api/phases';

  constructor(protected http: HttpClient) {}

  create(phase: IPhase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(phase);
    return this.http
      .post<IPhase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(phase: IPhase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(phase);
    return this.http
      .put<IPhase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPhase>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPhase[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(phase: IPhase): IPhase {
    const copy: IPhase = Object.assign({}, phase, {
      startDate: phase.startDate && phase.startDate.isValid() ? phase.startDate.format(DATE_FORMAT) : undefined,
      endDate: phase.endDate && phase.endDate.isValid() ? phase.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((phase: IPhase) => {
        phase.startDate = phase.startDate ? moment(phase.startDate) : undefined;
        phase.endDate = phase.endDate ? moment(phase.endDate) : undefined;
      });
    }
    return res;
  }
}
