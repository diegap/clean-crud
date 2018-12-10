import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration

class MongoDbReactiveConfig : AbstractReactiveMongoConfiguration() {

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create()
    }

    override fun getDatabaseName(): String {
        return "rxdb"
    }

}