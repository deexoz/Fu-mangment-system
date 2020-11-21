import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnnouncement } from 'app/shared/model/announcement.model';

type EntityResponseType = HttpResponse<IAnnouncement>;
type EntityArrayResponseType = HttpResponse<IAnnouncement[]>;

@Injectable({ providedIn: 'root' })
export class AnnouncementService {
  public resourceUrl = SERVER_API_URL + 'api/announcements';

  constructor(protected http: HttpClient) {}

  create(announcement: IAnnouncement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcement);
    return this.http
      .post<IAnnouncement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(announcement: IAnnouncement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcement);
    return this.http
      .put<IAnnouncement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnnouncement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnnouncement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(announcement: IAnnouncement): IAnnouncement {
    const copy: IAnnouncement = Object.assign({}, announcement, {
      startDate: announcement.startDate && announcement.startDate.isValid() ? announcement.startDate.format(DATE_FORMAT) : undefined,
      endDate: announcement.endDate && announcement.endDate.isValid() ? announcement.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((announcement: IAnnouncement) => {
        announcement.startDate = announcement.startDate ? moment(announcement.startDate) : undefined;
        announcement.endDate = announcement.endDate ? moment(announcement.endDate) : undefined;
      });
    }
    return res;
  }
}
