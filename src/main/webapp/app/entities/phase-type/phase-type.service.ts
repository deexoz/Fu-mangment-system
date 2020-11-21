import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhaseType } from 'app/shared/model/phase-type.model';

type EntityResponseType = HttpResponse<IPhaseType>;
type EntityArrayResponseType = HttpResponse<IPhaseType[]>;

@Injectable({ providedIn: 'root' })
export class PhaseTypeService {
  public resourceUrl = SERVER_API_URL + 'api/phase-types';

  constructor(protected http: HttpClient) {}

  create(phaseType: IPhaseType): Observable<EntityResponseType> {
    return this.http.post<IPhaseType>(this.resourceUrl, phaseType, { observe: 'response' });
  }

  update(phaseType: IPhaseType): Observable<EntityResponseType> {
    return this.http.put<IPhaseType>(this.resourceUrl, phaseType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhaseType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhaseType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
