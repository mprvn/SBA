import { ParentTask } from './parent-task';
import { Project } from "src/app/model/project";

export class Task {
  id: number;
  task: string;
  priority: number;
  startDate: Date;
  endDate: Date;
  parentTask: ParentTask;
  project: Project;
  projId: number;
  userId: number;
  status: boolean;
}
