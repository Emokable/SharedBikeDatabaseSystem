package com.example.sharedbike.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordEncryption {
    public static void main(String[] args) {
        // 用户名和密码
        String username = "TaoShihan";
        String password = "TaoShihan";

        // 盐值
        ByteSource salt = ByteSource.Util.bytes(username);

        // 设置哈希算法和迭代次数
        String hashAlgorithmName = "SHA-1";
        int hashIterations = 16;

        // 使用 SimpleHash 进行加密
        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        String encryptedPassword = hash.toHex();

        // 输出加密后的密码
        System.out.println("加密后的密码: " + encryptedPassword);
    }
}
