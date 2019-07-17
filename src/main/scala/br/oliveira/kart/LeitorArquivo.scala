package br.oliveira.kart


import scala.io.Source
import scala.util.{Success, Try, Failure}

object LeitorArquivo {
  private val hora = "(\\d{2}:\\d{2}:\\d{2}(?:.\\d{3}\\b)?)".r
  private val codigo = " ([0-9]+)(?-) ".r
  private val duracao = " (\\d{1,2}:\\d{2}.\\d{3})".r
  private val piloto = "([A-Za-z]+).([A-Za-z]+)".r
  private val volta = " (\\d{1}) ".r
  private val velocidadeMedia = "(\\d{2},\\d{1,3})".r

  def lerArquivoDeLogDaCorrida(path: String): Either[String, Corrida] = {
    Try(Source.fromFile(path)) match {
      case Success(arquivo) => Right(lerLinhasDoArquivoDeLogDaCorrida(arquivo.getLines().toList))
      case Failure(erro) => Left(erro.getMessage)
    }
  }

  def lerLinhasDoArquivoDeLogDaCorrida(linhas: List[String]): Corrida = {
    linhas.foldLeft(Map.empty[String, Piloto])((corrida, linha) => {
      this.codigo.findFirstIn(linha) match {
        case Some(c) =>
          val volta = recuperarVoltaPorLinha(linha)
          val codigoSemEspacos = c.replaceAll("\\s+", "")
          val piloto: Piloto = corrida.get(codigoSemEspacos) match {
            case Some(p) => p
            case None => Piloto(codigoSemEspacos, this.piloto.findFirstIn(linha).get)
          }
          corrida updated(piloto.codigo, piloto.copy(voltas = volta :: piloto.voltas))
        case None => corrida
      }
    }).values.toList
  }

  def recuperarVoltaPorLinha(linha: String): Volta = {
    val novaLinha = linha.replaceAll("\\t+", " ")
    Volta(this.hora.findFirstIn(novaLinha).get,
      converterNumeroVolta(this.volta.findFirstIn(novaLinha).get),
      converterVelocidade(this.velocidadeMedia.findFirstIn(novaLinha).get),
      this.duracao.findFirstIn(novaLinha).get)
  }
}
