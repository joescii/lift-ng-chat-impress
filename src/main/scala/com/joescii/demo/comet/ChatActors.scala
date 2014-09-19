package com.joescii.demo.comet

import net.liftweb.actor._
import net.liftweb.http.{CometListener, ListenerManager}
import net.liftmodules.ng.SimpleBindingActor
import net.liftmodules.ng.Angular.NgModel

object ChatServer extends LiftActor with ListenerManager {
  private var msgs = List.empty[String]

  def createUpdate = ChatMessages(msgs)

  override def lowPriority = {
    case msg:String => {
      msgs = (msgs :+ msg).distinct takeRight 10
      sendListenersMessage(ChatMessages(msgs))
    }
  }
}

case class ChatMessages(msgs:List[String]) extends NgModel

class ChatBinder extends SimpleBindingActor[ChatMessages] (
  "chatRoom",
  ChatMessages(List())
) with CometListener {
  def registerWith = ChatServer
}