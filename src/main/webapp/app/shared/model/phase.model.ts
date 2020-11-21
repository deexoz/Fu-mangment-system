import { Moment } from 'moment';
import { IResultData } from 'app/shared/model/result-data.model';
import { IPhaseType } from 'app/shared/model/phase-type.model';
import { IAnnouncement } from 'app/shared/model/announcement.model';
import { IProject } from 'app/shared/model/project.model';

export interface IPhase {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  discutionTime?: string;
  resultData?: IResultData;
  phaseTypes?: IPhaseType[];
  announcements?: IAnnouncement[];
  project?: IProject;
}

export class Phase implements IPhase {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public discutionTime?: string,
    public resultData?: IResultData,
    public phaseTypes?: IPhaseType[],
    public announcements?: IAnnouncement[],
    public project?: IProject
  ) {}
}
