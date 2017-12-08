-- 【HAC】【对接文档】数据库脚本

-- Author : zhenzhong.wang@hypers.com
-- Date : ${date}

-----------
-- 本文档以${appName}接入${envName}环境为例，涉及的表如下：
-- app, app_env_rel, app_group_rel, entity config, system_config
-----------

-- 查询全部超级管理员
select u.*
from user_role_rel rel left join user u on rel.user_id=u.id
where rel.role_id=1;


-- 新增应用
INSERT INTO `hac_account`.`app` (`id`, `name`, `short_name`, `code`, `description`, `status`, `creator`, `create_time`, `update_time`) VALUES ('${appId}', '${appName}', '${appShortName}', '${appCode}', '${appDescriptionCn}', '1', '115', '2017-11-07 15:43:05', '2017-11-07 15:43:05');

-- 将应用接入某个环境（如saas、vivaki、arvato）
INSERT INTO `app_env_rel` (`name`, `app_id`, `env_id`, `description`, `status`, `ssl_status`, `app_url`, `authorize_url`, `logo_large_url`, `logo_normal_url`, `logo_small_url`, `theme`, `copyright_info`, `support_email`, `creator`, `create_time`, `update_time`) VALUES ('${envName} ${appName}', ${appId}, ${envId}, '${appDescriptionEn}', 1, 1, 'https://${appUrl}', 'https://${appUrl}', 'https://${envUrl}/static/dist/resources/img/apps/100x100/CRM.png', 'https://${envUrl}/static/dist/resources/img/apps/42x32/CRM.png', NULL, '${appTheme}', NULL, NULL, 115, '2017-11-07 15:43:05', '2017-11-07 15:43:05');

-- 配置应用其他信息
INSERT INTO `entity_config` (`entity`, `entity_id`, `key`, `value`, `description`) VALUES ('app', '${appId}', 'description_cn', '${appDescriptionCn}', NULL);
INSERT INTO `entity_config` (`entity`, `entity_id`, `key`, `value`, `description`) VALUES ('app', '${appId}', 'description_en', '${appDescriptionEn}', NULL);
INSERT INTO `entity_config` (`entity`, `entity_id`, `key`, `value`, `description`) VALUES ('app', '${appId}', 'status_sync_api', 'PATCH,/api/sync/status', NULL);

-- 更新环境app_order，此处配置后应用才会在首页显示
UPDATE `hac_account`.`system_config` SET `value`='${envAppOrder},${appOrder}' WHERE  `host`='${envUrl}' and key='app_order';

-- 授权应用给用户
INSERT INTO `hac_account`.`app_user_rel` (`app_id`, `user_id`, `status`, `email_notify`) VALUES ('${appId}', '115', '1', '0');
INSERT INTO `hac_account`.`app_user_rel` (`app_id`, `user_id`, `status`, `email_notify`) VALUES ('${appId}', '118', '1', '0');
INSERT INTO `hac_account`.`app_user_rel` (`app_id`, `user_id`, `status`, `email_notify`) VALUES ('${appId}', '130', '1', '0');


-- 部分字段说明：

-- app表:
-- name 应用在Account中的显示名称
-- short_name 应用缩写，应用在九宫格中的显示名称
-- code 队列中的应用标识，如网站监测为hwa
-- description 中文描述

-- app_env_rel表:
-- name    应用在不同环境下的显示名称，如SaaS HWA、Vivaki HFA等
-- description    Account中区分环境位置的app显示名称，如Hyper Web Analytics、PM Advertiser等
-- app_url    app跳转url
-- authorize_url    授权应用浮层跳转的url，通常是应用默认首页
-- logo大中小url，应用不同规格的logo url，目前Account中只用到了大、中两个
-- theme 应用主题颜色
-- copyright_info 可以为空
-- support_email 可以为空

-- user表
-- 同步需要授权应用的用户