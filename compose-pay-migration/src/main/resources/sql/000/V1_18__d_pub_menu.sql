
insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U000', 'Y', null, '0', null, null, 0, '运管顶级菜单', '1', null);



insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U001', 'Y', 'fa fa-stumbleupon', '1', 'U000', null, 1, '通道管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00101', 'N', null, '2', 'U001', '/channel/list', 1, '通道信息', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U002', 'Y', 'fa fa-user-plus', '1', 'U000', null, 2, '商户管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00201', 'N', null, '2', 'U002', '/merchant/list', 1, '商户信息', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00202', 'N', null, '2', 'U002', '/merchantSecret/list', 2, '商户密钥', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U003', 'Y', 'fa fa-cny', '1', 'U000', null, 3, '订单管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00301', 'N', null, '2', 'U003', '/merpay/list', 1, '支付订单', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U008', 'Y', 'fa fa-sun-o', '1', 'U000', null, 8, '系统管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00802', 'N', null, '2', 'U008', '/pubParam/list', 1, '参数管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00803', 'N', null, '2', 'U008', '/pubMenu/list', 2, '菜单管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00805', 'N', null, '2', 'U008', '/pubUser/list', 4, '用户管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00806', 'N', null, '2', 'U008', '/pubRole/list', 5, '角色管理', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00808', 'N', null, '2', 'U008', '/pubLoginLog/list', 7, '登录日志', '1', null);

insert into PUB_MENU (ID, HAS_CHILD, ICON, NODE, PARENT_ID, PATH, SORT, TITLE, IS_SHOW, REMARK)
values ('U00809', 'N', null, '2', 'U008', '/pubOperationLog/list', 8, '操作日志', '1', null);






