package com.github.lylanv.greenedge.inspections;

//import com.android.tools.idea.projectsystem.BuildListener;
import com.android.tools.idea.projectsystem.ProjectSystemBuildManager;
import com.android.tools.idea.projectsystem.ProjectSystemBuildManager.BuildListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;

public class AndroidProjectBuildListener implements BuildListener {

    XYSeries series;
    int time = 0;

    @Override
    public void buildCompleted(final ProjectSystemBuildManager.BuildResult buildStatus) {
        if (buildStatus.getStatus() == ProjectSystemBuildManager.BuildStatus.SUCCESS){
            System.out.println("Build completed successfully");

        // creates XY chart to show energy consumption

        series = new XYSeries("Time");
        XYSeriesCollection energyDataset = new XYSeriesCollection(series);
        JFreeChart energyChart = ChartFactory.createXYLineChart(
                "Energy consumption of the application over time", //Chart title
                "Time", //X-axis label
                "Energy (J)", //Y-axis label
                energyDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot energyPlot = energyChart.getXYPlot();
        //energyPlot.setBackgroundPaint(Color.WHITE);
        energyPlot.setDomainPannable(true); // Allow panning (scrolling) on x-axis
        ChartPanel energyChartPanel = new ChartPanel(energyChart);
        //lineChartPanel.add(energyChartPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Energy consumption of the application over time");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(energyChartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Schedule a task to update the chart every second
        Timer timer = new Timer();
        timer.schedule(new UpdateTask(), 0, 1000); // Update every 1000 milliseconds (1 second)

        }
    }

    // Task to update the chart data
    class UpdateTask extends TimerTask {
        @Override
        public void run() {
            // Simulate data update (replace with your actual data fetching mechanism)
            int energyConsumption = energyDataFetch(); // Replace with actual method to get occurrences

            // Add or update data point in the series
            series.addOrUpdate(time++, energyConsumption);
        }
    }

    public final int energyDataFetch() {
        // Example: randomly generate occurrences
        return LogCatReader.energyConsumption;
    }

//    @Override
//    public void buildStarted(final ProjectSystemBuildManager.BuildMode buildMode) {
//    }

}
