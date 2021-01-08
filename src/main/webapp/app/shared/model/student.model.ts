import { IUser } from 'app/core/user/user.model';
import { IFaculty } from 'app/shared/model/faculty.model';
import { IProject } from 'app/shared/model/project.model';
import { IBatch } from 'app/shared/model/batch.model';

export interface IStudent {
  id?: number;
  index?: string;
  fullNameArabic?: string;
  phone?: string;
  user?: IUser;
  faculty?: IFaculty;
  project?: IProject;
  batch?: IBatch;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public index?: string,
    public fullNameArabic?: string,
    public phone?: string,
    public user?: IUser,
    public faculty?: IFaculty,
    public project?: IProject,
    public batch?: IBatch
  ) {}
}
