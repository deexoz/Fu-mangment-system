import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObservation } from 'app/shared/model/observation.model';

type EntityResponseType = HttpResponse<IObservation>;
type EntityArrayResponseType = HttpResponse<IObservation[]>;

@Injectable({ providedIn: 'root' })
export class ObservationService {
  public resourceUrl = SERVER_API_URL + 'api/observations';

  constructor(protected http: HttpClient) {}

  create(observation: IObservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observation);
    return this.http
      .post<IObservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(observation: IObservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observation);
    return this.http
      .put<IObservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObservation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObservation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(observation: IObservation): IObservation {
    const copy: IObservation = Object.assign({}, observation, {
      creationDate: observation.creationDate && observation.creationDate.isValid() ? observation.creationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((observation: IObservation) => {
        observation.creationDate = observation.creationDate ? moment(observation.creationDate) : undefined;
      });
    }
    return res;
  }
}
