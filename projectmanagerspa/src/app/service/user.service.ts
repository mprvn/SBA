import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../model/user';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  http: HttpClient;
  userHttpUrl: string = environment.apiUrl + '/users/';
  constructor(http: HttpClient) {
    this.http = http;
  }

  getAllUsers(): Promise<any> {
    return this.http.get<User>(this.userHttpUrl).toPromise().then(value => value);
  }

  getUser(id: number): Promise<any> {
    return this.http.get<User>(this.userHttpUrl + '' + id).toPromise().then(value => value);
  }

  getUserByProject(id: number): Promise<any> {
    return this.http.get<User>(this.userHttpUrl + 'projects/' + '' + id).toPromise().then(value => value);
  }

  getUserByTask(id: number): Promise<any> {
    return this.http.get<User>(this.userHttpUrl + 'tasks/' + '' + id).toPromise().then(value => value);
  }

  addUser(u: User): Promise<any> {
    return this.http.post(this.userHttpUrl, u).toPromise().then(value => value);
  }

  updateUser(u: User): Promise<any> {
    return this.http.put(this.userHttpUrl, u).toPromise().then(value => value);
  }

  deleteUser(id: number): Promise<any> {
    return this.http.delete(this.userHttpUrl + '' + id).toPromise().then(value => value);
  }
}
