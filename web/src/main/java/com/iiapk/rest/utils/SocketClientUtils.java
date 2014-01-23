package com.iiapk.rest.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.commons.io.IOUtils;

public class SocketClientUtils {
    /**
     * 用短连接方式发送数据
     * @param remoteIp
     * @param remotePort
     * @param connTimeout
     * @param soTimeout
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] sendDataShortConn(String remoteIp, int remotePort, int connTimeout, int soTimeout, byte[] data)
            throws IOException {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            socket = new Socket();
            // 设置read()阻塞超时时间
            socket.setSoTimeout(soTimeout);
            socket.connect(new InetSocketAddress(remoteIp, remotePort), connTimeout);
            out = socket.getOutputStream();
            // 发送数据
            IOUtils.write(data, out);
            in = socket.getInputStream();
            // 接收数据
            return IOUtils.toByteArray(in);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(socket);
        }
    }

    /**
     * 用短连接方式发送数据,客户端主动关闭
     * @param remoteIp
     * @param remotePort
     * @param connTimeout
     * @param soTimeout
     * @param soTimeoutAfterReceivedData 接收到数据后的soTimeout
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] sendDataShortConnPositiveClose(String remoteIp, int remotePort, int connTimeout,
            int soTimeout, int soTimeoutAfterReceivedData, byte[] data) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        InputStream input = null;
        try {
            socket = new Socket();
            // 设置read()阻塞超时时间
            socket.setSoTimeout(soTimeout);
            socket.connect(new InetSocketAddress(remoteIp, remotePort), connTimeout);
            out = socket.getOutputStream();
            // 发送数据
            IOUtils.write(data, out);
            input = socket.getInputStream();
            int n = 0;
            byte[] buffer = new byte[1024 * 32];
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
            boolean isReceviced = false;
            try {
                while (-1 != (n = input.read(buffer))) {
                    isReceviced = true;
                    if (n > 0) {
                        outBytes.write(buffer, 0, n);
                    }
                    // 接收到数据后,超时时间
                    socket.setSoTimeout(soTimeoutAfterReceivedData);
                }
            } catch (SocketTimeoutException e) {
                if (isReceviced) {
                    // 已接收数据到后的超时,忽略
                } else {
                    throw e;
                }
            }
            // 接收数据
            return outBytes.toByteArray();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(socket);
        }
    }
}