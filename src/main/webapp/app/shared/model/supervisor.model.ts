import { IFaculty } from 'app/shared/model/faculty.model';

export interface ISupervisor {
  id?: number;
  phoneNumber?: string;
  faculties?: IFaculty[];
}

export class Supervisor implements ISupervisor {
  constructor(public id?: number, public phoneNumber?: string, public faculties?: IFaculty[]) {}
}
