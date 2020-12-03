import { IStudent } from 'app/shared/model/student.model';
import { IProject } from 'app/shared/model/project.model';
import { IAnnouncement } from 'app/shared/model/announcement.model';
import { ISupervisor } from 'app/shared/model/supervisor.model';

export interface IFaculty {
  id?: number;
  name?: string;
  code?: string;
  students?: IStudent[];
  projects?: IProject[];
  announcments?: IAnnouncement[];
  supervisors?: ISupervisor[];
}

export class Faculty implements IFaculty {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public students?: IStudent[],
    public projects?: IProject[],
    public announcments?: IAnnouncement[],
    public supervisors?: ISupervisor[]
  ) {}
}
