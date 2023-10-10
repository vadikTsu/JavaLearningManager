create table "course"
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);

create table "group"
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);

create table "student"
(
    group_id bigint    not null,
    id       bigserial not null,
    name     varchar(255),
    primary key (id)
);

create table "student_course"
(
    course_id  bigint not null,
    student_id bigint not null,
    primary key (course_id, student_id)
);

alter table if exists "student"
    add constraint FKqq8hb86jxf1vmnrcu6v7h0ac
        foreign key (group_id)
            references "group";

alter table if exists "student_course"
    add constraint FKejrkh4gv8iqgmspsanaji90ws
        foreign key (course_id)
            references "course";

alter table if exists "student_course"
    add constraint FKq7yw2wg9wlt2cnj480hcdn6dq
        foreign key (student_id)
            references "student";
