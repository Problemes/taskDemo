package com.framework.quartz.api;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.List;

/**
 * 使用commons-fileupload文件上传：http://blog.csdn.net/qq_20991785/article/details/44455323
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

    private static String UPLOAD_PATH = "D:/test/upload";
    private static String DOWNLOAD_PATH = "D:\\test\\download";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public String servletUpload(){

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

            for(int i=0; i<items.size(); i++){

                FileItem item = items.get(i);

                if(item.isFormField()){

                    //这是一个普通的表单域
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();

                    paramValue = new String(paramValue.getBytes("iso-8859-1"),"utf-8");

                    System.out.println("paramName:" + paramName + " paramValue:" + paramValue);

                }else{

                    //这是上传文件域
//                    ServletContext sctx = getServletContext();

                    //依据逻辑路径获得实际部署时的物理路径
//                    String path = sctx.getRealPath("upload");
                    System.out.println("path:" + UPLOAD_PATH);

                    //获得上传文件的名称
                    filename = item.getName();

                    //item.getName方法在某些操作系统上，会返回路径加文件名。
                    filename = filename.substring( filename.lastIndexOf(File.separator) + 1);
                    System.out.println("filename:" + filename);

                    File file = new File(UPLOAD_PATH + File.separator + filename);

                    if (file == null){
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
    @Path("/download")
    public void processDownload(){

        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;

        System.out.println("Come on, baby .......");

        try{
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
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }

            out.flush();

            response.addHeader("token", "hello ok");

        }catch(Exception e){
            e.printStackTrace();
            response.addHeader("token", "hello exception");

        }finally {
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
}
