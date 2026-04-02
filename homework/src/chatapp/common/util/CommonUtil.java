package chatapp.common.util;

import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;

import java.io.*;

/*
 1、验证用户名格式：长度在4到12，且至少包含一位数字和字母
 2、验证密码格式：长度在6到12，且至少包含一位数字，字母和特殊字符
 3、将userinfo.txt中的信息转换为User用户
 4、检查一个用户是否在userinfo.txt中存在
 5、将用户写在userinfo.txt中
 */
public class CommonUtil {
    public static boolean checkUserName(String username){
        if(username==null||username.length()<4||username.length()>12){
            return false;
        }

        boolean hasLetter=false,hasDigit=false;
        for(int i=0;i<username.length();i++){
            char c=username.charAt(i);
            if(c>='0'&&c<='9') hasDigit=true;
            if((c>='a'&&c<='z')||(c>='A'&&c<='Z')) hasLetter=true;
        }
        return hasDigit||hasLetter;
    }

    public static boolean checkPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        if (password.length() > 12 || password.length() < 4) return false;
        boolean hasLetter = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        return hasLetter && hasDigit && hasSpecial;
    }

    public  static User parseUser(String info){
        String[] arr = info.split("&");
        String username=arr[0].split("=")[1];
        String password=arr[1].split("=")[1];
        return new User(username,password);
    }

    public  static User checkUserExist(User user){
        try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(MsgConstant.USER_FILE_PATH)))){
            String line;
            while((line=br.readLine())!=null){
                User temp=parseUser(line);
                if(user.equals(temp)){
                    return temp;
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveUserToFile(User user){
        //续写
        try(BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MsgConstant.USER_FILE_PATH,true)))){
            bw.write((user.toString()));
            //换行
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
