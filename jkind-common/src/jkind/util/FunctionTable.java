package jkind.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jkind.lustre.VarDecl;

public class FunctionTable {

	private final String funcName;
	private final Set<FunctionTableRow> rows = new HashSet<>();
	private final List<VarDecl> inputs;
	private final VarDecl output;

	public FunctionTable(String funcName, List<VarDecl> inputs, VarDecl output) {
		this.funcName = funcName;
		this.inputs = inputs;
		this.output = output;
	}

	public void addRow(FunctionTableRow row) {
		rows.add(row);
	}

	public List<VarDecl> getInputs() {
		return inputs;
	}
	
	public VarDecl getOutput() {
		return output;
	}
	
	public Set<FunctionTableRow> getRows() {
		return rows;
	}
	
	public String getName() {
		return funcName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.repeat("-", (inputs.size()+1)*10));
		sb.append("\n");
		for(VarDecl input : inputs){
			sb.append(String.format("%-10s", input.id +" "));
		}
		sb.append("| ");
		sb.append(funcName);
		sb.append("\n");
		sb.append(StringUtils.repeat("-", (inputs.size()+1)*10));
		sb.append("\n");
		for (FunctionTableRow row : rows) {
			sb.append(row);
			sb.append("\n");
		}
		return sb.toString();
	}

}