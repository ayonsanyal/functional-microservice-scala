import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.ayon.imdbapi.domain.movies.repository.MovieInformationRepository
import com.ayon.imdbapi.domain.movies.service.ImdbMovieExtractionService
import com.ayon.imdbapi.externals.interpreter.repository.MovieInformationRepositoryInterpreter
import com.ayon.imdbapi.externals.interpreter.service.ImdbMovieExtractionServiceInterpreter
import com.ayon.imdbapi.externals.routes.Routes
import swagger.Swagger
import com.softwaremill.macwire._

import scala.io.StdIn

object Boot {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-actor-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    lazy val http = Http(system)
    val route = new Routes(wire[ImdbMovieExtractionServiceInterpreter],wire[MovieInformationRepositoryInterpreter])
    val routesToRun: Route = route.routes ~ Example(system).routes ~ Swagger(system).routes ~
      getFromResourceDirectory("swagger-ui")
    val port = 8080
    val bindingFuture = Http().bindAndHandle(routesToRun, "0.0.0.0", port)


    println(s"Application has started on port $port")

    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}