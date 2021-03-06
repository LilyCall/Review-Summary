package keyword.kw_transient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Alexia
 * @description 使用transient关键字不序列化某个变量 注意读取的时候，读取数据的顺序一定要和存放数据的顺序保持一致 eg.
 * 在实际开发过程中，我们常常会遇到这样的问题，这个类的有些属性需要序列化，而其他属性不需要被序列化，打个比方，如果一个用户有一些敏感信息（如密码，银行卡号等），为了安全起见，不希望在网络操作（主要涉及到序列化操作，本地序列化缓存也适用）中被传输，这些信息对应的变量就可以加上transient关键字。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。
 * @date 2013-10-15
 */
public class TransientTest {

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Alexia");
        user.setPasswd("123456");
        user.age = 20;

        System.out.println("read before Serializable: ");
        System.out.println("username: " + user.getUsername());
        System.err.println("password: " + user.getPasswd());
        System.err.println("age: " + user.age);

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("/home/smates/temp/Java/user.txt"));
            os.writeObject(user); // 将User对象写进文件
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User.age = 10;
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("/home/smates/temp/Java/user.txt"));
            user = (User) is.readObject(); // 从流中读取User的数据
            is.close();

            System.out.println("\nread after Serializable: ");
            System.out.println("username: " + user.getUsername());
            System.err.println("password: " + user.getPasswd());
            System.err.println("age: " + user.age);//反序列化后类中static型变量age的值为当前JVM中对应static变量的值

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
