import ghidra.GhidraLaunchable;
import ghidra.GhidraApplicationLayout;
import ghidra.framework.Application;
import ghidra.framework.HeadlessGhidraApplicationConfiguration;
import ghidra.base.project.GhidraProject;
import ghidra.app.decompiler.*;
import ghidra.program.model.listing.*;
import ghidra.util.task.TaskMonitor;
import java.nio.file.*;
import java.io.*;

/** Export already analyzed local program databases read-only; never launch inputs. */
public class NativeExportLauncher implements GhidraLaunchable {
    public void launch(GhidraApplicationLayout layout,String[] args) throws Exception {
        Application.initializeApplication(layout,new HeadlessGhidraApplicationConfiguration());
        GhidraProject project=GhidraProject.openProject(args[0],args[1],false);
        try {
            Program program=project.openProgram("/",args[2],true);
            Path out=Paths.get(args[3]);Files.createDirectories(out);
            DecompInterface decompiler=new DecompInterface();
            decompiler.setOptions(new DecompileOptions());
            int total=0,ok=0,failed=0;
            try (PrintWriter code=new PrintWriter(Files.newBufferedWriter(out.resolve("recovered.c")));
                 PrintWriter manifest=new PrintWriter(Files.newBufferedWriter(out.resolve("functions.tsv")));
                 PrintWriter assembly=new PrintWriter(Files.newBufferedWriter(out.resolve("disassembly.txt")))) {
                if(!decompiler.openProgram(program))throw new IOException(decompiler.getLastMessage());
                code.println("/* Reconstructed pseudocode; not original or buildable C. */");
                manifest.println("address\tname\tstatus");
                FunctionIterator functions=program.getFunctionManager().getFunctions(true);
                while(functions.hasNext()) {
                    Function f=functions.next();total++;
                    if(f.isExternal()){manifest.println(f.getEntryPoint()+"\t"+f.getName()+"\texternal");continue;}
                    DecompileResults result=decompiler.decompileFunction(f,10,TaskMonitor.DUMMY);
                    code.println("\n/* "+f.getEntryPoint()+" "+f.getName()+" */");
                    if(result.decompileCompleted() && result.getDecompiledFunction()!=null) {
                        code.println(result.getDecompiledFunction().getC());ok++;
                        manifest.println(f.getEntryPoint()+"\t"+f.getName()+"\tpseudocode-emitted");
                    } else {failed++;manifest.println(f.getEntryPoint()+"\t"+f.getName()+"\tfailed: "+result.getErrorMessage().replace('\n',' '));}
                    code.flush();manifest.flush();
                }
                InstructionIterator instructions=program.getListing().getInstructions(true);
                while(instructions.hasNext()){Instruction i=instructions.next();assembly.println(i.getAddress()+" "+i);}
                Files.writeString(out.resolve("summary.json"),"{\"identified_functions\":"+total+",\"pseudocode_emitted\":"+ok+",\"failed\":"+failed+",\"executed\":false,\"equivalence_verified\":false}");
            } finally {decompiler.dispose();}
        } finally {project.close();}
    }
}
