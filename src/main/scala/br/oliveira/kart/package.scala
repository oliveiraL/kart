package br.oliveira

import java.time.format.DateTimeFormatter
import java.time.{Duration, LocalTime}

package object kart {

  type Corrida = List[Piloto]

  implicit val numeroDeVoltas: Int = 4

  implicit def stringParaLocalTime(periodo: String): LocalTime =
    LocalTime.parse(periodo)

  implicit def strintParaDuration(tempo: String): Duration =
    Duration.between(LocalTime.MIN, "00:0" + tempo.replaceAll("\\s+", ""))

  implicit def duracaoParaString(duracao: Duration): String = {
    val formatacao = duracao match {
      case d if d.toHours > 0 => "HH:mm:ss.SSS"
      case d if d.toMinutes > 0 => "mm:ss.SSS"
      case _ => "ss.SSS"
    }
    LocalTime.MIDNIGHT.plus(duracao).format(DateTimeFormatter.ofPattern(formatacao))
  }

  implicit def converterPilotoParaString(piloto: Piloto): String = {
    val tempoProva: String = if (piloto.concluiu) piloto.excedido match {
      case tempo if tempo.isZero => piloto.duracaoCorrida
      case excedido =>
        val tempo: String = excedido
        s"+${tempo}s"
    } else "Não concluiu"
    val melhorTempo: String = piloto.melhorVolta.duracao
    s"${piloto.posicao},  ${piloto.codigo},  ${piloto.nome},  ${piloto.quantidadeDeVoltas},  $tempoProva,   ${piloto.melhorVolta.numero},   $melhorTempo    ${arrendondarNumero(piloto.velocidadeMedia)}km/h"
  }

  def converterVelocidade(velocidade: String): Double =
    velocidade.replaceAll(",", ".").toDouble


  def converterNumeroVolta(volta: String): Int =
    volta.replaceAll("\\s+", "").toInt


  def recuperarMelhorVoltaDaCorrida(corrida: Corrida): (Piloto, Volta) = {
    val melhor = corrida.minBy(_.melhorVolta.duracao)
    (melhor, melhor.melhorVolta)
  }

  def calcularVencedorETempoExcedidoPorPiloto(corrida: Corrida): Corrida = {
    val concluiram = corrida.filter(_.concluiu).sortBy(_.duracaoCorrida)
    val naoConcluiram = corrida.filterNot(_.concluiu).sortBy(_.nome)
    val possicao: List[Piloto] = concluiram ::: naoConcluiram
    val vencedor: Piloto = possicao.head
    possicao.zipWithIndex.map(piloto => piloto._1.copy(excedido = piloto._1.duracaoCorrida.minus(vencedor.duracaoCorrida), posicao = piloto._2 + 1))
  }

  def arrendondarNumero(valor: Double): Double =
    BigDecimal(valor).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

}
