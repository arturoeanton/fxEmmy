@GrabResolver(name = 'Maven Central', root = 'http://repo1.maven.org/')
@Grab(group = 'redis.clients', module = 'jedis', version = '2.1.0')
import redis.clients.jedis.Jedis

@Singleton
class Memory {
    private Jedis jedis
    private getJedis() {
        if (jedis == null) {
            jedis = new Jedis("localhost", 6379, 0)
            jedis.connect()
        }
        try {
            jedis.ping();
        }catch(def e){
            jedis = new Jedis("localhost", 6379, 0)
            jedis.connect()
        }
        return jedis

    }

    def put(def key, def value) {
        getJedis().hset("${key}", "state", value.toString())
        getJedis().expire("${key}", 60)
    }

    def get(def key) {
        getJedis().expire("${key}", 60)
        getJedis().hget("${key}", "state")

    }

    def putUsername(def key, def value) {
        getJedis().hset("${key}", "username", value.toString())
        getJedis().expire("${key}", 60)
    }

    def getUsername(def key) {
        getJedis().expire("${key}", 60)
        getJedis().hget("${key}", "username")
    }
}