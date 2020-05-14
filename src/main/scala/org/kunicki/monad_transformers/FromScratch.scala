package org.kunicki.monad_transformers

case class User(id: Long, name: String)

case class Company(id: Long, name: String)

class FromScratch {

  private def findUserById(id: Long): User = ???

  private def findCompanyByUser(user: User): Company = ???

  def findCompanyByUserId(id: Long): Company = ???
}
