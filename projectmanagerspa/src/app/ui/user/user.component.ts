import { Component, OnInit } from '@angular/core';
import { User } from "src/app/model/user";
import { UserService } from "src/app/service/user.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  user: User;
  users = [] as User[];
  filteredUsers = [] as User[];
  errorMsg: any;
  isUpdate: boolean;
  searchFilter: string;
  constructor(private userService: UserService) {
    this.user = new User();
  }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getAllUsers().then(value => {
      this.users = value;
      this.filteredUsers = value;
    });
  }

  update(u: User): void {
    this.isUpdate = true;
    this.user.employeeId = u.employeeId;
    this.user.firstName = u.firstName;
    this.user.lastName = u.lastName;
  }

  delete(u: User): void {
    this.userService.deleteUser(u.employeeId)
      .then(
      value => {
        this.getUsers();
      }
      );
  }

  search(): void {
    let search = this.searchFilter;
    if (search) {
      this.filteredUsers = this.filteredUsers.filter(function (user: User) {
        return user.firstName.indexOf(search) >= 0;
      });
    } else {
      this.filteredUsers = this.users;
    }
  }

  onSubmit() {
    if (!this.validateForm()) {
      return false;
    }
    this.errorMsg = '';
    this.userService.addUser(this.user).then(
      value => {
        this.getUsers();
        this.emptyFields();
      }
    );
  }

  onUpdate() {
    if (!this.validateForm()) {
      return false;
    }
    this.errorMsg = '';
    this.userService.updateUser(this.user).then(
      value => {
        this.getUsers();
      }
    );
    this.emptyFields();
    this.isUpdate = false;
  }

  emptyFields() {
    this.user.employeeId = undefined;
    this.user.firstName = '';
    this.user.lastName = '';
  }

  public validateForm() {
    const firstName = this.user.firstName;
    const lastName = this.user.lastName;
    const empId = this.user.employeeId;

    if (!firstName) {
      this.errorMsg = `FirstName is mandatory`;
      return false;
    }
    if (!this.user.lastName) {
      this.errorMsg = `LastName is mandatory`;
      return false;
    }
    if (!empId) {
      this.errorMsg = `Employee Id is mandatory`;
      return false;
    }

    return true;
  }

  public reset() {
    this.errorMsg = '';
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  sortFirstName() {
    this.filteredUsers = this.users.sort((user1, user2) => {
      if (user1.firstName > user2.firstName) {
        return -1;
      }
      if (user2.firstName > user1.firstName) {
        return 1;
      }
      return 0;
    });
  }

  sortLastName() {
    this.filteredUsers = this.users.sort((user1, user2) => {
      if (user1.lastName > user2.lastName) {
        return -1;
      }
      if (user2.lastName > user1.lastName) {
        return 1;
      }
      return 0;
    });
  }

  sortEmpId() {
    this.filteredUsers = this.users.sort((user1, user2) => {
      if (user1.employeeId > user2.employeeId) {
        return -1;
      }
      if (user2.employeeId > user1.employeeId) {
        return 1;
      }
      return 0;
    });
  }

  resetFilter() {
    this.getUsers();
  }

}
