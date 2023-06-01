package models.user

import sangria.schema._

object Roles extends Enumeration {
  type Role = Value
  val user, admin = Value

  implicit val RoleType = EnumType(
    name = "Genre",
    values = List(
      EnumValue("user", value = Roles.user),
      EnumValue("admin", value = Roles.admin),
    ))
}
