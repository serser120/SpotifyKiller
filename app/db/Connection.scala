package db

import com.google.inject.Singleton
import slick.jdbc.PostgresProfile.api._

@Singleton
object Connection {
  val db = Database.forConfig("postgres")
}
