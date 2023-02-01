run_call:=java --module-path '..\dist\;..\lib\javafx-sdk-11\lib\' --add-modules hu.unipannon.virt.plot

all: SimpleLinePlot.class ComplexLinePlot.class BarPlot.class ScatterPlot.class LogPlot.class

simple: SimpleLinePlot.class
	$(run_call) SimpleLinePlot

complex: ComplexLinePlot.class
	$(run_call) ComplexLinePlot

bar: BarPlot.class
	$(run_call) BarPlot

scatter: ScatterPlot.class
	$(run_call) ScatterPlot

log: LogPlot.class
	$(run_call) LogPlot


%.class: %.java
	javac --module-path '..\dist\;..\lib\javafx-sdk-11\lib' --add-modules hu.unipannon.virt.plot $<
