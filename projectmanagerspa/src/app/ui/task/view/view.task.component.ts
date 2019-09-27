import { Component, OnInit } from '@angular/core';
import { Task } from 'src/app/model/task';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/service/task.service';
import * as moment from 'moment';

@Component({
  selector: 'app-view-task',
  templateUrl: './view.task.component.html',
  styleUrls: ['./view.task.component.scss']
})
export class ViewTaskComponent implements OnInit {
  tasks: Task[];
  filterByName: string;
  filterByParentTask: number;
  filterByPriorityFrom: number;
  filterByPriorityTo: number;
  filterByStartDate: Date;
  filterByEndDate: Date;
  currentDate: Date;
  constructor(private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService) {
    this.getTasks();
  }

  ngOnInit() {
  }

  getTasks(): void {
    this.taskService.getAllTasks().then(value => {
      this.tasks = value;
     });
  }

  update(t: Task ): void {
    this.router.navigate(['/edittask' , t.id]);
  }

  delete(t: Task): void {
    this.taskService.deleteTask(t.id, t)
      .then(
        value => {
          this.getTasks();
        }
      );
  }

  finishTask(t: Task): void {
    t.endDate = new Date(moment.now());
    t.status = true;
    this.taskService.updateTask(t.id, t)
      .then(
        value => {
          this.getTasks();
        }
      );
  }

  isActive(task: Task): boolean {
    return moment(task.startDate).isSameOrBefore(moment())
           && (task.endDate == null || moment(task.endDate).isAfter(moment()));
  }

  isExpired(task: Task): boolean {
    return moment(task.endDate).isBefore(moment());
  }

  resetFilter() {
    this.getTasks();
  }
}
