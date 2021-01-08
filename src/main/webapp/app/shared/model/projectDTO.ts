import { IFaculty } from './faculty.model';
import { IStudent } from './student.model';
import { ISupervisor } from './supervisor.model';

export class ProjectDTO {
  constructor() {}
  public projectName?: string;
  public student?: IStudent[];
  public faculty?: IFaculty;
  public supervisor?: ISupervisor;
}
