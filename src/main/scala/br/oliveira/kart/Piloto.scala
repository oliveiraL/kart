package br.oliveira.kart

import java.time.Duration

case class Piloto(
  codigo: String,
  nome: String,
  voltas: List[Volta] = List.empty[Volta],
  excedido: Duration = Duration.ZERO,
  posicao: Int = 0
) {
  def melhorVolta: Volta = voltas.minBy(_.duracao)

  def duracaoCorrida: Duration =
    voltas.foldLeft(Duration.ZERO)((corrida, volta) => corrida.plus(volta.duracao))

  def concluiu(implicit numeroDeVoltas: Int): Boolean =
    quantidadeDeVoltas >= numeroDeVoltas

  def quantidadeDeVoltas: Int = voltas.size

  def velocidadeMedia: Double = {
    voltas.foldLeft(0.0)((velocidade, volta) => velocidade + ((volta.velocidadeMedia / 3.6) * volta.duracao.getSeconds)) /
      voltas.foldLeft(0.0)(_ + _.duracao.getSeconds)
  } * 3.6
}
