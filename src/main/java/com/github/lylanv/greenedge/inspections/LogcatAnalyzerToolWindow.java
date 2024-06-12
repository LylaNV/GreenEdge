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

public class LogcatAnalyzerToolWindow {
    private final Project project;

    private JPanel textPanel;
    private JPanel graphPanel;
    private JTextArea logcatTextArea;

    private boolean clearGraph = false;

    public LogcatAnalyzerToolWindow(Project project) {
        this.project = project;

        textPanel = new JPanel(new BorderLayout());
        graphPanel = new JPanel(new BorderLayout());

        // creates text area to show log statements
        logcatTextArea = new JTextArea();
        textPanel.add(new JScrollPane(logcatTextArea), BorderLayout.CENTER);

        // creates bar chart to show each red API calls count
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

    // updates the bar graph
    public void updateGraph(DefaultCategoryDataset dataset) {
        if (!clearGraph) {
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
        }else {
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Logcat Analysis",
                    "Category",
                    "Count",
                    null,
                    PlotOrientation.VERTICAL,
                    true, true, false);
            graphPanel.removeAll();
            graphPanel.add(new ChartPanel(barChart), BorderLayout.CENTER);
            graphPanel.revalidate();
            graphPanel.repaint();
            clearGraph = false;
        }
    }

    // adds the newly found log statement to the text area
    public void appendLog(String log) {
        logcatTextArea.append(log + "\n");
    }

//    public void clearTextArea() {
//        logcatTextArea.setText("");
//        //logcatTextArea = new JTextArea();
//    }
//
//    public void clearGraph() {
//        //DefaultCategoryDataset emptyDataset = new DefaultCategoryDataset();
//        clearGraph = true;
//        updateGraph(null);
//        graphPanel.removeAll();
//        //graphPanel = new JPanel(new BorderLayout());
//    }

    public void deleteLogcatAnalyzerToolWindow(){
        logcatTextArea.setText("");
        clearGraph = true;
        updateGraph(null);
        graphPanel.removeAll();
    }
}
