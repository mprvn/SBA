INSERT INTO parent_task (parent_id,parent_task) VALUES (1,"parent task1");
INSERT INTO parent_task (parent_id,parent_task) VALUES (2,"parent task2");
INSERT INTO parent_task (parent_id,parent_task) VALUES (3,"parent task3");
INSERT INTO parent_task (parent_id,parent_task) VALUES (4,"parent task4");


INSERT INTO project(id,end_date,priority,project,start_date) VALUES(1, CURDATE(), 10, "Project 1", CURDATE());
INSERT INTO project(id,end_date,priority,project,start_date) VALUES(2, CURDATE(), 10, "Project 2", CURDATE());
INSERT INTO project(id,end_date,priority,project,start_date) VALUES(3, CURDATE(), 10, "Project 3", CURDATE());
INSERT INTO project(id,end_date,priority,project,start_date) VALUES(4, CURDATE(), 10, "Project 4", CURDATE());
INSERT INTO project(id,end_date,priority,project,start_date) VALUES(5, CURDATE(), 10, "Project 5", CURDATE());
INSERT INTO project(id,end_date,priority,project,start_date) VALUES(6, CURDATE(), 10, "Project 6", CURDATE());



INSERT INTO task(end_date,priority,start_date,task,parent_task_id, status, project_id )VALUES(CURDATE() ,10,CURDATE() ,"task 1",1, false, 1);
INSERT INTO task(end_date,priority,start_date,task,parent_task_id, status, project_id)VALUES(CURDATE() ,11,CURDATE() ,"task 2",1, false, 2);
INSERT INTO task(end_date,priority,start_date,task,parent_task_id, status, project_id)VALUES(CURDATE() ,12,CURDATE() ,"task 3",1,true, 1);
INSERT INTO task(end_date,priority,start_date,task,parent_task_id, status, project_id)VALUES(CURDATE() ,13,CURDATE() ,"task 4",1,true, 1);



INSERT INTO users (employee_id,first_name,last_name,project_id,task_id)VALUES(101,"sam", "sam1",1,1);
INSERT INTO users (employee_id,first_name,last_name,project_id,task_id)VALUES(102,"sam1", "sam1",2,2);
INSERT INTO users (employee_id,first_name,last_name,project_id,task_id)VALUES(103,"sam2", "sam1",1,2);
INSERT INTO users (employee_id,first_name,last_name,project_id,task_id)VALUES(104,"sam1", "sam1",3,3);


