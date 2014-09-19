package com.joescii.demo
package snippet

import net.liftmodules.ng.Angular._
import net.liftweb.common.Empty

object ChatSnips {
  def chatService = renderIfNotAlreadyDefined(angular.module("ChatServices")
    .factory("Chat", jsObjFactory()
      .jsonCall("submit", (msg:String) => {
        comet.ChatServer ! msg
        Empty
      })
    ))
}
