insert into member (member_id, member_password, member_name, birth, created_at, modified_at)
            value ('user', '{noop}12', '유저', now(), now(), now());
insert into member (member_id, member_password, member_name, birth, created_at, modified_at)
            value ('admin', '{noop}12', '어드민', now(), now(), now());
insert into member (member_id, member_password, member_name, birth, created_at, modified_at)
            value ('superadmin', '{noop}12', '슈퍼어드민', now(), now(), now());

insert into member_role (member_id, `role`)
            value ('user', 'ROLE_USER');
insert into member_role (member_id, `role`)
            value ('admin', 'ROLE_ADMIN');
insert into member_role (member_id, `role`)
            value ('superadmin', 'ROLE_SUPERADMIN');