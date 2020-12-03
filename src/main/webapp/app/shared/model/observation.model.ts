import { Moment } from 'moment';
import { IFile } from 'app/shared/model/file.model';
import { IProject } from 'app/shared/model/project.model';

export interface IObservation {
  id?: number;
  title?: string;
  detail?: any;
  creationDate?: Moment;
  file?: IFile;
  project?: IProject;
}

export class Observation implements IObservation {
  constructor(
    public id?: number,
    public title?: string,
    public detail?: any,
    public creationDate?: Moment,
    public file?: IFile,
    public project?: IProject
  ) {}
}
