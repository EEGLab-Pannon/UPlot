import hu.unipannon.virt.plot.fluent.Figure;
import hu.unipannon.virt.plot.fluent.Line;
import hu.unipannon.virt.plot.fluent.FrameStyle;
import hu.unipannon.virt.plot.util.FunctionGenerator;

import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.Line.line;
import static hu.unipannon.virt.plot.fluent.FrameStyle.title;
import static hu.unipannon.virt.plot.util.FunctionGenerator.function;

public class LogPlot {
    public static void main(String[] args) {
        var f1 = function()
            .mapping(x -> Math.cos(x) + 2)
            .startValue(1.0)
            .endValue(8.0)
            .makeFunction();
        var f2 = function()
            .startValue(1.0)
            .endValue(8.0)
            .mapping(x -> Math.sin(x) + 2)
            .makeFunction();
        
        var fig = figure()
            .title("Logarithmic plot example")
            .loglog(
                line(f1.getXs(), f1.getYs())
              , line(f2.getXs(), f2.getYs()));
        
        fig.show();
    }
}
