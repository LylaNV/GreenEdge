package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class LogcatAnalyzerToolWindow {
    private final Project project;
//    private final JTabbedPane tabs;
//    private final JTextArea textArea;
//    private final ChartPanel chartPanel;

    //private final JBPanel chartPanel;

    private JPanel textPanel;
    private JPanel graphPanel;
    private JTextArea logcatTextArea;

    public LogcatAnalyzerToolWindow(Project project) {
        this.project = project;

        textPanel = new JPanel(new BorderLayout());
        graphPanel = new JPanel(new BorderLayout());

        logcatTextArea = new JTextArea();
        textPanel.add(new JScrollPane(logcatTextArea), BorderLayout.CENTER);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart barChart = ChartFactory.createBarChart(
                "Logcat Analysis",
                "Category",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, JBColor.BLUE);

        ChartPanel chartPanel = new ChartPanel(barChart);
        graphPanel.add(chartPanel, BorderLayout.CENTER);
//        tabs = new JTabbedPane();
//
//        textArea = new JTextArea();
//        //chartPanel = createBarChartPanel(new DefaultCategoryDataset()); // Initial empty dataset
//        chartPanel = createBarChartPanel(new DefaultCategoryDataset()); // Initial empty dataset
//
//        tabs.addTab("Logcat Analyzer", new JScrollPane(textArea));
//        tabs.addTab("Bar Graph", chartPanel);

    }



    public JPanel getTextPanel() {
        return textPanel;
    }

    public JPanel getGraphPanel() {
        return graphPanel;
    }

    public void updateText(String text) {
        logcatTextArea.setText(text);
    }

    public void updateGraph(DefaultCategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Logcat Analysis",
                "Category",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        graphPanel.removeAll();
        graphPanel.add(new ChartPanel(barChart), BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    public void appendLog(String log) {
        logcatTextArea.append(log + "\n");
    }

    public void clearTextArea(String log) {
        logcatTextArea.removeAll();
    }


//    public void appendLog(String log) {
//        textArea.append(log + "\n");
//    }
//
//    public JTextArea getContent() {
//        return textArea;
//    }
//
//    private ChartPanel createBarChartPanel(DefaultCategoryDataset dataset) {
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Method Call Counts",
//                "Method Name",
//                "Number of Calls",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false);
//        return new ChartPanel(chart);
//    }
}
