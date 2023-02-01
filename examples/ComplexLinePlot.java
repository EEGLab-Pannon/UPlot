import hu.unipannon.virt.plot.fluent.Figure;
import hu.unipannon.virt.plot.fluent.Plot;
import hu.unipannon.virt.plot.fluent.Line;
import hu.unipannon.virt.plot.fluent.LineStyle;
import hu.unipannon.virt.plot.fluent.FrameStyle;

import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.Plot.plot;
import static hu.unipannon.virt.plot.fluent.Line.line;
import static hu.unipannon.virt.plot.fluent.LineStyle.style;
import static hu.unipannon.virt.plot.fluent.FrameStyle.title;

public class ComplexLinePlot {
    public static void main(String[] args) {
        var xs = new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        var ys1 = new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        var ys2 = new double[] {1.0, 2.0, 3.0, 10.0, 7.0, 8.0, 4.0, 5.0, 0.0, 6.0, 9.0};
        var fig = figure()
            .title("Complex line plot example")
            .plot(
                title("Plot of 2 random series")
                .xlabel("Domain")
                .ylabel("Image")
                , line(xs, ys1, style("-.").color("red"))
                , line(xs, ys2, style("--").color("blue").width(1.5)));
        
        fig.show();
    }
}
