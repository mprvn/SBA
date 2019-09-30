import { Component, OnInit } from '@angular/core';
import { Task } from 'src/app/model/task';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/service/task.service';
import * as moment from 'moment';
import { Project } from "src/app/model/project";
import { ProjectService } from "src/app/service/project.service";

@Component({
  selector: 'app-view-task',
  templateUrl: './view.task.component.html',
  styleUrls: ['./view.task.component.scss']
})
export class ViewTaskComponent implements OnInit {
  tasks: Task[];
  filteredTasks: Task[];
  parentFilterSet: boolean = false;
  currentDate: Date;
  projects: Project[];
  projectName: string;
  projectId: number;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService,
    private projectService: ProjectService) {
    this.getTasks();
  }

  ngOnInit() {
  }

  getTasks(): void {
    this.taskService.getAllTasks().then(value => {
      this.tasks = value;
      this.filteredTasks = value;
    });
  }


  update(t: Task): void {
    this.router.navigate(['/edittask', t.id]);
  }

  delete(t: Task): void {
    this.taskService.deleteTask(t.id, t)
      .then(
      value => {
        this.getTasks();
      }
      );
  }

  isTaskActive(t: Task): boolean {
    return moment(t.startDate).isSameOrBefore(moment())
      && (t.endDate == null || moment(t.endDate).isAfter(moment()));
  }

  isTaskExpired(t: Task): boolean {
    return moment(t.endDate).isBefore(moment());
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



  loadProjects(): void {
    this.projectService.getAllProjects().then(value => {
      this.projects = value;
    });
  }
  getTaskByProject(id: number): void {
    this.taskService.getTaskByProject(id).then(task => {
      this.filteredTasks = task;
    });
  }

  onProjectSelected() {

    this.projects = this.projects.filter(project => {
      if (project.id == this.projectId) {
        this.projectName = project.project;
        this.parentFilterSet = true;
      }
    });
    this.getTaskByProject(this.projectId);
  }

  sortByStartDate() {
    if (this.parentFilterSet) {
      this.filteredTasks = this.filteredTasks.sort((a: any, b: any) =>
        new Date(a.startDate).getTime() - new Date(b.startDate).getTime()
      );
    } else {
      this.filteredTasks = this.tasks.sort((a: any, b: any) =>
        new Date(a.startDate).getTime() - new Date(b.startDate).getTime()
      );
    }
  }
  sortByEndDate() {
    if (this.parentFilterSet) {
      this.filteredTasks = this.filteredTasks.sort((a: any, b: any) =>
        new Date(a.endDate).getTime() - new Date(b.endDate).getTime()
      );
    } else {
      this.filteredTasks = this.tasks.sort((a: any, b: any) =>
        new Date(a.endDate).getTime() - new Date(b.endDate).getTime()
      );
    }
  }
  sortByPriority() {
    if (this.parentFilterSet) {
      this.filteredTasks = this.filteredTasks.sort((a: any, b: any) => b.priority - a.priority);
    } else {
      this.filteredTasks = this.tasks.sort((a: any, b: any) => b.priority - a.priority);
    }

  }
  sortByCompleted() {
    if (this.parentFilterSet) {
      this.filteredTasks = this.filteredTasks.filter(t => this.isTaskExpired(t));
    } else {
      this.filteredTasks = this.tasks.filter(t => this.isTaskExpired(t));
    }
    this.filteredTasks = this.filteredTasks.sort((a: any, b: any) =>
      new Date(b.endDate).getTime() - new Date(a.endDate).getTime()
    );
  }

  
  filterReset() {
    this.getTasks();
  }
}
