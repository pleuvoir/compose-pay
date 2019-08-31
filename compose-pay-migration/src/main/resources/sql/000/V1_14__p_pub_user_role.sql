alter table pub_user_role
  add constraint user_role_only unique (user_id, role_id);
