/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

// package net.adoptopenjdk.bumblebench.unsafe.generator;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

public class BenchGenerator extends Task {
    String templateFilePath;
    public void setTemplate(String template) { templateFilePath=template; }

    String outputdir;
    public void setOutputdir(String dir) { outputdir=dir; }

    String prefix;
    public void setPrefix(String pfx) { prefix=pfx; }

    public class Blueprint {
        String mod="", type="", inlinetype="", init="", mode="", asLong="";

        public void setMode(String m) { mode=m; }
        public void setType(String ty) { type=ty; }
        public void setInlinetype(String ity) { inlinetype=ity; }
        public void setInit(String i) { init=i; }
        public void setAsLong(String s) { asLong=s; }
        public String getName() { return prefix + inlinetype + mode + "Bench"; }

        public String populateTemplate(String template) {
            return template.replaceAll("#name#", getName())
                           .replaceAll("#type#", inlinetype)
                           .replaceAll("#Type#", type)
                           .replaceAll("#mode#", mode)
                           .replaceAll("#init#", init)
                           .replaceAll("#asLong#", asLong);
        }
    }

    public Blueprint createBlueprint() {
        Blueprint bp = new Blueprint();
        blueprints.add(bp);
        return bp;
    }

    List<Blueprint> blueprints = new ArrayList<>();

    public void execute() {
        String template;
        try {
            template = Files.readString(Path.of(templateFilePath));
        } catch (IOException e) {
            throw new BuildException("Failed to read PutBench template.", e);
        }
        for (Blueprint bp : blueprints) {
            try (PrintStream out = new PrintStream(Path.of(outputdir, bp.getName() + ".java").toString())) {
                out.print(bp.populateTemplate(template));
            } catch (FileNotFoundException e) {
                throw new BuildException("Failed to generate "+bp.getName()+" benchmark.", e);
            }
        }
    }
}
