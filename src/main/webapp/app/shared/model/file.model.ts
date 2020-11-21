import { Moment } from 'moment';
import { IResultData } from 'app/shared/model/result-data.model';
import { IProject } from 'app/shared/model/project.model';

export interface IFile {
  id?: number;
  name?: string;
  fileContentType?: string;
  file?: any;
  date?: Moment;
  type?: string;
  resultData?: IResultData;
  project?: IProject;
}

export class File implements IFile {
  constructor(
    public id?: number,
    public name?: string,
    public fileContentType?: string,
    public file?: any,
    public date?: Moment,
    public type?: string,
    public resultData?: IResultData,
    public project?: IProject
  ) {}
}
