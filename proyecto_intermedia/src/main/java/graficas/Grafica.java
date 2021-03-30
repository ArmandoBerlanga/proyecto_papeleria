package graficas;

import java.io.File; //Respectivos imports
import java.io.IOException;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

abstract class Grafica { //clase padre abstracta
    protected String tituloVentana; //Atributos aplicables a clases hijas
    protected String tituloGrafica;

    protected Grafica(String tituloVentana, String tituloGrafica) //constructor
        {
            setTituloVentana(tituloVentana);
            setTituloGrafica(tituloGrafica);
        }

    public void setTituloVentana(String tituloVentana) //respectivos seys y gets
        {
            this.tituloVentana = tituloVentana;
        }

    public String getTituloVentana() 
        {
            return tituloVentana;
        }

    public void setTituloGrafica(String tituloGrafica) 
        {
            this.tituloGrafica = tituloGrafica;
        }

    public String getTituloGrafica() 
        {
            return tituloGrafica;
        }

    protected void guardarArchivo (JFreeChart chart, String fileName) throws IOException 
        { 
            //Metodo para la creacion e la imagen y guardado del archivo
            File chartFile = new File(fileName + ".jpeg");
            ChartUtils.saveChartAsJPEG(chartFile, chart, 740, 480);
        }

    public abstract void generarGrafica (); //Metodo abstracto a definir por las clases hijas

}