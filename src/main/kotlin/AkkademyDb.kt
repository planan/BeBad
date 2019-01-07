import akka.actor.AbstractActor
import akka.event.Logging
import akka.event.LoggingAdapter
import akka.japi.pf.ReceiveBuilder
import messages.SetRequest
import scala.PartialFunction

class AkkademyDb: AbstractActor() {
    private val log: LoggingAdapter = Logging.getLogger(context.system, this)
    val map: HashMap<String, Any> = hashMapOf()
    override fun createReceive(): Receive = ReceiveBuilder().apply {
        match(SetRequest::class.java) {  message ->
            log.info("Received Set request: $message")
            map[message.key] = message.value
        }
        matchAny{ o ->
            log.info("received unknown message: $o")
        }
    }.build()
}