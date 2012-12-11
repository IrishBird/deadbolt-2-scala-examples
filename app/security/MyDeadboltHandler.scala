package security

import be.objectify.deadbolt.scala.{DynamicResourceHandler, DeadboltHandler}
import play.api.mvc.{Request, Result, Results}
import be.objectify.deadbolt.core.models.Subject
import models.User

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDeadboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends DeadboltHandler
{
  override def getDynamicResourceHandler[A](request: Request[A]): Option[DynamicResourceHandler] = {
    if (dynamicResourceHandler != null) Some(dynamicResourceHandler)
    else Some(new MyDynamicResourceHandler())
  }

  override def getSubject[A](request: Request[A]): Option[Subject] = {
    // e.g. request.session.get("user")
    Some(new User("steve"))
  }

  def onAccessFailure[A](request: Request[A]): Result = {
    Results.Forbidden(views.html.accessFailed())
  }
}