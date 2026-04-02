# 控制台多用户聊天系统

## 项目简介

这是一个基于Java多线程实现的控制台多用户聊天系统，支持用户注册、登录和群聊功能。系统采用客户端-服务器架构，服务端使用多线程处理客户端连接，实现了实时消息转发。

## 项目结构

```
homework/
├── src/                            # 源代码目录
│   ├── chatapp/                    # 聊天应用主包
│   │   ├── client/                 # 客户端子模块
│   │   │   ├── Client.java         # 客户端入口
│   │   │   └── handler/            # 客户端消息处理
│   │   │       └── ClientMsgHandler.java  # 接收服务端转发的消息
│   │   ├── server/                 # 服务端子模块
│   │   │   ├── Server.java         # 服务端入口（监听端口+接收连接）
│   │   │   └── handler/            # 服务端客户端连接处理
│   │   │       └── ClientHandler.java     # 处理单个客户端的消息
│   │   ├── common/                 # 公共模块
│   │   │   ├── constant/           # 消息协议常量
│   │   │   │   └── MsgConstant.java       # 定义消息类型和反馈结果
│   │   │   ├── entity/             # 实体类
│   │   │   │   └── User.java               # 用户实体
│   │   │   └── util/               # 工具类
│   │   │       └── CommonUtil.java         # 格式校验、用户管理
│   ├── ui/                         # 界面层
│   │   ├── HomeUI.java             # 主界面
│   │   ├── LoginUI.java            # 登录界面
│   │   ├── RegisterUI.java         # 注册界面
│   │   ├── ChatUI.java             # 聊天界面
│   │   └── base/                   # 基础UI封装
│   │       └── BaseUI.java         # 提供通用界面方法
│   └── test/                       # 测试类
│       └── TestClient.java         # 测试客户端
├── bin/                            # 编译输出目录
├── README.md                       # 项目说明文件
├── homework.iml                    # IntelliJ IDEA项目文件
└── userinfo.txt                    # 用户信息存储文件
```

## 系统功能

1. **用户注册**：新用户可以注册账号，系统会验证用户名和密码格式，并检查用户名是否已存在
2. **用户登录**：已注册用户可以登录系统，系统会验证用户名和密码
3. **群聊功能**：登录成功后，用户可以进入群聊大厅，发送消息给所有在线用户
4. **实时消息**：系统使用多线程实现实时消息转发，确保消息及时送达

## 技术实现

### 服务端
- 使用`ServerSocket`监听端口（8888）
- 每接收到一个客户端连接，创建一个`ClientHandler`线程处理该连接
- 使用`CopyOnWriteArrayList`存储在线客户端，实现线程安全的消息转发
- 支持三种消息类型：登录、注册和聊天

### 客户端
- 使用`Socket`连接服务端
- 提供控制台界面，支持用户注册、登录和聊天操作
- 启动独立线程接收服务端转发的消息

### 消息协议
- 消息格式：`消息类型 消息内容`
- 消息类型：
  - 0：登录
  - 1：注册
  - 2：聊天消息
- 反馈结果：
  - 1：成功
  - 0：失败

### 用户信息管理
- 使用文本文件`userinfo.txt`存储用户信息
- 每行存储一个用户，格式为：`username=用户名&password=密码`

## 格式要求

### 用户名
- 长度：4-12位
- 必须包含至少一个字母和一个数字

### 密码
- 长度：6-12位
- 必须包含至少一个字母、一个数字和一个特殊字符

## 使用方法

### 1. 启动服务端
```bash
# 编译服务端代码
javac -d bin src\chatapp\common\entity\User.java src\chatapp\common\constant\MsgConstant.java src\chatapp\common\util\CommonUtil.java src\chatapp\server\handler\ClientHandler.java src\chatapp\server\Server.java

# 运行服务端
java -cp bin chatapp.server.Server
```

### 2. 启动客户端
```bash
# 编译客户端代码
javac -d bin src\chatapp\client\Client.java src\chatapp\client\handler\ClientMsgHandler.java src\ui\base\BaseUI.java src\ui\HomeUI.java src\ui\LoginUI.java src\ui\RegisterUI.java src\ui\ChatUI.java

# 运行客户端
java -cp bin chatapp.client.Client
```

### 3. 测试客户端
```bash
# 编译测试客户端
javac -d bin src\test\TestClient.java

# 运行测试客户端
java -cp bin test.TestClient
```

## 运行流程

1. 启动服务端，服务端会在端口8888上监听连接
2. 启动客户端，客户端会自动连接服务端
3. 在客户端主界面选择功能：
   - 选择1：进入登录界面，输入用户名和密码进行登录
   - 选择2：进入注册界面，输入用户名和密码进行注册
   - 选择0：退出系统
4. 登录成功后，进入群聊大厅，可以发送消息给所有在线用户
5. 在群聊大厅输入886退出聊天

## 注意事项

1. 服务端必须先于客户端启动
2. 客户端连接服务端时，默认连接到127.0.0.1:8888
3. 用户信息存储在userinfo.txt文件中，位于项目根目录
4. 系统会对用户名和密码进行格式验证，确保安全性

## 代码优化

1. **异常处理**：完善了异常处理机制，提高系统稳定性
2. **格式校验**：客户端和服务端都进行格式校验，确保数据合法性
3. **线程安全**：使用线程安全的集合存储在线客户端
4. **资源管理**：使用try-with-resources确保资源正确关闭
5. **代码结构**：模块化设计，提高代码可维护性

## 扩展建议

1. 添加私聊功能
2. 实现用户在线状态显示
3. 添加消息历史记录
4. 实现密码加密存储
5. 添加图形界面

## 许可证

本项目仅供学习使用，如有任何问题请联系作者。