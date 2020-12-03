import { IUser } from 'app/core/user/user.model';
import { IFaculty } from 'app/shared/model/faculty.model';
import { IProject } from 'app/shared/model/project.model';

export interface IStudent {
  id?: number;
  index?: string;
  fullNameArabic?: string;
  phone?: string;
  user?: IUser;
  faculty?: IFaculty;
  project?: IProject;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public index?: string,
    public fullNameArabic?: string,
    public phone?: string,
    public user?: IUser,
    public faculty?: IFaculty,
    public project?: IProject
  ) {}
}
