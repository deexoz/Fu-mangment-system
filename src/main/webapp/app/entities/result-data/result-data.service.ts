import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultData } from 'app/shared/model/result-data.model';

type EntityResponseType = HttpResponse<IResultData>;
type EntityArrayResponseType = HttpResponse<IResultData[]>;

@Injectable({ providedIn: 'root' })
export class ResultDataService {
  public resourceUrl = SERVER_API_URL + 'api/result-data';

  constructor(protected http: HttpClient) {}

  create(resultData: IResultData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultData);
    return this.http
      .post<IResultData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resultData: IResultData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultData);
    return this.http
      .put<IResultData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResultData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResultData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resultData: IResultData): IResultData {
    const copy: IResultData = Object.assign({}, resultData, {
      date: resultData.date && resultData.date.isValid() ? resultData.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resultData: IResultData) => {
        resultData.date = resultData.date ? moment(resultData.date) : undefined;
      });
    }
    return res;
  }
}
