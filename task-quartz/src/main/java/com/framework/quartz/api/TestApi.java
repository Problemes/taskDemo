package com.framework.quartz.api;

import net.sf.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by HR on 2017/10/18.
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/test")
public class TestApi {

    @GET
    @Path("/method/param/{param}")
    public JSONObject testJersey(@PathParam("param") String param){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("param", param);
        return jsonObject;
    }

}
