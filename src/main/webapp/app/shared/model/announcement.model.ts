import { Moment } from 'moment';
import { IFaculty } from 'app/shared/model/faculty.model';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IAnnouncement {
  id?: number;
  title?: string;
  content?: any;
  announcmentType?: AnnouncementType;
  startDate?: Moment;
  endDate?: Moment;
  faculty?: IFaculty;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public announcmentType?: AnnouncementType,
    public startDate?: Moment,
    public endDate?: Moment,
    public faculty?: IFaculty
  ) {}
}
