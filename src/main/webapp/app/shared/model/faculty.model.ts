import { IStudent } from 'app/shared/model/student.model';
<<<<<<< HEAD
=======
import { IProject } from 'app/shared/model/project.model';
import { IAnnouncement } from 'app/shared/model/announcement.model';
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
import { ISupervisor } from 'app/shared/model/supervisor.model';

export interface IFaculty {
  id?: number;
  name?: string;
<<<<<<< HEAD
  students?: IStudent[];
  supservisors?: ISupervisor[];
}

export class Faculty implements IFaculty {
  constructor(public id?: number, public name?: string, public students?: IStudent[], public supservisors?: ISupervisor[]) {}
=======
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
>>>>>>> Initial version of pums generated by JHipster-6.10.5 with entity
}
