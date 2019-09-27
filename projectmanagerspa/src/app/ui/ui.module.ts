import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { LayoutComponent } from "src/app/ui/layout/layout.component";
import { HeaderComponent } from "src/app/ui/header/header.component";
import { FooterComponent } from "src/app/ui/footer/footer.component";
import { AddTaskComponent } from "src/app/ui/task/add/add.task.component";
import { ViewTaskComponent } from "src/app/ui/task/view/view.task.component";
import { EditTaskComponent } from "src/app/ui/task/edit/edit.task.component";
import { AppRoutingModule } from "src/app/app-routing.module";
import { FormsModule, FormBuilder } from "@angular/forms";
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { UserComponent } from './user/user.component';
import { FilterTasksPipe } from "src/app/util/filter-tasks.pipe";


@NgModule({
  declarations: [
    LayoutComponent,
    HeaderComponent,
    FooterComponent,
    AddTaskComponent,
    ViewTaskComponent,
    EditTaskComponent,
    UserComponent,
    FilterTasksPipe
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    DatePipe, FormBuilder
  ],
  exports: [
    LayoutComponent
]
})
export class UiModule { }
