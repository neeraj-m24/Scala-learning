import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}

import java.util.Properties

//Kafka producer factory for creating producer instance
object KafkaProducerFactory {
  def createProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }
}