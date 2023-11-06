package agente;

import algoritmo.ProgramaLadrao;

import java.util.Arrays;

public class Ladrao extends ProgramaLadrao {
	public int heuristica(int codigo){ //Ladrão prefere explorar até encontrar o poupador -100 até 100
		/*
		  codigos:
		  -2 -> Sem visão para o local
		  -1 -> fora do ambiente (mundo exterior) / borda do mapa
		   0 -> Célula vazia (sem nenhum agente)
		   1 -> Parede
		   3 -> Banco
		   4 -> Moeda
		   5 -> Pastinha do Poder
		   100 ou 110 -> Poupador
		   200 -> Ladrão
		* */
		switch (codigo) {
			case -2:
				return -50;
			case -1:
				return -100;
			case 0:
				return 30;
			case 1:
				return 0;
			case 3:
				return 40;
			case 4:
				return -20;
			case 5:
				return -30;
			case 100, 110:
				return 100;
			case 200:
				return 90;
		}
		return 0;
	}

	public int heuristicaOlfato(int codigo) {
		switch(codigo){
		case 0:
			return 0;
		case 1:
			return 35;
		case 2:
			return 34;
		case 3:
			return 33;
		case 4:
			return 32;
		case 5:
			return 31;
		}
	return 0;
	}

	public int visaoCimaEsquerda(){
		int total = 0;
		total += heuristica(sensor.getVisaoIdentificacao()[0])
				+ heuristica(sensor.getVisaoIdentificacao()[1])
				+ heuristica(sensor.getVisaoIdentificacao()[2]);

		total += heuristica(sensor.getVisaoIdentificacao()[5])
				+ heuristica(sensor.getVisaoIdentificacao()[6])
				+ heuristica(sensor.getVisaoIdentificacao()[7]);

		total += heuristica(sensor.getVisaoIdentificacao()[10])
				+ heuristica(sensor.getVisaoIdentificacao()[11]);
		return total;
	}
	public int visaoCimaDireita(){
		int total = 0;
		total += heuristica(sensor.getVisaoIdentificacao()[2])
				+ heuristica(sensor.getVisaoIdentificacao()[3])
				+ heuristica(sensor.getVisaoIdentificacao()[4]);

		total += heuristica(sensor.getVisaoIdentificacao()[7])
				+ heuristica(sensor.getVisaoIdentificacao()[8])
				+ heuristica(sensor.getVisaoIdentificacao()[9]);

		total += heuristica(sensor.getVisaoIdentificacao()[12])
				+ heuristica(sensor.getVisaoIdentificacao()[13]);
		return total;
	}
	public int visaoBaixoEsquerda(){
		int total = 0;
		total += heuristica(sensor.getVisaoIdentificacao()[10])
				+ heuristica(sensor.getVisaoIdentificacao()[11])
				+ heuristica(sensor.getVisaoIdentificacao()[14]);

		total += heuristica(sensor.getVisaoIdentificacao()[15])
				+ heuristica(sensor.getVisaoIdentificacao()[16])
				+ heuristica(sensor.getVisaoIdentificacao()[19]);

		total += heuristica(sensor.getVisaoIdentificacao()[20])
				+ heuristica(sensor.getVisaoIdentificacao()[21]);
		return total;
	}
	public int visaoBaixoDireita(){
		int total = 0;
		total += heuristica(sensor.getVisaoIdentificacao()[12])
				+ heuristica(sensor.getVisaoIdentificacao()[13])
				+ heuristica(sensor.getVisaoIdentificacao()[16]);

		total += heuristica(sensor.getVisaoIdentificacao()[17])
				+ heuristica(sensor.getVisaoIdentificacao()[18])
				+ heuristica(sensor.getVisaoIdentificacao()[21]);

		total += heuristica(sensor.getVisaoIdentificacao()[22])
				+ heuristica(sensor.getVisaoIdentificacao()[23]);
		return total;
	}

	public int compararVisao(){
		int maior = visaoCimaEsquerda();
		int quadrante = 1;
		if(maior < visaoCimaDireita()){
			maior = visaoCimaDireita();
			quadrante = 2;
		}
		if(maior < visaoBaixoDireita()){
			maior = visaoBaixoDireita();
			quadrante = 3;
		}
		if(maior < visaoBaixoEsquerda()) {
//			maior = visaoBaixoEsquerda();
			quadrante = 4;
		}
		System.out.println(quadrante);
		return quadrante;
	}

	public int olfatoCimaEsquerda(){
		int total = 0;
		total += sensor.getAmbienteOlfatoLadrao()[0]
				+ sensor.getAmbienteOlfatoLadrao()[1]
				+ sensor.getAmbienteOlfatoLadrao()[3];
		return total;
	}
	public int olfatoCimaDireita(){
		int total = 0;
		total += sensor.getAmbienteOlfatoLadrao()[1]
				+ sensor.getAmbienteOlfatoLadrao()[2]
				+ sensor.getAmbienteOlfatoLadrao()[4];
		return total;
	}
	public int olfatoBaixoEsquerda(){
		int total = 0;
		total += sensor.getAmbienteOlfatoLadrao()[3]
				+ sensor.getAmbienteOlfatoLadrao()[5]
				+ sensor.getAmbienteOlfatoLadrao()[6];
		return total;
	}
	public int olfatoBaixoDireita(){
		int total = 0;
		total += sensor.getAmbienteOlfatoLadrao()[4]
				+ sensor.getAmbienteOlfatoLadrao()[6]
				+ sensor.getAmbienteOlfatoLadrao()[7];
		return total;
	}

	public int compararOlfato() {
		int maior = olfatoCimaEsquerda();
		int quadrante = 1;
		if(maior < olfatoCimaDireita()) {
			maior = olfatoCimaDireita();
			quadrante = 2;
		}
		if(maior < olfatoBaixoDireita()) {
			maior = olfatoBaixoDireita();
			quadrante = 3;
		}
		if(maior < olfatoBaixoEsquerda()) {
			quadrante = 4;
		}
		return quadrante;
	}

	public int mover(){
		int compararVisao = compararVisao();
		int compararOlfato = compararOlfato();
		int[] visaoId = sensor.getVisaoIdentificacao();
		int[] olfatoId = sensor.getAmbienteOlfatoLadrao();

		int olfato1 = ((heuristicaOlfato(olfatoId[0]) + heuristicaOlfato(olfatoId[1]) + heuristicaOlfato(olfatoId[2])) / 3); // 0 1 2
		int olfato2 = ((heuristicaOlfato(olfatoId[0]) + heuristicaOlfato(olfatoId[1]) + heuristicaOlfato(olfatoId[3])) / 3); // 0 1 3
		int olfato3 = ((heuristicaOlfato(olfatoId[0]) + heuristicaOlfato(olfatoId[3]) + heuristicaOlfato(olfatoId[5])) / 3); // 0 3 5
		int olfato4 = ((heuristicaOlfato(olfatoId[3]) + heuristicaOlfato(olfatoId[5]) + heuristicaOlfato(olfatoId[6])) / 3); // 3 5 6
		int olfato5 = ((heuristicaOlfato(olfatoId[5]) + heuristicaOlfato(olfatoId[6]) + heuristicaOlfato(olfatoId[7])) / 3); // 5 6 7
		int olfato6 = ((heuristicaOlfato(olfatoId[4]) + heuristicaOlfato(olfatoId[7]) + heuristicaOlfato(olfatoId[6])) / 3); // 4 6 7
		int olfato7 = ((heuristicaOlfato(olfatoId[2]) + heuristicaOlfato(olfatoId[4]) + heuristicaOlfato(olfatoId[7])) / 3); // 2 4 7

		// 0 1 2 6 7
		int visao1 = ((heuristica(visaoId[0]) + heuristica(visaoId[1]) +
				heuristica(visaoId[2]) + heuristica(visaoId[6]) +
				heuristica(visaoId[7])) / 5);

		// 0 5 6 10 11
		int visao2 = ((heuristica(visaoId[0]) +
				heuristica(visaoId[5]) + heuristica(visaoId[6]) +
				heuristica(visaoId[10]) + heuristica(visaoId[11])) / 5);

		// 2 3 4 7 8
		int visao3 = ((heuristica(visaoId[2]) + heuristica(visaoId[3]) +
				heuristica(visaoId[4]) + heuristica(visaoId[7]) +
				heuristica(visaoId[8])) / 5);

		// 4 8 9 12 13
		int visao4 = ((heuristica(visaoId[4]) +
				heuristica(visaoId[8]) + heuristica(visaoId[9]) +
				heuristica(visaoId[12]) + heuristica(visaoId[13])) / 5);

		// 12 13 17 18 23
		int visao5 = ((heuristica(visaoId[12]) + heuristica(visaoId[13]) +
				heuristica(visaoId[17]) + heuristica(visaoId[18]) +
				heuristica(visaoId[23])) / 5);

		// 16 17 21 22 23
		int visao6 = ((heuristica(visaoId[16]) +
				heuristica(visaoId[17]) + heuristica(visaoId[21]) +
				heuristica(visaoId[22]) + heuristica(visaoId[23])) / 5);

		// 15 16 19 20 21
		int visao7 = ((heuristica(visaoId[15]) + heuristica(visaoId[16]) +
				heuristica(visaoId[19]) + heuristica(visaoId[20]) +
				heuristica(visaoId[21])) / 5);

		// 10 11 14 15 19
		int visao8 = ((heuristica(visaoId[10]) +
				heuristica(visaoId[11]) + heuristica(visaoId[14]) +
				heuristica(visaoId[15]) + heuristica(visaoId[19])) / 5);

		switch (compararVisao) {
			case 1:
				boolean decisao = ( visao1 > visao2 ||
						(visaoId[11] == 1) || (visaoId[11] == 3) ||
						(visaoId[11] == 4) || (visaoId[11] == 5));

				boolean decisaoOlfato =
						olfato1 > olfato2
						&& olfato1 > olfato3
						&& olfato1 > olfato4
						&& olfato1 > olfato5
						&& olfato1 > olfato6
						&& olfato1 > olfato7;
				if (decisao || decisaoOlfato) {
					return 1;
				}
				return 4;

			case 2:
				boolean decisao2 = (visao3 >  visao4) ||
						(visaoId[12] == 1)|| (visaoId[12] == 3) ||
						(visaoId[12] == 4) || (visaoId[12] == 5);

				boolean decisaoOlfato2 =
								olfato2 > olfato1
								&& olfato2 > olfato3
								&& olfato2 > olfato4
								&& olfato2 > olfato5
								&& olfato2 > olfato6
								&& olfato2 > olfato7;
				if (decisao2 || decisaoOlfato2) {
					return 1;
				}
				return 3;

			case 3:
				boolean decisao3 = (visao5 >  visao6) ||
						(visaoId[16] == 1)|| (visaoId[16] == 3) ||
						(visaoId[16] == 4) || (visaoId[16] == 5);
				boolean decisaoOlfato3 =
								olfato3 > olfato2
								&& olfato3 > olfato1
								&& olfato3 > olfato4
								&& olfato3 > olfato5
								&& olfato3 > olfato6
								&& olfato3 > olfato7;
				if (decisao3 || decisaoOlfato3) {
					return 3;
				}
				return 2;

			default:
				boolean decisao4 = (visao7 >  visao8) ||
						(visaoId[11] == 1) || (visaoId[11] == 3) ||
						(visaoId[11] == 4) || (visaoId[11] == 5);
				boolean decisaoOlfato4 =
						olfato4 > olfato2
								&& olfato4 > olfato3
								&& olfato4 > olfato1
								&& olfato4 > olfato5
								&& olfato4 > olfato6
								&& olfato4 > olfato7;
				if (decisao4 || decisaoOlfato4) {
					return 2;
				}
				return 4;
		}
	}

	public int acao() {
		for (int i = 0; i < sensor.getVisaoIdentificacao().length; i++) {
			System.out.print(sensor.getVisaoIdentificacao()[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < sensor.getVisaoIdentificacao().length; i++) {
			System.out.print(heuristica(sensor.getVisaoIdentificacao()[i]) + " ");
		}
		System.out.println();

		System.out.println(visaoCimaEsquerda());
		System.out.println(visaoCimaDireita());
		System.out.println(visaoBaixoDireita());
		System.out.println(visaoBaixoEsquerda());

		if(
				Arrays.stream(sensor.getVisaoIdentificacao()).anyMatch(n -> n > 1) &&
				Arrays.stream(sensor.getAmbienteOlfatoLadrao()).anyMatch(n -> n > 1)
		) {
			return mover();
		}
		else {
			return (int)((Math.random() * 100) % 4) + 1;
		}
//	return mover();
	}
}

//		sensor.getVisaoIdentificacao();
//		sensor.getAmbienteOlfatoLadrao();
//		sensor.getAmbienteOlfatoPoupador();
//		sensor.getNumeroDeMoedas();
//		sensor.getPosicao();

//Se um ladrão ver um poupador, ele diz a posição atual e os outros ladrões vão até a posição
