package graficas;

import java.io.IOException; //Respectivos imports
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class GraficaPie extends Grafica { //Herencia de clase abstracta
    private HashMap <String, Double> listaDatos; //Lista de datos

    public GraficaPie(String tituloVentana, String tituloGrafica) //Respectivo contructor
        {
            super(tituloVentana, tituloGrafica); //inicializacion de datos del padre
            listaDatos = new HashMap <String, Double> (); //inicializacion de los datos
        }

    public void setListaDatos(HashMap <String, Double> listaDatos) //Respectivos sets y gets
        {
            this.listaDatos = listaDatos;
        }

    public HashMap<String, Double> getListaDatos() 
        {
            return listaDatos;
        }
    
    public void agregarDato (String key, double value) //metodo para añadir datos
        {
            if (listaDatos.containsKey(key))
                listaDatos.put(key, listaDatos.get(key) + value);
            else 
                listaDatos.put(key, value);
        }

    @Override
    public void generarGrafica () //Metodo para generar la grafica y guardarla como JPEF
		{		
            DefaultPieDataset datos = new DefaultPieDataset();
            
            for (String key : listaDatos.keySet())
                datos.setValue(key, listaDatos.get(key));
	        
            JFreeChart chart = ChartFactory.createPieChart(tituloGrafica, datos, true, true, false);

            try {

                guardarArchivo(chart, tituloVentana); //metodo padre para guardar la grafica como JPEG

            } catch (IOException e) 
                {
                    System.out.println("\nERROR: " + e.getMessage());
                }
	        
	        ChartFrame frame = new ChartFrame(tituloVentana, chart);
            frame.pack();
	        frame.setVisible(true); 
        }


    public static void main(String[] args) {
        
        GraficaPie gp = new GraficaPie("tituloVentana", "tituloGrafica");
        gp.agregarDato("Queso", 123.54);
        gp.agregarDato("Nopales", 1234423.54);
        gp.generarGrafica();

    }

}