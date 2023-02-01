import hu.unipannon.virt.plot.fluent.Figure;
import hu.unipannon.virt.plot.fluent.FrameStyle;
import hu.unipannon.virt.plot.fluent.Scatter;
import hu.unipannon.virt.plot.fluent.Series;

import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.FrameStyle.title;
import static hu.unipannon.virt.plot.fluent.Scatter.scatter;
import static hu.unipannon.virt.plot.fluent.Series.series;


public class ScatterPlot {

    public static void main(String[] args) {
        
        var xs1 = new double[] {0.0, 0.81, 0.9, 1.0, 1.0};
        var ys1 = new double[] {5.8, 6.0, 5.9, 5.8, 6.1};

        var xs2 = new double[] {2.0, 2.81, 2.9, 3.0, 3.0};
        var ys2 = new double[] {2.8, 3.0, 2.9, 2.8, 3.1};

        var fig = figure()
            .title("Scatter plot example")        
            .scatter(
                title("Two data points")
                .legend("data group 1", "data group 2")
                , series(xs1, ys1)
                , series(xs2, ys2));
        
        fig.show();
    }
    
}
