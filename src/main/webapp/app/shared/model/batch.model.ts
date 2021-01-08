import { IProject } from 'app/shared/model/project.model';

export interface IBatch {
  id?: number;
  year?: string;
  projects?: IProject[];
}

export class Batch implements IBatch {
  constructor(public id?: number, public year?: string, public projects?: IProject[]) {}
}
