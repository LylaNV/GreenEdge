package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//import com.android.ddmlib.*;

public class LogcatAnalyzerToolWindow {
    private final Project project;

    private JPanel textPanel;
    private JPanel graphPanel;
    //private JPanel lineChartPanel;
    private JTextArea logcatTextArea;

    public int time = 0;
    public Timer timer;
    private XYSeries series;

    private boolean clearGraph = false;

    public LogcatAnalyzerToolWindow(Project project) {
        this.project = project;

        textPanel = new JPanel(new BorderLayout());
        graphPanel = new JPanel(new BorderLayout());
        //lineChartPanel = new JPanel(new BorderLayout());

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

//        // creates XY chart to show energy consumption
//        series = new XYSeries("Time");
//        XYSeriesCollection energyDataset = new XYSeriesCollection(series);
//        JFreeChart energyChart = ChartFactory.createXYLineChart(
//                "Energy consumption of the application over time", //Chart title
//                "Time", //X-axis label
//                "Energy (J)", //Y-axis label
//                energyDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//        XYPlot energyPlot = energyChart.getXYPlot();
//        //energyPlot.setBackgroundPaint(Color.WHITE);
//        energyPlot.setDomainPannable(true); // Allow panning (scrolling) on x-axis
//        ChartPanel energyChartPanel = new ChartPanel(energyChart);
//        //lineChartPanel.add(energyChartPanel, BorderLayout.CENTER);
//
//        JFrame frame = new JFrame("Energy consumption of the application over time");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.add(energyChartPanel, BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
//
//        // Schedule a task to update the chart every second
//        timer = new Timer();
//        timer.schedule(new UpdateTask(), 0, 1000); // Update every 1000 milliseconds (1 second)

//        ApplicationManager.getApplication().executeOnPooledThread(() -> {
//            try{
//                initializeADB();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        });
    }

//    private void initializeADB() throws IOException {
//        AndroidDebugBridge.init(false);
//        AndroidDebugBridge adb = AndroidDebugBridge.createBridge("/Users/lylan/UiO/android/platform-tools//adb", false);
//
//        if (adb == null) {
//            System.err.println("Failed to connect to ADB.");
//            return;
//        }
//
//        AndroidDebugBridge.addDeviceChangeListener(new AndroidDebugBridge.IDeviceChangeListener() {
//            @Override
//            public void deviceConnected(IDevice device) {
//                // creates line chart to show energy consumption
//                series = new XYSeries("Time");
//                XYSeriesCollection energyDataset = new XYSeriesCollection(series);
//                JFreeChart energyChart = ChartFactory.createXYLineChart(
//                        "Energy consumption of the application over time", //Chart title
//                        "Time", //X-axis label
//                        "Energy (J)", //Y-axis label
//                        energyDataset,
//                        PlotOrientation.VERTICAL,
//                        true,
//                        true,
//                        false
//                );
//                XYPlot energyPlot = energyChart.getXYPlot();
//                //energyPlot.setBackgroundPaint(Color.WHITE);
//                energyPlot.setDomainPannable(true); // Allow panning (scrolling) on x-axis
//                ChartPanel energyChartPanel = new ChartPanel(energyChart);
//                //lineChartPanel.add(energyChartPanel, BorderLayout.CENTER);
//
//                JFrame frame = new JFrame("Energy consumption of the application over time");
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.add(energyChartPanel, BorderLayout.CENTER);
//                frame.pack();
//                frame.setVisible(true);
//
//                // Schedule a task to update the chart every second
//                timer = new Timer();
//                timer.schedule(new UpdateTask(), 0, 1000); // Update every 1000 milliseconds (1 second)
//            }
//
//            @Override
//            public void deviceDisconnected(IDevice device) {
//                // Handle device disconnection if needed
//            }
//
//            @Override
//            public void deviceChanged(IDevice device, int changeMask) {
//                // Handle device changes if needed
//            }
//        });
//    }


//    public void drawLineGraph(){
//        // creates line chart to show energy consumption
//        series = new XYSeries("Time");
//        XYSeriesCollection energyDataset = new XYSeriesCollection(series);
//        JFreeChart energyChart = ChartFactory.createXYLineChart(
//                "Energy consumption of the application over time", //Chart title
//                "Time", //X-axis label
//                "Energy (J)", //Y-axis label
//                energyDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//        XYPlot energyPlot = energyChart.getXYPlot();
//        //energyPlot.setBackgroundPaint(Color.WHITE);
//        energyPlot.setDomainPannable(true); // Allow panning (scrolling) on x-axis
//        ChartPanel energyChartPanel = new ChartPanel(energyChart);
//        //lineChartPanel.add(energyChartPanel, BorderLayout.CENTER);
//
//        JFrame frame = new JFrame("Energy consumption of the application over time");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.add(energyChartPanel, BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
//
//        // Schedule a task to update the chart every second
//        timer = new Timer();
//        timer.schedule(new UpdateTask(), 0, 1000); // Update every 1000 milliseconds (1 second)
//    }

//    // Task to update the chart data
//    class UpdateTask extends TimerTask {
//        @Override
//        public void run() {
//            // Simulate data update (replace with your actual data fetching mechanism)
//            int energyConsumption = energyDataFetch(); // Replace with actual method to get occurrences
//
//            // Add or update data point in the series
//            series.addOrUpdate(time++, energyConsumption);
//        }
//    }

//    // Method to simulate fetching data (replace with actual data source)
//    public final int energyDataFetch() {
//        // Example: randomly generate occurrences
//        return LogCatReader.energyConsumption;
//    }

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
//
//    // updates the line graph
//    public void updateLineChart(XYSeriesCollection energyDS) {
//        if (!clearGraph) {
//            JFreeChart lineChart = ChartFactory.createXYLineChart(
//                    "Energy Consumption of Application", //Chart title
//                    "Time", //X-axis label
//                    "Energy (J)", //Y-axis label
//                    energyDS,
//                    PlotOrientation.VERTICAL, true, true, false
//            );
//            lineChartPanel.removeAll();
//            lineChartPanel.add(new ChartPanel(lineChart), BorderLayout.CENTER);
//            lineChartPanel.revalidate();
//            lineChartPanel.repaint();
//        }else {
//            JFreeChart lineChart = ChartFactory.createXYLineChart(
//                    "Energy Consumption of Application", //Chart title
//                    "Time", //X-axis label
//                    "Energy (J)", //Y-axis label
//                    null,
//                    PlotOrientation.VERTICAL, true, true, false
//            );
//            lineChartPanel.removeAll();
//            lineChartPanel.add(new ChartPanel(lineChart), BorderLayout.CENTER);
//            lineChartPanel.revalidate();
//            lineChartPanel.repaint();
//            clearGraph = false;
//        }
//    }

    // adds the newly found log statement to the text area
    public void appendLog(String log) {
        logcatTextArea.append(log + "\n");
    }

    public void deleteLogcatAnalyzerToolWindow(){
        logcatTextArea.setText("");
        clearGraph = true;
        updateGraph(null);
//        updateLineChart(null);

        graphPanel.removeAll();
        graphPanel.revalidate();

//        lineChartPanel.removeAll();
//        lineChartPanel.revalidate();
    }
}
