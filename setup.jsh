// example setup script for the JShell environment

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Insert path to the javafx jars and the project jar
// env -module-path path/to/javafx/lib;path/to/module/jar
/env -module-path lib/javafx-sdk-11/lib/;dist/
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

// main project module
/env -add-modules hu.unipannon.virt.plot

// fluent components imported in one step
import hu.unipannon.virt.plot.fluent.*;

// static imports for the fluent interface starter functions
import static hu.unipannon.virt.plot.fluent.Figure.figure;
import static hu.unipannon.virt.plot.fluent.Figure.Tiles.rows;
import static hu.unipannon.virt.plot.fluent.Figure.Positions.pos;
import static hu.unipannon.virt.plot.fluent.Plot.plot;
import static hu.unipannon.virt.plot.fluent.Log.loglog;
import static hu.unipannon.virt.plot.fluent.Log.semilogx;
import static hu.unipannon.virt.plot.fluent.Log.semilogy;
import static hu.unipannon.virt.plot.fluent.Scatter.scatter;
import static hu.unipannon.virt.plot.fluent.Line.line;
import static hu.unipannon.virt.plot.fluent.LineStyle.style;
import static hu.unipannon.virt.plot.fluent.Series.series;
import static hu.unipannon.virt.plot.fluent.Bar.bar;
import static hu.unipannon.virt.plot.fluent.Bar.hbar;
import static hu.unipannon.virt.plot.fluent.Category.CategoryStyle.width;
import static hu.unipannon.virt.plot.fluent.Category.ColorWrapper.color;
import static hu.unipannon.virt.plot.fluent.Category.category;
import static hu.unipannon.virt.plot.fluent.ScatterStyle.marker;
import static hu.unipannon.virt.plot.fluent.FrameStyle.title;
import static hu.unipannon.virt.plot.data.LegendSettings.LegendPosition.location;
import static hu.unipannon.virt.plot.util.FunctionGenerator.function;

