/*
MIT License

Copyright (c) 2023 Electrical Brain Imaging Lab, University of Pannonia
Copyright (c) 2023 Balint Toth
Copyright (c) 2023 Zoltan Juhasz



Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * <b>UPlot - a MATLAB-like, easy-to-use scientific plot library in Java.</b><br>
 * The <code>hu.unipannon.virt.plot</code> module contains all code for the UPlot 
 * library.<br>
 * 
 * UPlot makes use of an Embedded DSL like programming interface. Plots can be 
 * initialised from Java code or JShell and can be displayed either in a separate 
 * window, or embedded into JavaFX Panes.
 * <br>
 * The module can be used by itself in the JShell environment. To make this process 
 * easier, there is a setup.jsh JShell script file in the project root directory, 
 * that contains all the commands required to setup the shell environment and the 
 * imports for the Fluent Interface.
 * To use the script, edit the line starting with <code>/env -module-path</code>, 
 * by adding the path to the hu.virt.unipannon.plot.jar and the path to the javafx 
 * library jar files installed on your system separated by a semicolon.
 * After this setup, open jshell and use the <code>/open setup.jsh</code> command. 
 * If the paths are incorrect, the shell will indicate it with exceptions. 
 * If no errors are present, the plotting functions are all imported and you can 
 * start plotting right away.
 * <br><br>
 * Usage requires the Java 11. versions of the following modules:<br>
 * <pre>
 *  - javafx.controls
 *  - javafx.swing
 * </pre>
 * 
 * <br><br>
 * <b>This library is licensed under the MIT License.</b>
 * <br>
 * Copyright (c) 2023 Electrical Brain Imaging Lab<br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy<br>
 * of this software and associated documentation files (the "Software"), to deal<br>
 * in the Software without restriction, including without limitation the rights<br>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell<br>
 * copies of the Software, and to permit persons to whom the Software is<br>
 * furnished to do so, subject to the following conditions:<br>
 * <br>
 * The above copyright notice and this permission notice shall be included in all<br>
 * copies or substantial portions of the Software.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR<br>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,<br>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE<br>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER<br>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,<br>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE<br>
 * SOFTWARE.<br>
 *<br>
 * @author Tóth Bálint, University of Pannonia
 */
module hu.unipannon.virt.plot {
    // javafx base modules
    requires javafx.controls;
    requires javafx.swing;
    
    // exporting all the code
    exports hu.unipannon.virt.plot.fluent;
    exports hu.unipannon.virt.plot.control;
    exports hu.unipannon.virt.plot.data;
    exports hu.unipannon.virt.plot.frame;
    exports hu.unipannon.virt.plot.util;
    exports hu.unipannon.virt.plot.frame.horizontal;
    exports hu.unipannon.virt.plot.frame.vertical;
}
