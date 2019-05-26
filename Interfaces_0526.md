#### 团队子任务指定

/distributeTask

![1558850271011](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558850271011.png)

![1558850332894](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558850332894.png)

成功返回1，数据库插入失败返回-1，成员不属于团队项目返回-2

#### 查看当前子任务和成员分配情况

/getDistributeTask

![1558850791364](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558850791364.png)

![1558850814537](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558850814537.png)

#### 获取今天的团队子任务

/getTodaysTasks

![1558851639898](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558851639898.png)

![1558851654521](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558851654521.png)

返回进行中的团队子任务，或恰好今天已完成的团队子任务

#### 获取所有的团队子任务

/getAllTasks

![1558851869627](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558851869627.png)

#### 推迟团队子任务

/delayProjectTask

![1558852249570](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558852249570.png)

推迟状态为6

#### 获取团队子任务数据

/getAnalyzeData

![1558852516023](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558852516023.png)

![1558852504936](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558852504936.png)

#### 新建团队子任务

/addSubTasks

![1558853006767](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558853006767.png)

#### 设置前置任务

/setPreTask

![1558854512848](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558854512848.png)

成功返回1，数据库更新失败返回-1，已为前置任务返回-2，前置任务不存在返回-3

#### 查找当前任务的前置任务

/findPreTask

![1558855295785](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558855295785.png)

#### 查找以当前任务为前置任务的子任务

/findPostTask

![1558855773163](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558855773163.png)

#### 获取完成度

/getProjectProgress

![1558857219229](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558857219229.png)

![1558857236384](C:\Users\MY-T\AppData\Roaming\Typora\typora-user-images\1558857236384.png)

