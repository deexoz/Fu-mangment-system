import { Moment } from 'moment';
import { IPhase } from 'app/shared/model/phase.model';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IAnnouncement {
  id?: number;
  content?: any;
  announcmentType?: AnnouncementType;
  startDate?: Moment;
  endDate?: Moment;
  phase?: IPhase;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public content?: any,
    public announcmentType?: AnnouncementType,
    public startDate?: Moment,
    public endDate?: Moment,
    public phase?: IPhase
  ) {}
}
