package models

import com.github.tminglei.slickpg.PgEnumSupport
import slick.jdbc.PostgresProfile
import sangria.schema._
import sangria.macros.derive.{EnumTypeName, IncludeValues, deriveEnumType}

object Genres extends Enumeration{
  type Genre = Value
  val Jazz, Rock, Folk, HipHop, CountryMusic, Blues, Classical, Reggae, Electronic, Funk = Value
//    "Jazz", "Rock", "Folk", "HipHop", "CountryMusic", "Blues", "Classical", "Reggae", "Electronic", "Funk"))
implicit val GenreType = EnumType(
  name = "Genre",
  values = List(
    EnumValue("Jazz", value = Genres.Jazz),
    EnumValue("Rock", value = Genres.Rock),
    EnumValue("Folk", value = Genres.Folk),
    EnumValue("HipHop", value = Genres.HipHop),
    EnumValue("CountryMusic", value = Genres.CountryMusic),
    EnumValue("Blues", value = Genres.Blues),
    EnumValue("Classical", value = Genres.Classical),
    EnumValue("Reggae", value = Genres.Reggae),
    EnumValue("Electronic", value = Genres.Electronic),
    EnumValue("Funk", value = Genres.Funk)
  ))
}

trait MyEnumAPI extends PostgresProfile with PgEnumSupport{
  implicit val genreTypeMapper = createEnumJdbcType("Genre", Genres)
  implicit val genreListTypeMapper = createEnumListJdbcType("Genre", Genres)
  implicit val genreColumnExtensionMethodsBuilder = createEnumColumnExtensionMethodsBuilder(Genres)
  implicit val genreOptionColumnExtensionMethodsBuilder = createEnumOptionColumnExtensionMethodsBuilder(Genres)
}
