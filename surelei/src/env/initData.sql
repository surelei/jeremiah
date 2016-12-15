INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(1,'root_menu','root_menu',1,0,99,'','/1/','/root_menu/',NULL);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(2,'system_config','系统设置',2,100,99,'','/1/2/','/root_menu/system_config/',1);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(3,'menu_config','菜单设置',3,100,0,'/menu/list.htm','/1/2/3/','/root_menu/system_config/menu_config/',2);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(4,'org_config','组织结构设置',3,200,0,'/org/list.htm','/1/2/4/','/root_menu/system_config/org_config/',2);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(5,'role_config','角色设置',3,300,0,'/role/list.htm','/1/2/5/','/root_menu/system_config/role_config',2);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(6,'user_config','用户管理',3,400,0,'/user/list.htm','/1/2/6/','/root_menu/system_config/user_config/',2);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(7,'res_config','资源管理',3,500,0,'/resource/list.htm','/1/2/7/','/root_menu/system_config/res_config',2);
INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID)
VALUES(8,'user_logout','退出系统',2,99999,1,'/logout','/1/8/','/root_menu/user_logout',1);
INSERT INTO T_RES_INFO(RSI_ID,RSI_NAME,RSI_URL) VALUES(1,'所有资源','/**');
INSERT INTO T_ROLE_INFO(RI_ID,RI_NAME,RI_DESC) VALUE(1,'超级管理员角色','拥有所有访问权限');
INSERT INTO T_ORG_INFO(OI_ID,OI_NAME,OI_LEVEL,OI_ORDER,OI_DESC,OI_ID_PATH,OI_NAME_PATH,OI_PARENT_ID) 
VALUES(1,'root_org',1,0,'组织结构根节点','/1/','/root_org/',NULL);
INSERT INTO T_USER_INFO(UI_ID,UI_NAME,UI_PASS,UI_FIRST_NAME,UI_LAST_NAME,OI_ID) VALUES(1,'admin','admin','super','admin',1);
commit;