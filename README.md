# Guli_Project  后端

## 后台管理系统功能总结
### 1.登录功能
集成SpringSecurity
### 2.权限管理功能
+ 菜单管理
    + 展示、添加、修改、删除
+ 角色管理
    +展示、添加、修改、删除（批量删除）
    +为角色分配菜单
+ 用户管路
    + 展示、添加、修改、删除（批量删除）
    + 为用户分配角色
+ 讲师管理
    +条件查询分页列表、添加、修改、删除
+ 课程分类
    + 添加课程分类--->读取Excel里面课程分类数据，添加到数据库中
    + 课程分类列表--->使用树形结构显示课程分类列表
+ 课程管理
    + 课程列表功能（条件分页查询）
    + 添加课程

        课程发布流程：第一步填写课程基本信息，第二步添加课程大纲（章节和小节），第三步课程信息确认，最终课程发布
    + 添加小节上传课程视频
+ 流量监测
    + 统计流量
    + 监测流量使用图表

## 后台管理系统技术总结
**整个项目采用B2C模式，后端采用微服务的架构进行开发**

### SpringBoot 2.2.1

### Spring Cloud H版
![图片1.png](https://i.loli.net/2021/05/20/Q1oRcdkL5fqMHEZ.png)
项目中，使用阿里巴巴Nacos，替代SpringCloud一些组件
+ 使用Nacos作为注册中心
+ 使用Nacos作为配置中心
+ OpenFeign 服务调用，实现远程调用
+ 熔断器 Hystrix
+ Gateway 网关
### MyBatisPlus
+ 自动填充
+ 乐观锁
+ 逻辑删除
+ 代码生成器
### EasyExcel
+ EasyExcel对poi进行封装，采用SAX方式解析
+ 项目应用在添加课程分类，读取excel数据
### Spring Security
+ 在项目整合框架实现权限管理功能
+ SpringSecurity框架组成：认证和授权
+ SpringSecurity登录认证过程
![图片2.png](https://i.loli.net/2021/05/20/uZOyRk398UHjFfn.png)
### Redis
+ 首页数据通过Redis进行缓存

### Nginx
+ 反向代理服务器
+ 请求转发，负载均衡，动静分离

### OAuth2+JWT


### HttpClient
+ 发送请求返回响应的工具，不需要浏览器完成请求和响应的过程
+ 应用场景：微信登录获取扫描人信息，微信支付查询支付状态

### Cookie
+ Cookie特点：

1. 客户端技术
2. 每次发送请求带着cookie值进行发送
3. cookie有默认会话级别，关闭浏览器cookie默认不存在了，
4. 但是可以设置cookie有效时长  setMaxAge

### 微信登录

### 微信支付

### 阿里云OSS
+ 文件存储服务器
+ 添加讲师时候上传讲师头像

### 阿里云视频点播
+ 视频上传、删除、播放
+ 整合阿里云视频播放器进行视频播放
+ 使用视频播放凭证

### 阿里云短信服务
+ 注册时候，发送手机验证码

### Git
+ 代码提交到远程Git仓库

### Docker+Jenkins
+ 手动打包运行
+ idea打包
+ jenkins自动化部署过程
