
    create table courses (
        id bigserial not null,
        name varchar(255),
        primary key (id)
    );

    create table groups (
        id bigserial not null,
        name varchar(255),
        primary key (id)
    );

    create table students (
        group_id bigint not null,
        id bigserial not null,
        name varchar(255),
        primary key (id)
    );

    create table student_course (
        course_id bigint not null,
        student_id bigint not null,
        primary key (course_id, student_id)
    );

    alter table if exists students
       add constraint FKd2slqqxxl5g1axj5th1nqukq7 
       foreign key (group_id) 
       references groups;

    alter table if exists student_course 
       add constraint FKejrkh4gv8iqgmspsanaji90ws 
       foreign key (course_id) 
       references courses;

    alter table if exists student_course 
       add constraint FKq7yw2wg9wlt2cnj480hcdn6dq 
       foreign key (student_id) 
       references students;
