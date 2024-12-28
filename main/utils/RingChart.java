package main.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;


import java.text.DecimalFormat;

public class RingChart {
    /**
     * This Function is generating ring chart*/
    public static JFreeChart generateRingChart(PieDataset dataset) {
        JFreeChart ringChart = ChartFactory.createRingChart("Monthly Income",dataset,true, false, false);

        PiePlot plot = (PiePlot) ringChart.getPlot();
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0.00"), new DecimalFormat("0.00%"));
        plot.setLabelGenerator(gen);
        ringChart.setBorderVisible(false);
        return ringChart;
    }
}
