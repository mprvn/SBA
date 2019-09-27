import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { Task } from 'src/app/model/task';
import { ParentTask } from 'src/app/model/parent-task';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/service/task.service';
import { Validator } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-task',
  templateUrl: './add.task.component.html',
  styleUrls: ['./add.task.component.scss']
})
export class AddTaskComponent implements OnInit {
  @Output() tasks;
  @ViewChild('parentSelect', {static: false}) parentSelect;
  task: Task;
  parentTask: ParentTask;
  parentId: number;
  parentTaskName: String;
  parentName: string;
  parentTaskList = [] as ParentTask[];
  projectName: string;
  parentmap = [];
  errorMsg: any;
  isParentTask: boolean;
  today = new Date();
  todayPlusOne = new Date().setDate(this.today.getDate() + 1);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService,
    private datePipe: DatePipe) {

    this.task = new Task();
    this.parentTask = new ParentTask();
    this.task.priority = 1;
    this.isParentTask = false;
    this.task.startDate = this.formatDate(this.today);
    this.task.endDate = this.formatDate(this.todayPlusOne);
  }

  parentSelection(parentID) {
    this.parentTaskList.filter(parent => {
      if (parent.id == this.parentId) {
        this.parentName = parent.task;
      }
    });
  }

  ngOnInit() {
    this.loadParents();
  }
  formatDate(date: any) {
    return <any>this.datePipe.transform(date, 'yyyy-MM-dd');
  }

  loadParents() {
    this.taskService.getAllParentTasks().then(
      value => this.parentTaskList = value
    );
  }

  onSubmit() {
    this.errorMsg = '';
    if (!this.validateForm()) {
      return false;
    }
    if (this.isParentTask) {
      const parentTaskConst = new ParentTask();
      parentTaskConst.task = this.task.task;
      this.parentTask = parentTaskConst;
      this.taskService.addParentTask(this.parentTask).then(
        value => { this.router.navigate(['./viewtask']); }
      );
    } else {
      this.setParentTask();
      this.resetParentTask(this.task.parentTask);
      this.taskService.addTask(this.task).then(
        value => {
          this.router.navigate(['./viewtask']);
        }
      );
      //Default setting for Start date
      this.task.startDate = this.formatDate(this.today);
    }
    this.errorMsg = '';
  }

  setParentTask() {
    const ptask = new Task();
    ptask.id = this.parentId;
    ptask.task = this.parentName;
    this.task.parentTask = ptask;
  }

  resetParentTask(parentTask: ParentTask) {
    if (parentTask && !parentTask.id) {
      this.parentId = this.parentSelect.nativeElement.value;
      this.parentSelection(this.parentId);
      if (!this.task.priority) {
        this.task.priority = 0;
      }
      this.setParentTask();
    }
  }
  changeChkBox(event) {
    if (event.target.checked) {
      this.isParentTask = true;
      this.task.startDate = undefined;
      this.task.endDate = undefined;
      this.task.priority = 0;
    } else {
      this.isParentTask = false;
      this.task.startDate = this.formatDate(this.today);
      this.task.endDate = this.formatDate(this.todayPlusOne);
    }
  }

  public validateForm() {
    const t = new Date();
    const today = new Date(t.getFullYear(), t.getMonth(), t.getDate());
    const endDate = new Date(this.task.endDate);
    const startDate = new Date(this.task.startDate);
    let formattedDate = this.formatDate(today);
    if ((!this.task.task)) {
      this.errorMsg = `Task name is mandatory`;
      return false;
    }
    if (!this.isParentTask) {
      if (!this.task.startDate) {
        this.errorMsg = `Start Date is mandatory`;
        return false;
      }

      if (startDate < today) {
        this.errorMsg = `Start should be ${formattedDate} or in the future`;
        return false;
      }

      if (endDate < today) {
        this.errorMsg = `End Date should be ${formattedDate} or in the future`;
        return false;
      }

      if (endDate < startDate) {
        formattedDate = this.formatDate(startDate);
        this.errorMsg = `End Date should be greater than start date: ${formattedDate}`;
        return false;
      }
    }

    return true;
  }
}
