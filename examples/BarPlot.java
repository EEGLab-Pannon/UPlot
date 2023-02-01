import hu.unipannon.virt.plot.fluent.Figure;
import hu.unipannon.virt.plot.fluent.Bar;
import hu.unipannon.virt.plot.fluent.Category;
import hu.unipannon.virt.plot.fluent.FrameStyle;

import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.Bar.bar;
import static hu.unipannon.virt.plot.fluent.FrameStyle.title;
import static hu.unipannon.virt.plot.fluent.Category.category;

public class BarPlot {
    public static void main(String[] args) {
        var ys1 = new double[] {4.0, 5.0, 3.0, 2.0, 5.0};
        var ys2 = new double[] {5.0, 3.0, 4.0, 5.0, 4.0};

        var fig = figure()
            .title("Bar Plot Example")
            .bar(
                title("Random Data")
                , category()
                    .series(ys1)
                    .series(ys2));
        
        fig.show();
    }    
}
