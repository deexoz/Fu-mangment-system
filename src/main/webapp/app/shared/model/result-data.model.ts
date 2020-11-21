import { Moment } from 'moment';
import { IFile } from 'app/shared/model/file.model';
import { IPhase } from 'app/shared/model/phase.model';

export interface IResultData {
  id?: number;
  ovservation?: any;
  date?: Moment;
  file?: IFile;
  phase?: IPhase;
}

export class ResultData implements IResultData {
  constructor(public id?: number, public ovservation?: any, public date?: Moment, public file?: IFile, public phase?: IPhase) {}
}
