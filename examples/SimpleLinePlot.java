import hu.unipannon.virt.plot.fluent.Figure;
import hu.unipannon.virt.plot.fluent.Plot;
import hu.unipannon.virt.plot.fluent.Line;

import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.Plot.plot;
import static hu.unipannon.virt.plot.fluent.Line.line;

public class SimpleLinePlot {
    public static void main(String[] args) {
        var xs = new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        var ys = new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        var fig = figure()
            .title("Simple line plot example")
            .plot(line(xs, ys));
        
        fig.show();
    }
}