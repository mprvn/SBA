import { Component, OnInit } from '@angular/core';
import { Task } from 'src/app/model/task';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/service/task.service';
import * as  moment from 'moment';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit.task.component.html',
  styleUrls: ['./edit.task.component.scss']
})
export class EditTaskComponent implements OnInit {
  task: Task;
  today: any;
  parentId: number;
  parentName: string;
  errorMsg: any;
  startDt: Date;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService) {
    this.task = new Task();
    this.today = moment().format('YYYY-MM-DD');
  }

  ngOnInit() {
    const taskId = this.route.snapshot.paramMap.get('id');
    this.taskService.getTask(taskId).then(value => {
      this.task = value;
      this.startDt = this.task.startDate;
      if (!this.task.parentTask) {
        this.parentId = this.task.parentTask.id;
        this.parentName = this.task.parentTask.task;
      }
    });
  }


  onSubmit() {
    if (!this.validateForm()) {
      return false;
    }

    this.taskService.updateTask(this.task.id, this.task)
      .then(
        value => {
          this.router.navigate(['./viewtask']);
        }
      );
  }

  

  onCancel() {
    this.router.navigate(['./viewtask']);
  }

  public validateForm() {
    const t = new Date();
    const today = new Date(t.getFullYear(), t.getMonth(), t.getDate() );
    const tmpEndDate = this.task.endDate == null ? undefined : this.task.endDate;
    const endDate = new Date(tmpEndDate);
    const startDate = new Date(this.task.startDate);
    const tmpStartDt = new Date(this.startDt);
    const taskName = this.task.task;
    let formattedDate;

    if (!taskName || taskName.trim().length < 1) {
      this.errorMsg = `Task name is mandatory`;
      return false;
    }
    if (!this.task.startDate || (!this.task.startDate)) {
      this.errorMsg = `Start Date is mandatory`;
      return false;
    }

    if (endDate < today) {
      formattedDate = this.formatDate(today);
      this.errorMsg = `End Date should be ${formattedDate} or in the future`;
      return false;
    }

    if (startDate < tmpStartDt) {
      formattedDate = this.formatDate(tmpStartDt);
      this.errorMsg = `Start Date should be ${formattedDate} or in the future`;
      return false;
    }
    if (endDate < startDate) {
      formattedDate = this.formatDate(startDate);
      this.errorMsg = `End Date should be greater than start date: ${formattedDate}`;
      return false;
    }
    return true;
  }

  public formatDate(date: any) {
    return moment(date).format('DD-MM-YYYY');
  }
}
