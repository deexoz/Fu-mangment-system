import { Moment } from 'moment';
import { IObservation } from 'app/shared/model/observation.model';
import { IProject } from 'app/shared/model/project.model';

export interface IFile {
  id?: number;
  name?: string;
  fileContentType?: string;
  file?: any;
  uploadDate?: Moment;
  type?: string;
  observation?: IObservation;
  project?: IProject;
}

export class File implements IFile {
  constructor(
    public id?: number,
    public name?: string,
    public fileContentType?: string,
    public file?: any,
    public uploadDate?: Moment,
    public type?: string,
    public observation?: IObservation,
    public project?: IProject
  ) {}
}
