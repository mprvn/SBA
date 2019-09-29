import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddTaskComponent } from "src/app/ui/task/add/add.task.component";
import { EditTaskComponent } from "src/app/ui/task/edit/edit.task.component";
import { ViewTaskComponent } from "src/app/ui/task/view/view.task.component";
import { UserComponent } from "src/app/ui/user/user.component";
import { ProjectComponent } from "src/app/ui/project/project.component";


const routes: Routes = [
  {
    path: '',
    redirectTo: '/viewtask',
    pathMatch: 'full'
  },
  {
    path: 'addtask',
    component: AddTaskComponent
  },
  {
    path: 'edittask/:id',
    component: EditTaskComponent
  },
  {
    path: 'project',
    component: ProjectComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'viewtask',
    component: ViewTaskComponent
  }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
