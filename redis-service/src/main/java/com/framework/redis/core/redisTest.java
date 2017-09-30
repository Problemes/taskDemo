package com.framework.redis.core;

import com.framework.redis.util.RedisClient;
import com.framework.redis.util.RedisShardPoolClient;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by HR on 2017/9/21.
 */
public class redisTest {

    @Test
    public void testRedis(){

        RedisClient redisClient = RedisClient.getInstance();

        Jedis jedis = redisClient.getJedis();

        System.out.println("Jedis:" + jedis);

        jedis.setex("wori",60,"shshsa");

        System.out.println(jedis.get("wori"));
    }

    @Test
    public void testShardedRedis(){

        RedisShardPoolClient redisClient = RedisShardPoolClient.getInstance();

        ShardedJedis shardedJedis = redisClient.getJedis();

        System.out.println("Jedis:" + shardedJedis);

        shardedJedis.setex("wori",60,"shshsa");

        System.out.println("JedisClient:" + shardedJedis.getShard("wori").getClient());
        System.out.println("JedisClientHost:" + shardedJedis.getShard("wori").getClient().getHost() + "---" + shardedJedis.getShard("wori").getClient().getPort());

        System.out.println(shardedJedis.get("wori"));
    }
}
