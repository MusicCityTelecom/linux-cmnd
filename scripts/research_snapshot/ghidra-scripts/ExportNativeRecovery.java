// Static recovery only. The imported executable is never launched.
// @category CMND.Research
import ghidra.app.script.GhidraScript;
import ghidra.app.decompiler.*;
import ghidra.program.model.listing.*;
import java.nio.file.*;
import java.io.*;

public class ExportNativeRecovery extends GhidraScript {
    public void run() throws Exception {
        Path output=Paths.get(getScriptArgs()[0]);
        Files.createDirectories(output);
        int total=0, ok=0, failed=0;
        DecompInterface decompiler=new DecompInterface();
        decompiler.setOptions(new DecompileOptions());
        try (PrintWriter code=new PrintWriter(Files.newBufferedWriter(output.resolve("recovered.c")));
             PrintWriter functions=new PrintWriter(Files.newBufferedWriter(output.resolve("functions.tsv")));
             PrintWriter assembly=new PrintWriter(Files.newBufferedWriter(output.resolve("disassembly.txt")))) {
            if (!decompiler.openProgram(currentProgram)) throw new IOException(decompiler.getLastMessage());
            code.println("/* Reconstructed pseudocode, not original or buildable C. */");
            functions.println("address\tname\tstatus");
            FunctionIterator iter=currentProgram.getFunctionManager().getFunctions(true);
            while (iter.hasNext() && !monitor.isCancelled()) {
                Function function=iter.next(); total++;
                if (function.isExternal()) {
                    functions.println(function.getEntryPoint()+"\t"+function.getName()+"\texternal");continue;
                }
                DecompileResults result=decompiler.decompileFunction(function,20,monitor);
                code.println("\n/* "+function.getEntryPoint()+" "+function.getName()+" */");
                if(result.decompileCompleted() && result.getDecompiledFunction()!=null) {
                    code.println(result.getDecompiledFunction().getC());ok++;
                    functions.println(function.getEntryPoint()+"\t"+function.getName()+"\tpseudocode-emitted");
                } else {
                    failed++;code.println("/* DECOMPILATION FAILED; see function manifest */");
                    functions.println(function.getEntryPoint()+"\t"+function.getName()+"\tfailed: "+result.getErrorMessage().replace('\n',' '));
                }
                code.flush();functions.flush();
            }
            InstructionIterator instructions=currentProgram.getListing().getInstructions(true);
            while(instructions.hasNext() && !monitor.isCancelled()) {
                Instruction instruction=instructions.next();
                assembly.println(instruction.getAddress()+" "+instruction);
            }
            Files.writeString(output.resolve("summary.json"),"{\"identified_functions\":"+total+",\"pseudocode_emitted\":"+ok+",\"failed\":"+failed+",\"executed\":false,\"equivalence_verified\":false}");
        } finally {decompiler.dispose();}
    }
}
