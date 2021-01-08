import { Moment } from 'moment';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IAnnouncement {
  id?: number;
  title?: string;
  content?: any;
  announcementType?: AnnouncementType;
  startDate?: Moment;
  endDate?: Moment;
  open?: boolean;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public announcementType?: AnnouncementType,
    public startDate?: Moment,
    public endDate?: Moment,
    public open?: boolean
  ) {
    this.open = this.open || false;
  }
}
