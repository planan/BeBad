package messages.message

import akka.actor.ActorRef

data class SendingMessage(val senderTo: ActorRef, val messageString: String)