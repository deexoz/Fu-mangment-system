import { IProject } from 'app/shared/model/project.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IBatch {
  id?: number;
  year?: string;
  projects?: IProject[];
  students?: IStudent[];
}

export class Batch implements IBatch {
  constructor(public id?: number, public year?: string, public projects?: IProject[], public students?: IStudent[]) {}
}
