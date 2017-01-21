package modules

import background.chatrooms.Chatrooms
import com.google.inject.AbstractModule

/**
  * Created by rokas on 1/21/17.
  */
class BackgroundModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Chatrooms]).asEagerSingleton()
  }
}
