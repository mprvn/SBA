import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ParentTask } from '../model/parent-task';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  taskHttpUrl: string = environment.apiUrl + '/tasks/';
  ptaskHttpUrl: string = environment.apiUrl + '/ptasks/';

  constructor(private https: HttpClient) { }

  getAllParentTasks(): Promise<any> {
    return this.https.get<ParentTask>(this.ptaskHttpUrl).toPromise().then(value => value);
  }

  getAllTasks(): Promise<any> {
    return this.https.get<Task>(this.taskHttpUrl).toPromise().then(value => value);
  }

  getTask(id: string): Promise<any> {
    return this.https.get(this.taskHttpUrl + '' + id).toPromise().then(value => value);
  }

  getTaskByProject(id: number): Promise<any> {
    return this.https.get<Task>(this.taskHttpUrl + 'projects/' + '' + id).toPromise().then(value => value);
  }

  addTask(t: Task): Promise<any> {
    return this.https.post(this.taskHttpUrl, t).toPromise().then(value => value);
  }

  addParentTask(t: ParentTask): Promise<any> {
    return this.https.post(this.ptaskHttpUrl, t).toPromise().then(value => value);
  }

  updateTask(id: number, t: Task): Promise<any> {
    return this.https.put(this.taskHttpUrl, t).toPromise().then(value => value);
  }

  deleteTask(id: number, t: Task): Promise<any> {
    return this.https.delete(this.taskHttpUrl + '' + id).toPromise().then(value => value);
  }

}
