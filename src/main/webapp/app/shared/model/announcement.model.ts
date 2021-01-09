import { Moment } from 'moment';
import { IFaculty } from 'app/shared/model/faculty.model';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IAnnouncement {
  id?: number;
  title?: string;
  content?: any;
  announcementType?: AnnouncementType;
  startDate?: Moment;
  endDate?: Moment;
  open?: boolean;
  faculty?: IFaculty;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public announcementType?: AnnouncementType,
    public startDate?: Moment,
    public endDate?: Moment,
    public open?: boolean,
    public faculty?: IFaculty
  ) {
    this.open = this.open || false;
  }
}
