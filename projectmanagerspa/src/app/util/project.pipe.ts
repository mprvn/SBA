import { Pipe, PipeTransform, Injectable } from '@angular/core';
import { Project } from '../model/project';

@Pipe({
  name: 'projectFilter'
})

@Injectable()
export class ProjectPipe implements PipeTransform {

  transform(projects: Array<Project>, filterByProject?: string) {
    if (filterByProject) {
      projects = projects.filter(project =>
         project.project.toUpperCase().startsWith(filterByProject.toUpperCase()));
    }
    return projects;
  }

}
