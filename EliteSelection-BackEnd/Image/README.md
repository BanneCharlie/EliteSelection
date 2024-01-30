# 1.19 (后台系统 前后端的搭建以及登录模块的实现)
*前端系统架构 :*
![img.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img.png)
*后端系统架构 :*
![img.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_1.png)
*统一异常处理 :*

![img_5.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_5.png) 

`用户登录的流程图 :`
![img_2.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_2.png)

`图片验证码实现流程图 :`
![img_4.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_4.png)
`获取用户信息 :`通过登录时的token,从Redis中获取对应的用户信息(用户登录时将token作为key值 用户信息为value值存放入redis中)

`用户退出信息 :`后端根据token删除用户数据 

`登录校验信息 :`设置拦截器,配置拦截器的注册和需要拦截的url
![img_6.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_6.png)

**用户登录模块出现的问题 :**

* EliteSelection-manager
- mapper/SysUserManager.xml 无法通过.xml执行sql语句实现持久化,暂时使用注解来实现持久化
  - 出现问题为spring整合mybatis的相关配置错位
  - 解决方法
  ![img_7.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_7.png)
- SysUserServiceImp类中的login()中无法通过key值获取redis中的数据,进行校验;
  - 解决方法为注入RedisTemplate时设置它的key 与 value的泛型(String)
# 1.25(权限模块的实现)
## 角色管理(BUG)
*功能划分为 :*
- 角色的展示(分页展示 通过pagehelper插件实现)

- 角色的添加

- 角色的修改

- 角色的删除

  **角色管理模块出现的问题 :**

- pagehelper 插件没有实现分页功能
`pagehelper 实现分页的流程`
![img_8.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_8.png)
## 用户管理
- 用户的展示(分页展示 通过pagehelper插件实现)

- 用户的添加

- 用户的修改

- 用户的删除

- 用户头像的上传  
  通过搭建Minio(开源的分布式对象存储服务器)来保存图片
  ![img_10.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_10.png)

  ![img_9.png](https://banne.oss-cn-shanghai.aliyuncs.com/Java/img_9.png) 

  **用户管理模块出现的问题 :**

- 在linux中部署的 minio 无法实现图片的上传  (暂定)
  - 目前通过使用windows下部署minio进行图片的上传 
  
- 在修改用户头像的时候无法实现修改,出现报错 ERR_CONNECTION_REST

  - 上传的图片太大 无法传输出现的报错,放一些小一点的文件即可; 通过更改MinionClient的其他方法可以解决

- 用户角色的分配
## 菜单管理
- 菜单的展示(菜单具有层级关系,通过递归对数据库获取的所有菜单信息进行封装,转化为前端需要的数据类型 通过递归实现)
- 添加菜单
- 删除菜单
- 修改菜单
- 角色菜单的分配
- 前端动态展示用户的角色拥有的菜单
  **菜单管理模块出现的问题 :**
- 角色分配菜单时,选中全部子菜单;当为菜单添加新的功能时 新加入的菜单也会被自动选入
# 1.29(商品模块的实现)
## 分类管理
- 分类的展示(通过懒模式仅展示第一层级的数据)
- EasyExcel实现数据的导入和导出
  - 导入相关依赖
  - 创建于excel表格对应的类 (CategoryExcelVo)
  - 实现数据的导出(下载到处的文件)
  - 实现数据的导入
    - 主要是通过实现AnalysisEventListener<T>接口 
    - 获取从文件读取的数据,存入数据库中(官方网址: https://easyexcel.opensource.alibaba.com/)
## 品牌管理
- 品牌的查询
- 品牌的添加
- 品牌的修改
- 品牌的删除