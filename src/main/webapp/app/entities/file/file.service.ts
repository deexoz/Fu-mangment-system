import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFile } from 'app/shared/model/file.model';

type EntityResponseType = HttpResponse<IFile>;
type EntityArrayResponseType = HttpResponse<IFile[]>;

@Injectable({ providedIn: 'root' })
export class FileService {
  public resourceUrl = SERVER_API_URL + 'api/files';

  constructor(protected http: HttpClient) {}

  create(file: IFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(file);
    return this.http
      .post<IFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(file: IFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(file);
    return this.http
      .put<IFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(file: IFile): IFile {
    const copy: IFile = Object.assign({}, file, {
      uploadDate: file.uploadDate && file.uploadDate.isValid() ? file.uploadDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.uploadDate = res.body.uploadDate ? moment(res.body.uploadDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((file: IFile) => {
        file.uploadDate = file.uploadDate ? moment(file.uploadDate) : undefined;
      });
    }
    return res;
  }
}
