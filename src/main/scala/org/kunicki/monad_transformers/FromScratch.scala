package org.kunicki.monad_transformers

import scala.concurrent.{ExecutionContext, Future}

case class User(id: Long, name: String)

case class Company(id: Long, name: String)

class FromScratch {

  type Effect[A] = OptionInsideFuture[A]

  private def findUserById(id: Long): Effect[User] = ???

  private def findCompanyByUser(user: User): Effect[Company] = ???

  def findCompanyByUserId(id: Long)(implicit ec: ExecutionContext): Future[Option[Company]] =
    (for {
      user <- findUserById(id)
      company <- findCompanyByUser(user)
    } yield company).value
}

case class OptionInsideFuture[A](value: Future[Option[A]])(implicit ec: ExecutionContext) {

  def map[B](f: A => B): OptionInsideFuture[B] = OptionInsideFuture(value.map(_.map(f)))

  def flatMap[B](f: A => OptionInsideFuture[B]): OptionInsideFuture[B] = OptionInsideFuture(value.flatMap {
    case Some(a) => f(a).value
    case None => Future.successful(None)
  })
}

case class OptionInsideList[A](value: List[Option[A]])(implicit ec: ExecutionContext) {

  def map[B](f: A => B): OptionInsideList[B] = OptionInsideList(value.map(_.map(f)))

  def flatMap[B](f: A => OptionInsideList[B]): OptionInsideList[B] = OptionInsideList(value.flatMap {
    case Some(a) => f(a).value
    case None => List(None)
  })
}