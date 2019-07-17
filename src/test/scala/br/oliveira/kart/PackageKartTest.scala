package br.oliveira.kart

import java.time.{Duration, LocalTime}

import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

class PackageKartTest extends FunSpec with Matchers with BeforeAndAfter {


  val voltas = List[Volta](Volta(inicio = LocalTime.now(), numero = 1, velocidadeMedia = 40, duracao = Duration.between(LocalTime.MIN, "00:01:19")),
    Volta(inicio = LocalTime.now(), numero = 2, velocidadeMedia = 43, duracao = Duration.between(LocalTime.MIN, "00:01:12")),
    Volta(inicio = LocalTime.now(), numero = 3, velocidadeMedia = 42, duracao = Duration.between(LocalTime.MIN, "00:02:14")),
    Volta(inicio = LocalTime.now(), numero = 4, velocidadeMedia = 44, duracao = Duration.between(LocalTime.MIN, "00:01:30")))

  val piloto = Piloto(codigo = "001", nome = "Piloto", voltas = voltas)

  val voltas2 = List[Volta](Volta(inicio = LocalTime.now(), numero = 1, velocidadeMedia = 40, duracao = Duration.between(LocalTime.MIN, "00:01:10")),
    Volta(inicio = LocalTime.now(), numero = 2, velocidadeMedia = 40, duracao = Duration.between(LocalTime.MIN, "00:01:05")),
    Volta(inicio = LocalTime.now(), numero = 3, velocidadeMedia = 42, duracao = Duration.between(LocalTime.MIN, "00:04:14")),
    Volta(inicio = LocalTime.now(), numero = 4, velocidadeMedia = 42, duracao = Duration.between(LocalTime.MIN, "00:01:26")))

  val piloto2 = Piloto(codigo = "002", nome = "Piloto 2", voltas = voltas2)

  val corrida: Corrida = List(piloto, piloto2)

  describe("Testar pacote kart") {
    it("Deve recuperar a melhor volta da corrida") {
      val melhorVolta = recuperarMelhorVoltaDaCorrida(corrida)
      melhorVolta._1.codigo shouldEqual "002"
      melhorVolta._2.numero shouldEqual 2
    }

    it("Deve calcular o tempo excedido por piloto") {
      calcularVencedorETempoExcedidoPorPiloto(corrida).map(_.excedido) shouldEqual List(Duration.ZERO, Duration.between(LocalTime.MIN, "00:01:40"))
    }


  }
}
