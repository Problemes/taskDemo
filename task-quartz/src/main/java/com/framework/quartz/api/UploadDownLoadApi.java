package com.framework.quartz.api;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 使用commons-fileupload文件上传：http://blog.csdn.net/qq_20991785/article/details/44455323
 * 使用原生下载可用httpclient测试：https://www.cnblogs.com/Scott007/p/3817285.html
 * Created by HR on 2018/1/17.
 */

@Path("/io")
public class UploadDownLoadApi {

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private ServletContext sctx;

    private static String UPLOAD_PATH = "D:/zzz/upload";
    private static String DOWNLOAD_PATH = "D:\\zzz\\download";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public String servletUpload() {

        String filename = "";

         /*
         * step1,创建DiskFileItemFactory对象,该对象为解析器提供解析时的缺省的配置。
         */
        DiskFileItemFactory diff = new DiskFileItemFactory();

        String serverPath = "D:\\test";
        String tempPath = "\\testUpload";
        Integer sizeMax = 1;
        diff.setSizeThreshold(5 * 1024); //最大缓存
        diff.setRepository(new File(serverPath + tempPath));//临时文件目录
        ServletFileUpload upload = new ServletFileUpload(diff);
        upload.setSizeMax(sizeMax * 1024 * 1024);//文件最大上限

        /*
         * 创建解析器
         */
        ServletFileUpload sfu = new ServletFileUpload(diff);
        sfu.setHeaderEncoding("utf-8");

        /*
         * 使用解析器解析,解析器会将每一个表单域(比如 uname,file)中的数据解析出来之后，放到
         * 对应的一个FileItem对象上。FileItem对象提供了相应的方法来获得请求参数值。
         */
        try {
            List<FileItem> items = sfu.parseRequest(request);

            for (int i = 0; i < items.size(); i++) {

                FileItem item = items.get(i);

                if (item.isFormField()) {

                    //这是一个普通的表单域
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();

                    paramValue = new String(paramValue.getBytes("iso-8859-1"), "utf-8");

                    System.out.println("paramName:" + paramName + " paramValue:" + paramValue);

                } else {

                    //这是上传文件域
//                    ServletContext sctx = getServletContext();

                    //依据逻辑路径获得实际部署时的物理路径
//                    String path = sctx.getRealPath("upload");
                    System.out.println("path:" + UPLOAD_PATH);

                    //获得上传文件的名称
                    filename = item.getName();

                    //item.getName方法在某些操作系统上，会返回路径加文件名。
                    filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
                    System.out.println("filename:" + filename);

                    File file = new File(UPLOAD_PATH + File.separator + filename);

                    if (file == null) {
                        throw new RuntimeException("filePath is null");
                    }

                    item.write(file);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传文件失败");
        }

        return filename;
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/interfaceDownload")
    public void processDownload() {

        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;

        System.out.println("Come on, baby .......");

        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");

            String userName = request.getHeader("userName");
            String password = request.getHeader("password");
            String fileName = request.getHeader("fileName");

            System.out.println("userName:" + userName);
            System.out.println("password:" + password);
            System.out.println("fileName:" + fileName);


            //可以根据传递来的userName和password做进一步处理，比如验证请求是否合法等
//            File file = new File(DOWNLOAD_PATH + "\\" + fileName);
            File file = new File(fileName);
            response.setContentLength((int) file.length());
            response.setHeader("Accept-Ranges", "bytes");

            int readLength = 0;

            in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
            out = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[BUFFER_SIZE];
            while ((readLength = in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }

            out.flush();

            response.addHeader("token", "200 ok");

        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("token", "exception" + e.getMessage());

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/clientDownload")
    /**
     * servlet实现下载功能：http://www.jb51.net/article/85102.htm
     * 注意：客户端是用a标签来接收url，url需要encodeURI
     */
    public void clientProcessDownload() throws URISyntaxException, IOException {

        OutputStream os = null;
        InputStream fis = null;

        try {
            String filePath = sctx.getRealPath("download/test.txt"); //文件在项目中的路径
//            String filePath = this.getClass().getClassLoader().getResource("").toURI().getPath() + "download/test.txt"; //文件在项目中的路径

            String filePathDownLoad = this.getClass().getClassLoader().getResource("").toURI().getPath() + "download"; //文件在项目中的路径
            String filePathRealPath = sctx.getRealPath("download"); //文件在项目中的路径
            System.out.println("love download:-->>>" + filePathDownLoad);
            System.out.println("love Real download path:-->>>" + filePathRealPath);

            System.out.println("project file Path:--->>>" + filePath);

            File outfile = new File(filePath);

            String filename = outfile.getName();// 获取文件名称

            System.out.println("file name:--->>>" + filename);

            FileInputStream file = new FileInputStream(filePath);

            fis = new BufferedInputStream(file);

            byte[] buffer = new byte[fis.available()];

            fis.read(buffer); //读取文件流

            response.reset(); //重置结果集

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1")); //返回头 文件名

            response.addHeader("Content-Length", "" + outfile.length()); //返回头 文件大小

            response.setContentType("application/octet-stream");  //设置数据种类 //获取返回体输出权

            os = new BufferedOutputStream(response.getOutputStream());

            os.write(buffer);// 输出文件

            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void testFileExist() {
        File file = new File("d:\\zzz\\tests.txt");
        this.judeFileExists(file);

        File dir = new File("d:\\zzz");
        this.judeDirExists(dir);


    }

    // 判断文件是否存在
    public static void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }


}
