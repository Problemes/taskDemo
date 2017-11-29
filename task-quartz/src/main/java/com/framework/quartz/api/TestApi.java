package com.framework.quartz.api;

import com.framework.http.core.HttpUtil;
import com.framework.quartz.authority.Base64Util;
import com.framework.quartz.authority.RSAUtil;
import com.framework.redis.util.RedisClient;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by HR on 2017/10/18.
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/test")
public class TestApi {

    RedisClient redisClient = RedisClient.getInstance();

    @GET
    @Path("/method/param/{param}")
    public JSONObject testJersey(@PathParam("param") String param){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("param", param);
        return jsonObject;
    }

    @GET
    @Path("/username/{username}/password/{password}/token")
    public JSONObject getToken(@PathParam("username") String username,
                               @PathParam("password") String password) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        Jedis jedis = redisClient.getJedis();

        String up = username + ":" + password;

        byte[] encryptData = RSAUtil.encryptByPublicKey(up.getBytes(), jedis.get("public_" + username));
        String data = Base64Util.encryptBASE64(encryptData);

        String url = "http://api.730edu.net/restApi/v1/verify/client/test/getToken?data=";

        //此处只需要把data encode 就行了。
        String result = HttpUtil.doGet(url + URLEncoder.encode(data, "UTF-8"));

        return JSONObject.fromObject(result);
    }

}
