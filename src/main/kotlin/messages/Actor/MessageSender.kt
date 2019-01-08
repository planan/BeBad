package messages.Actor

import akka.actor.AbstractActor
import akka.actor.ActorContext
import akka.actor.ActorRef
import akka.event.Logging
import akka.event.LoggingAdapter
import akka.japi.pf.ReceiveBuilder
import messages.message.SendingMessage
import messages.message.SetRequest
import scala.PartialFunction
import scala.runtime.BoxedUnit

class MessageSender: AbstractActor() {
    private val log: LoggingAdapter = Logging.getLogger(context.system, this)
    var lastMessageString: String = ""
    override fun createReceive(): Receive = ReceiveBuilder().apply {
        match(SendingMessage::class.java) { message ->
            lastMessageString = message.messageString
            log.info("last message string: $lastMessageString")
            message.senderTo.tell(message.messageString, self())
        }
        matchAny{ o ->
            log.info("received unknown message: $o")
        }
    }.build()
}