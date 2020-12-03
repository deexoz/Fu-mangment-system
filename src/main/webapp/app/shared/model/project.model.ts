import { IStudent } from 'app/shared/model/student.model';
import { IFile } from 'app/shared/model/file.model';
import { IObservation } from 'app/shared/model/observation.model';
import { IFaculty } from 'app/shared/model/faculty.model';
import { ISupervisor } from 'app/shared/model/supervisor.model';

export interface IProject {
  id?: number;
  name?: string;
  details?: any;
  objectives?: any;
  problems?: any;
  students?: IStudent[];
  files?: IFile[];
  observations?: IObservation[];
  faculty?: IFaculty;
  supervisor?: ISupervisor;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public details?: any,
    public objectives?: any,
    public problems?: any,
    public students?: IStudent[],
    public files?: IFile[],
    public observations?: IObservation[],
    public faculty?: IFaculty,
    public supervisor?: ISupervisor
  ) {}
}
