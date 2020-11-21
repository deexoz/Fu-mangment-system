import { IPhase } from 'app/shared/model/phase.model';
import { IFile } from 'app/shared/model/file.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IProject {
  id?: number;
  name?: string;
  detials?: any;
  objective?: any;
  problem?: any;
  phases?: IPhase[];
  files?: IFile[];
  students?: IStudent[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public detials?: any,
    public objective?: any,
    public problem?: any,
    public phases?: IPhase[],
    public files?: IFile[],
    public students?: IStudent[]
  ) {}
}
