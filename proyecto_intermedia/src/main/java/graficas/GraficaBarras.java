package graficas;

import java.io.IOException;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficaBarras extends Grafica { //Herencia de clas abstacta
    private HashMap <String, HashMap <String, Double>> listaDatos; //Hashmap para añadir n datos
    private String ejeX; //Nombre de los ejes
    private String ejeY;

    public GraficaBarras(String tituloVentana, String tituloGrafica, String ejeX, String ejeY) //Constructor 
        {
            super(tituloVentana, tituloGrafica); //inicializacion de atributos del padre
            setEjeX(ejeX); //inicializacion de atributos propios
            setEjeY(ejeY);
            listaDatos = new HashMap<String, HashMap<String, Double>>(); //iniciaizacion de hashmap
        }

    public void setListaDatos(HashMap<String, HashMap<String, Double>> listaDatos) //Respectivos set y gets
        {
            this.listaDatos = listaDatos;
        }

    public HashMap<String, HashMap<String, Double>> getListaDatos() 
        {
            return listaDatos;
        }

    public void setEjeX(String ejeX) 
        {
            this.ejeX = ejeX;
        }

    public String getEjeX() 
        {
            return ejeX;
        }

    public void setEjeY(String ejeY) 
        {
            this.ejeY = ejeY;
        }

    public String getEjeY() 
        {
            return ejeY;
        }

    public void agregarDato(String conjunto, String etiquetaDato, double valor) //Metodo para añadir datos
        {
            HashMap<String, Double> map;
            if (listaDatos.keySet().contains(conjunto))
                {
                    map = listaDatos.get(conjunto);
                    if(map.containsKey(etiquetaDato))
                        valor += map.get(etiquetaDato);
                }
                

            else
                map = new HashMap<String, Double>();

            map.put(etiquetaDato, valor);
            listaDatos.put(conjunto, map);
        }

    @Override
    public void generarGrafica() //Metodo para generar la grafica, override de la abstraccion del principal
        {
            DefaultCategoryDataset ds = new DefaultCategoryDataset();

            for (String key : listaDatos.keySet())
                for (String keySecMap : listaDatos.get(key).keySet())
                    ds.addValue(listaDatos.get(key).get(keySecMap), key, keySecMap);

            JFreeChart chart = ChartFactory.createBarChart(tituloGrafica, ejeX, ejeY, ds, PlotOrientation.VERTICAL, true,
                    true, false);

            try {

                guardarArchivo(chart, tituloVentana); //Se guarda como JPEG la imagen creada con los datos

            } catch (IOException e) 
                {
                    System.out.println("\nERROR: " + e.getMessage());
                }

            ChartFrame frame = new ChartFrame(tituloVentana, chart);
            frame.pack();
            frame.setVisible(true);
            
        }

}