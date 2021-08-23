package lifeRay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ejercicio {

	//funcion para sacar el precio
		public static Double getPrice(String entrada) {
			//expresion regular que representa los precios
			Pattern p = Pattern.compile("([0-9])+,+([0-9])\\w");
			Matcher m = p.matcher(entrada);
			//se devuelve el precio
			String precio = "";
			while(m.find()) {
				precio = m.group();
			}
			//reemplazamos la coma por el punto porque sino no se puede pasar a Double
			precio = precio.replace(",", ".");
			double result = Double.parseDouble(precio);
			return result;
		}
		
		
		public static String[] entrada1(String[] entrada, String[] exentos) {
			//Lo primero que hay que hacer es separar en un substring el precio
			/*En este caso se aprecia que los productos son solo de una unidad, en caso de más unidades, 
			se habría que separar el número de unidades para calcular el coste total de todas las unidades*/
			//se recorre la entrada y para se calculan los impouestos
			double impuestoTotal = 0.00;
			double precioTotal = 0.00;
			
			for(int i=0; i<entrada.length; i++) {
				double impuesto = 1.00;
				boolean anadirImpuesto = true;
				//se mira si está exento y después si es importado
				for(int j=0; j<exentos.length; j++) {
					//si no contiene ningun producto exento, se le añade el impuesto
					if(entrada[i].contains(exentos[j])) {
						anadirImpuesto = false;
					}
				}
				//si el producto es importado se le añade 0.05 mas al impuesto
				if(entrada[i].contains("importado")) {
					impuesto += 0.05;
				}
				if(anadirImpuesto==true) {
					impuesto += 0.10;
				}
				//se obtiene el precio y se reemplaza por el precio anterior
				//se reemplaza la a por el símbolo : para que la salida quede bien
				double precio = getPrice(entrada[i]);
				//redondeamos el precio
				double rounded = Math.round((precio*impuesto) * 20.0)/20.0; 
				impuestoTotal = impuestoTotal + (rounded - precio);
				precioTotal += rounded;
				String roundedStr = String.valueOf(rounded).replace(".", ",");
				entrada[i] = entrada[i].replaceAll("([0-9])+,+([0-9])\\w", roundedStr);
				entrada[i] = entrada[i].replaceAll(" +a+ ", ": ");
				//se imprime
				System.out.println(entrada[i]);
			}
			impuestoTotal = Math.round((impuestoTotal) * 100.0)/100.0;
			String imToStr = String.valueOf(impuestoTotal).replace(".", ",");
			System.out.println("Impuestos sobre las ventas: "+imToStr+" €");
			precioTotal = Math.round((precioTotal) * 100.0)/100.0;
			String preToStr = String.valueOf(precioTotal).replace(".", ",");
			System.out.println("Total: "+preToStr+" €");
			return entrada;
		}

		public static void main(String[] args) {
			//se crea un array con todos los términos que están exentos del impuesto
			String[] exentos = {"libro", "chocolate", "bombones", "pastillas"};
			//se crean las entradas
			String[] entrada1 = {"1 libro a 12,49 €", "1 CD de música a 14,99 €", "1 barrita de chocolate a 0,85 €"};
			System.out.println("Resultado 1");
			String[] salida1 = entrada1(entrada1, exentos);
			System.out.println();
			
			String[] entrada2 = {"1 caja de bombones importados a 10,00 €", "1 frasco de perfume a importado a 47,50 €"};
			System.out.println("Resultado 2");
			String[] salida2 = entrada1(entrada2, exentos);
			System.out.println();
			
			String[] entrada3 = {"1 frasco de perfume importado a 27,99 €", "1 frasco de perfume a 18,99 €", "1 caja de pastillas para el dolor de cabeza a 9,75 €", "1 caja de bombones importados a 11,25 €"};
			System.out.println("Resultado 3");
			String[] salida3 = entrada1(entrada3, exentos);
			System.out.println();
		}


}
