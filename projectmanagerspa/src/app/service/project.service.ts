import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Project } from '../model/project';


@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  http: HttpClient;
  projectHttpUrl: string = environment.apiUrl + '/projects/';

   constructor(http: HttpClient) {
    this.http = http;
   }

  getAllProjects(): Promise<any> {
    return this.http.get<Project>(this.projectHttpUrl).toPromise().then(value => value);
  }

  getAllProjectsWithTasks(): Promise<any> {
    return this.http.get<Project>(this.projectHttpUrl + 'tasks/').toPromise().then(value => value);
  }

  getProject(id: number): Promise<any> {
    return this.http.get<Project>(this.projectHttpUrl + '' + id).toPromise().then(value => value);
  }

  addProject(p: Project): Promise<any> {
    return this.http.post(this.projectHttpUrl, p).toPromise().then(value => value);
  }

  updateProject(p: Project): Promise<any> {
    return this.http.put(this.projectHttpUrl, p).toPromise().then(value => value);
  }

}
