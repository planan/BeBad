import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestActorRef
import junit.framework.Assert.assertEquals
import messages.Actor.AkkademyDb
import messages.Actor.MessageSender
import messages.message.SendingMessage
import messages.message.SetRequest
import org.junit.Test

class AkkademyDbTest {
    private val system: ActorSystem = ActorSystem.create()

    @Test
    fun itShouldPlaceKeyValueFromSetMessageIntoMap() {
        val actorRef: TestActorRef<AkkademyDb> =
                TestActorRef.create(system, Props.create(AkkademyDb::class.java))
        actorRef.tell(SetRequest("key", "value"), ActorRef.noSender())

        val akkademyDb: AkkademyDb = actorRef.underlyingActor()
        assertEquals(akkademyDb.map["key"], "value")
    }

    @Test
    fun itShouldRecordLastSendingMessageString() {
        val actorRef: TestActorRef<AkkademyDb> =
                TestActorRef.create(system, Props.create(AkkademyDb::class.java))
        actorRef.tell(SetRequest("key", "value"), ActorRef.noSender())

        val recordActorRef: TestActorRef<MessageSender> =
                TestActorRef.create(system, Props.create(MessageSender::class.java))
        recordActorRef.tell(SendingMessage(actorRef, "trulyLastString"), ActorRef.noSender())

        val messageSender: MessageSender = recordActorRef.underlyingActor()
        assertEquals(messageSender.lastMessageString, "trulyLastString")
    }
}