package org.kunicki.monad_transformers

import scala.concurrent.{ExecutionContext, Future}

case class User(id: Long, name: String)

case class Company(id: Long, name: String)

class FromScratch {

  type Effect[A] = Future[Option[A]]

  private def findUserById(id: Long): Effect[User] = ???

  private def findCompanyByUser(user: User): Effect[Company] = ???

  def findCompanyByUserId(id: Long)(implicit ec: ExecutionContext): Future[Option[Company]] =
    for {
      user <- findUserById(id)
      company <- findCompanyByUser(user)
    } yield company
}
