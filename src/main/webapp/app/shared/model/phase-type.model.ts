import { IPhase } from 'app/shared/model/phase.model';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IPhaseType {
  id?: number;
  title?: string;
  description?: any;
  phaseType?: AnnouncementType;
  phase?: IPhase;
}

export class PhaseType implements IPhaseType {
  constructor(
    public id?: number,
    public title?: string,
    public description?: any,
    public phaseType?: AnnouncementType,
    public phase?: IPhase
  ) {}
}
