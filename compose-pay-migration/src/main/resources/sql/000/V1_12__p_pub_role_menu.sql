alter table pub_role_menu
  add constraint role_menu_only unique (role_id, menu_id);