import { IProject } from 'app/shared/model/project.model';
import { IFaculty } from 'app/shared/model/faculty.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ISupervisor {
  id?: number;
  fullName?: string;
  role?: string;
  gender?: Gender;
  email?: string;
  phoneNumber?: string;
  projects?: IProject[];
  faculties?: IFaculty[];
}

export class Supervisor implements ISupervisor {
  constructor(
    public id?: number,
    public fullName?: string,
    public role?: string,
    public gender?: Gender,
    public email?: string,
    public phoneNumber?: string,
    public projects?: IProject[],
    public faculties?: IFaculty[]
  ) {}
}
