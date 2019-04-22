# 用户操作

## 注册

![1555934019981](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555934019981.png)

1 -- 注册成功，2 -- 用户名已存在，3 -- 注册失败，重试

## 登录

使用token进行登录，密码验证成功之后返回token，登录之后的所有操作在发送请求时**都要带上token**，验证token正确后才可以进行其他操作

1. **通过用户名获取用户id**

![1555936025008](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555936025008.png)

1 -- 用户不存在，其他值 -- 用户id

2. **通过用户id、用户名和密码获取token**

![1555936098556](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555936098556.png)

空值 -- 用户信息错误，其他值 -- token

3. **使用用户id、token登录**

![1555936129204](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555936129204.png)

1 -- 登陆成功，2 -- token错误

## 查找

### 通过用户名查找用户

![1555934207517](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555934207517.png)

空值 -- 用户不存在，其他值 -- 用户信息

### 通过邮箱查找用户

![1555934263954](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555934263954.png)

空值 -- 用户不存在，其他值 -- 用户信息

### 通过用户名查找用户id

![1555934321916](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555934321916.png)

-1 -- 用户不存在，其他值 -- 用户id

## 删除

![1555934396714](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555934396714.png)

1 -- 删除成功，2 -- 删除失败

## 验证Token

![1555936172930](C:\Users\Teng_MY\AppData\Roaming\Typora\typora-user-images\1555936172930.png)

-1 -- token不存在即用户处于未登录状态，其他值 -- 用户id