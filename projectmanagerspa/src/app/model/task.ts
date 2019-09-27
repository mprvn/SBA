import { ParentTask } from './parent-task';

export class Task {
  id: number;
  task: string;
  priority: number;
  startDate: Date;
  endDate: Date;
  parentTask: ParentTask;
  projId: number;
  status: boolean;
}
