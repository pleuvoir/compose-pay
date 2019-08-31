alter table pub_role_permission
  add constraint role_permission_only unique (role_id, permission_id);
