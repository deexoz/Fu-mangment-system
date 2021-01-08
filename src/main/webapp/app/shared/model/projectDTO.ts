import { IProject } from './project.model';
import { IStudent } from './student.model';

export class ProjectDTO {
  constructor(public project?: IProject, public students?: IStudent[]) {}
}
