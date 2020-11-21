import { IStudent } from 'app/shared/model/student.model';
import { ISupervisor } from 'app/shared/model/supervisor.model';

export interface IFaculty {
  id?: number;
  name?: string;
  students?: IStudent[];
  supservisors?: ISupervisor[];
}

export class Faculty implements IFaculty {
  constructor(public id?: number, public name?: string, public students?: IStudent[], public supservisors?: ISupervisor[]) {}
}
