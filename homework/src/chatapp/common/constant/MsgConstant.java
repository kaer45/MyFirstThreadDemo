package chatapp.common.constant;

public class MsgConstant {
    // 消息类型（state）
    public static final int MSG_TYPE_LOGIN = 0;    // 登录
    public static final int MSG_TYPE_REGISTER = 1; // 注册
    public static final int MSG_TYPE_CHAT = 2;     // 聊天消息
    // 反馈结果
    public static final int FEEDBACK_SUCCESS = 1;  // 成功
    public static final int FEEDBACK_FAIL = 0;     // 失败
    // 消息分隔符
    public static final String MSG_SPLIT = "&";
    // 用户信息文件路径
    public static final String USER_FILE_PATH = "homework\\userinfo.txt";

    public static final String IP="127.0.0.1";
    public  static final int PORT=8888;
}