import scala.util.Random

def printIntroductoryMessage(): Unit = {
  println()
  println ("""Congratulations, you are the newest ruler of ancient Samaria, elected
  for a ten year term of office. Your duties are to dispense food, direct
  farming, and buy and sell land as needed to support your people. Watch
  out for rat infestations and the plague! Grain is the general currency,
  measured in bushels. The following will help you in your decisions:
    * Each person needs at least 20 bushels of grain per year to survive.
    * Each person can farm at most 10 acres of land.
    * It takes 2 bushels of grain to farm an acre of land.
    * The market price for land fluctuates yearly.
  Rule wisely and you will be showered with appreciation at the end of
  your term. Rule poorly and you will be kicked out of office!""")
}

def readInt(message: String): Int = {
  try {
    scala.io.StdIn.readLine(message).toInt
  } catch {
    case _: Throwable =>
      println("That’s not an integer. Please enter an integer.")
      readInt(message)
  }
}

def askHowMuchLandToBuy(bushels: Int, price: Int) = {
  var acresToBuy = readInt("How many acres will you buy? ")
  println()
  while (acresToBuy < 0 || acresToBuy * price > bushels) {
    println("O Great Hammurabi, we have but " + bushels + " bushels of grain!")
    acresToBuy = readInt("How many acres will you buy? ")
  }
  acresToBuy
}

def howManyAcresOfLandToSell(acres: Int) = {
  var acresToSell = readInt("How many acres would you like to sell? ")
  println()
  while (acresToSell < 0 || acresToSell > acres) {
    println("O Great Hammurabi, we have but " + acres + " acres to sell!")
    acresToSell = readInt("How many acres will you sell? ")
  }
  acresToSell
}

def howMuchGrainToFeedToThePeople(bushels: Int) = {
  var grainToFeed = readInt("How much grain would like to use to feed your people? ")
  println()
  while (grainToFeed > bushels) {
    println("O Great Hammurabi, we have but " + bushels + " of grain to feed the people!")
    grainToFeed = readInt("How much grain would like to use to feed your people? ")
  }
  grainToFeed
}

def howManyAcresToPlantWithSeed(bushels: Int, population: Int) = {
  var acresToPlant = readInt("How many acres would like to plant with seeds? ")
  println()
  while (acresToPlant > population * 10 || acresToPlant * 2 > bushels) {
    println("O Great Hammurabi, we do not have the people to resources to do it so!")
    acresToPlant = readInt("How much acres would like to plant? ")
  }
  acresToPlant
}

def plagueDevastation(population: Int) = {
  var peopleDead = 0
  var plaguePercentage = scala.util.Random.nextInt(100)
  if (plaguePercentage <= 15) {
    peopleDead = population / 2
  }
  peopleDead
}

def howManyPeopleDiedByStarvation(population: Int, grainsUsedToFeed: Float) = {
  var bushelStorageNeededForTheYear = (population * 20)
  var feedPeople = ((grainsUsedToFeed / bushelStorageNeededForTheYear) * 100).toInt
  var starvedPeople = population - feedPeople
  var starvedPeopleMaxPercentage = (population * 0.45).toInt
  if (feedPeople < starvedPeopleMaxPercentage) {
    println()
    println(
      """O great Hammurabi! You were the worst ruler in history,
    You could not look after your people as you let """.stripMargin + starvedPeople + """ people starve to death.
    The left population are so mad at you that they are coming for you.
    They are planning to decapitate you in the central square.
    You really sucks!!!
    GAME OVER """)
    println()
    System.exit(1)
  }
  if (starvedPeople < 0) {
    starvedPeople = 0
  }
  starvedPeople
}


def howManyPeopleCameToTheCity(starved: Int, acresOwned: Int, bushelsInStorage: Int, population: Int) = {
  var numberOfPeopleWhoMovedIn = 0
  if (starved <= 0) {
    numberOfPeopleWhoMovedIn = (20 * acresOwned + bushelsInStorage) / (100 * population) + 1
    numberOfPeopleWhoMovedIn
  } else {
    numberOfPeopleWhoMovedIn
  }
}

def howGoodTheHarvestWas(acresToPlant: Int) = {
  var bushelsGrowth = 1 + scala.util.Random.nextInt(8)
  bushelsGrowth
}

def ratsDevastation(bushelsInStorage: Int) = {
  var attackProbability = scala.util.Random.nextInt(100)
  var attackLoss = 1 + scala.util.Random.nextInt(3)
  var bushelsEaten = 0
  if (attackProbability <= 40) {
    bushelsEaten = bushelsInStorage * (attackLoss / 10)
  }
  bushelsEaten
}

def howMuchLandWillCostNextYear() = {
  var acrePriceFluctuation = 17 + scala.util.Random.nextInt(6)
  acrePriceFluctuation
}

object Hammurabi {

  def hammurabi(): Unit = {
    var starved = 0 // how many people starved
    var immigrants = 5 // how many people came to the city
    var population = 100
    var harvest = 3000 // total bushels harvested
    var bushelsPerAcre = 3 // amount harvested for each acre planted
    var rats_ate = 200 // bushels destroyed by rats
    var bushelsInStorage = 2800
    var acresOwned = 1000
    var pricePerAcre = 19 // each acre costs this many bushels
    var plagueDeaths = 0

    printIntroductoryMessage()

    var year = for (i <- 1 to 10) {
      println()
      println(
        """O great Hammurabi!
      You are in year """ +  i  + """ of your ten year rule.
      In the previous year """ + starved + """ people starved to death.
      In the previous year """ + immigrants + """ people entered the kingdom.
      The population is now """ + population + """.
      We harvested """ + harvest + """ bushels at """ + bushelsPerAcre + """ bushels per acre.
      Rats destroyed """ + rats_ate + """ bushels, leaving """ + bushelsInStorage + """ bushels in storage.
      The city owns """ + acresOwned + """ acres of land.
      Land is currently worth """ + pricePerAcre + """ bushels per acre.
      There were """ + plagueDeaths + """ deaths from the plague.""")

      println()
      var acresToBuy = askHowMuchLandToBuy(bushelsInStorage, pricePerAcre)
      if (acresToBuy > 0) {
        var amountSpent = pricePerAcre * pricePerAcre
        acresOwned = acresOwned + acresToBuy
        bushelsInStorage = bushelsInStorage - amountSpent

      } else {
        var acresToSell = howManyAcresOfLandToSell(acresOwned)
        var amountEarned = acresToSell * pricePerAcre
        acresOwned = acresOwned - acresToSell
        bushelsInStorage = bushelsInStorage + amountEarned
      }


      var grainToFeed = howMuchGrainToFeedToThePeople(bushelsInStorage)
      var usedGrain = grainToFeed
      bushelsInStorage = bushelsInStorage - usedGrain


      var acresToPlant = howManyAcresToPlantWithSeed(bushelsInStorage, population)
      var updateBushelsInStorageValue = acresToPlant * 2
      bushelsInStorage = bushelsInStorage - updateBushelsInStorageValue


      var populationDeathWithPlague = plagueDevastation(population)
      population = population - populationDeathWithPlague.toInt
      plagueDeaths = populationDeathWithPlague.toInt


      var starvedPeopleWhoDied = howManyPeopleDiedByStarvation(population, usedGrain)
      starved = starvedPeopleWhoDied.toInt
      population = population - starved

      var quantityOfPeopleWhoMovedIn = howManyPeopleCameToTheCity(starved, acresOwned, bushelsInStorage, population)
      immigrants = quantityOfPeopleWhoMovedIn.toInt
      population = population + immigrants

      var bushlesProductionForTheYear = howGoodTheHarvestWas(acresToPlant)
      var bushelsProduction = acresToPlant * bushlesProductionForTheYear
      bushelsPerAcre = bushlesProductionForTheYear
      harvest = bushelsProduction
      bushelsInStorage = bushelsInStorage + bushelsProduction

      var bushelsAteByRats = ratsDevastation(bushelsInStorage)
      rats_ate = bushelsAteByRats
      bushelsInStorage = bushelsInStorage - bushelsAteByRats


      pricePerAcre = howMuchLandWillCostNextYear()

    }
    println(
      """O great Hammurabi!
        |You succed to rule the empire with merit.
        |The population love you and are acclaiming for you to continue in the command.
        |They never had a great ruler like yourself.
        |The word of mounth is saying that you were the greatest ruler!
        |You win.""")
    println()
  }
}

Hammurabi.hammurabi