create table if not exists queue_manager (
  id bigint identity,
  name varchar(100 char) not null,
  describe varchar(200 char) not null,
  ip varchar(20 char) not null,
  port int not null,
  ccsid int not null,
  channel varchar(100 char) not null
);

create table if not exists queue (
  id bigint identity,
  name varchar2(100 char) not null,
  describe varchar2(200 char) not null,
  manager_id bigint not null
);
